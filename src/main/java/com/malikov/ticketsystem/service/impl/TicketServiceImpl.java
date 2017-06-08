package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketPriceDetailsDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.TicketStatus;
import com.malikov.ticketsystem.repository.IFlightRepository;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.repository.IUserRepository;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.util.dtoconverter.TicketDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.malikov.ticketsystem.util.DateTimeUtil.BOOKING_DURATION_MILLIS;
import static com.malikov.ticketsystem.util.MessageUtil.getMessage;
import static com.malikov.ticketsystem.util.ValidationUtil.*;

/**
 * @author Yurii Malikov
 */
@Service("ticketService")
@Transactional
public class TicketServiceImpl implements ITicketService, MessageSourceAware {

    private static final Map<Long, ScheduledFuture> ticketIdRemovalTaskMap = new HashMap<>();

    private MessageSource messageSource;

    @Autowired
    private TaskScheduler scheduler;

    @Autowired
    private ITicketRepository ticketRepository;

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private IUserRepository userRepository;


    @Override
    public List<TicketWithRemainingDelayDTO> getActiveTicketsDelays(long userId, Integer start, Integer limit) {
        return ticketRepository
                .getActiveByUserId(userId, start, limit)
                .stream()
                .map(ticket -> {
                    ScheduledFuture ticketRemovalTask = ticketIdRemovalTaskMap.get(ticket.getId());
                    return TicketDTOConverter.getTicketWithRemaimingDelayDTO(ticket,
                            ticketRemovalTask != null
                                    ? ticketRemovalTask.getDelay(TimeUnit.MILLISECONDS)
                                    : null);
                })
                .collect(Collectors.toList());
    }

    // TODO: 6/7/2017 is it ok to return dto from service ???
    @Override
    public List<TicketDTO> getArchivedTickets(Long userId, Integer start, Integer limit) {
        return ticketRepository.getArchivedByUserId(userId, start, limit)
                .stream()
                .map(TicketDTOConverter::asDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void cancelBooking(Long ticketId) {
        Ticket ticket = ticketRepository.get(ticketId);
        checkNotFound(ticket != null
                        && ticket.getStatus().equals(TicketStatus.BOOKED)
                        && ticket.getUser().getId() == AuthorizedUser.id(),
                getMessage(messageSource,"exception.notFoundById") + ticketId);
        delete(ticketId);
    }

    @Override
    // TODO: 6/1/2017 It should be transactional ?
    @Transactional
    public void processPaymentByUser(Long ticketId, OffsetDateTime purchaseOffsetDateTime) {
        Ticket ticket = checkNotFound(ticketRepository.get(ticketId),
                getMessage(messageSource,"exception.notFoundById") + ticketId);

        if (!ticket.getUser().getId().equals(AuthorizedUser.id())) {
            throw new AccessDeniedException(getMessage(messageSource,"exception.accessDenied"));
        }

        if (!ticket.getStatus().equals(TicketStatus.BOOKED)) {
            throw new IllegalArgumentException(getMessage(messageSource,
                    "exception.onlyBookedTicketCanBeProcessed"));
        }

        checkNotFound(getWithdrawalStatus(AuthorizedUser.id(), ticket.getPrice()),
                getMessage(messageSource,"exception.rejectedByBank"));

        ticket.setStatus(TicketStatus.PAID);
        ticket.setPurchaseOffsetDateTime(purchaseOffsetDateTime);

        ticketRepository.save(ticket);
        terminateAutomaticRemovalTask(ticketId);
    }

    private void terminateAutomaticRemovalTask(Long ticketId) {
        ticketIdRemovalTaskMap.get(ticketId).cancel(false);
        ticketIdRemovalTaskMap.remove(ticketId);
    }


    // TODO: 5/30/2017 remove annotation?
    // TODO: 6/1/2017 Should i delete unnecessary ifNotPaid checking?
    @Async
    private ScheduledFuture getDeleteIfNotPaidTask(long ticketId) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);

        return scheduler.schedule(() -> {
                    checkNotFound(ticketRepository.deleteIfNotPaid(ticketId),
                            getMessage(messageSource,"exception.notFoundById") + ticketId);
                    ticketIdRemovalTaskMap.remove(ticketId);
                },
                new Date(System.currentTimeMillis() + BOOKING_DURATION_MILLIS));
    }

    // TODO: 5/30/2017 make it transactional??
    // TODO: 5/30/2017 consider splitting into several methods
    @Override
    @Transactional
    public Ticket createNewBookedTicketAndScheduledTask(TicketDTO ticketDTO, long flightId,
                                                        TicketPriceDetailsDTO ticketPriceDetailsDTO) {
        Ticket newTicket = new Ticket();
        BigDecimal ticketPrice;
        Flight flight = checkNotFound(flightRepository.get(flightId),
                getMessage(messageSource,"exception.notFoundById") + flightId);


        ticketPrice = ticketPriceDetailsDTO.getBaseTicketPrice();

        if (ticketDTO.isHasBaggage() != null && ticketDTO.isHasBaggage()) {
            ticketPrice = ticketPrice.add(ticketPriceDetailsDTO.getBaggagePrice());
        }

        if (ticketDTO.isHasPriorityRegistrationAndBoarding() != null
                && ticketDTO.isHasPriorityRegistrationAndBoarding()) {
            ticketPrice = ticketPrice.add(ticketPriceDetailsDTO.getPriorityRegistrationAndBoardingPrice());
        }

        checkEqual(ticketPrice, ticketDTO.getPrice(),
                getMessage(messageSource, "exception.mustBeSame"));

        ticketDTO.setPrice(ticketPrice);
        newTicket.setFlight(flight);

        newTicket = TicketDTOConverter.updateFromDTOBeforeBooking(newTicket, ticketDTO);
        checkEqual(flight.getDepartureUtcDateTime(), newTicket.getDepartureUtcDateTime(),
                getMessage(messageSource,"exception.mustBeSame"));
        checkEqual(flight.getDepartureAirport().getName(), newTicket.getDepartureAirportName(),
                getMessage(messageSource,"exception.mustBeSame"));
        checkEqual(flight.getArrivalAirport().getName(), newTicket.getArrivalAirportName(),
                getMessage(messageSource,"exception.mustBeSame"));
        checkEqual(flight.getDepartureAirport().getCity().getName(), newTicket.getDepartureCityName(),
                getMessage(messageSource,"exception.mustBeSame"));
        checkEqual(flight.getArrivalAirport().getCity().getName(), newTicket.getArrivalCityName(),
                getMessage(messageSource,"exception.notFoundByName") );

        newTicket.setUser(userRepository.get(AuthorizedUser.id()));
        newTicket.setDepartureZoneId(flight.getDepartureAirport().getCity().getZoneId());
        newTicket.setStatus(TicketStatus.BOOKED);

        Ticket bookedTicket = ticketRepository.save(newTicket);
        ticketIdRemovalTaskMap.put(bookedTicket.getId(), getDeleteIfNotPaidTask(bookedTicket.getId()));

        return bookedTicket;
    }

    @Override
    public void update(TicketDTO ticketDTO) {
        // TODO: 5/5/2017 get rid of message  duplicating ??? how??
        Assert.notNull(ticketDTO, "ticket must not be null");
        Ticket ticket = ticketRepository.get(ticketDTO.getId());
        ticket.setPassengerFirstName(ticketDTO.getPassengerFirstName());
        ticket.setPassengerLastName(ticketDTO.getPassengerLastName());
        ticket.setPrice(ticketDTO.getPrice());
        checkNotFound(ticketRepository.save(ticket),
                getMessage(messageSource,"exception.notFoundById") + ticket.getId());
    }

    @Override
    public void delete(long ticketId) {
        checkNotFound(ticketRepository.delete(ticketId),
                getMessage(messageSource,"exception.notFoundById") + ticketId);
        terminateAutomaticRemovalTask(ticketId);
    }

    /**
     * Method imitates withdrawal processing using bank API
     *
     * @param userId
     * @return true if payment was successful, true if bank returned fail status
     */
    private boolean getWithdrawalStatus(long userId, BigDecimal price) {
        return true;
    }

    @Override
    public List<Ticket> getByUserEmail(String email, Integer start, Integer limit) {
        return ticketRepository.getByEmail(email, start, limit);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
