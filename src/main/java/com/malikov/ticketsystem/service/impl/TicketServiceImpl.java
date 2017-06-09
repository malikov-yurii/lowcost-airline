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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.scheduling.TaskScheduler;
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
import static com.malikov.ticketsystem.util.ValidationUtil.checkEqual;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFound;

/**
 * @author Yurii Malikov
 */
@Service("ticketService")
@Transactional
public class TicketServiceImpl implements ITicketService, MessageSourceAware {

    private static final Logger LOG = LoggerFactory.getLogger(TicketServiceImpl.class);

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
    public void update(TicketDTO ticketDTO) {
        Assert.notNull(ticketDTO, "ticket must not be null");
        Ticket ticket = ticketRepository.get(ticketDTO.getId());
        ticket.setPassengerFirstName(ticketDTO.getPassengerFirstName());
        ticket.setPassengerLastName(ticketDTO.getPassengerLastName());
        ticket.setPrice(ticketDTO.getPrice());
        checkNotFound(ticketRepository.save(ticket),
                getMessage(messageSource, "exception.notFoundById") + ticket.getId());
        LOG.info("Ticket updated.");
    }

    @Override
    public void delete(long ticketId) {
        checkNotFound(ticketRepository.delete(ticketId),
                getMessage(messageSource, "exception.notFoundById") + ticketId);
        LOG.info("Ticket deleted.");
        terminateAutomaticRemovalTask(ticketId);
    }

    @Override
    public List<Ticket> getByUserEmail(String email, Integer start, Integer limit) {
        return ticketRepository.getByEmail(email, start, limit);
    }

    @Override
    public List<TicketWithRemainingDelayDTO> getActiveTicketsWithDelays(long userId, Integer start, Integer limit) {
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
                getMessage(messageSource, "exception.notFoundById") + ticketId);
        delete(ticketId);
        LOG.info("Booking for ticket={} canceled.", ticketId);
    }

    @Override
    @Transactional
    public Ticket createNewBookedTicketAndScheduledTask(TicketDTO ticketDTO, long flightId,
                                                        TicketPriceDetailsDTO ticketPriceDetailsDTO) {
        Ticket newTicket = new Ticket();
        BigDecimal ticketPrice;
        Flight flight = checkNotFound(flightRepository.get(flightId),
                getMessage(messageSource, "exception.notFoundById") + flightId);


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
                getMessage(messageSource, "exception.mustBeSame"));
        checkEqual(flight.getDepartureAirport().getName(), newTicket.getDepartureAirportName(),
                getMessage(messageSource, "exception.mustBeSame"));
        checkEqual(flight.getArrivalAirport().getName(), newTicket.getArrivalAirportName(),
                getMessage(messageSource, "exception.mustBeSame"));
        checkEqual(flight.getDepartureAirport().getCity().getName(), newTicket.getDepartureCityName(),
                getMessage(messageSource, "exception.mustBeSame"));
        checkEqual(flight.getArrivalAirport().getCity().getName(), newTicket.getArrivalCityName(),
                getMessage(messageSource, "exception.notFoundByName"));

        newTicket.setUser(userRepository.get(AuthorizedUser.id()));
        newTicket.setDepartureZoneId(flight.getDepartureAirport().getCity().getZoneId());
        newTicket.setStatus(TicketStatus.BOOKED);

        Ticket bookedTicket = ticketRepository.save(newTicket);
        ticketIdRemovalTaskMap.put(bookedTicket.getId(), getDeleteIfNotPaidTask(bookedTicket.getId()));
        LOG.info("New booked ticket {} created.", newTicket);

        return bookedTicket;
    }

    @Override
    @Transactional
    public void processPaymentByUser(Long ticketId, OffsetDateTime purchaseOffsetDateTime) {
        Ticket ticket = checkNotFound(ticketRepository.get(ticketId),
                getMessage(messageSource, "exception.notFoundById") + ticketId);

        if (!ticket.getUser().getId().equals(AuthorizedUser.id())) {
            throw new AccessDeniedException(getMessage(messageSource, "exception.accessDenied"));
        }

        if (!ticket.getStatus().equals(TicketStatus.BOOKED)) {
            throw new IllegalArgumentException(getMessage(messageSource,
                    "exception.onlyBookedTicketCanBeProcessed"));
        }

        checkNotFound(getWithdrawalStatus(AuthorizedUser.id(), ticket.getPrice()),
                getMessage(messageSource, "exception.rejectedByBank"));

        ticket.setStatus(TicketStatus.PAID);
        ticket.setPurchaseOffsetDateTime(purchaseOffsetDateTime);

        ticketRepository.save(ticket);
        terminateAutomaticRemovalTask(ticketId);
        LOG.info("Payment succeed.");
    }

    private ScheduledFuture getDeleteIfNotPaidTask(long ticketId) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);
        LOG.info("Scheduled future for ticket {} created.", ticketId);

        return scheduler.schedule(() -> {
                    checkNotFound(ticketRepository.deleteIfNotPaid(ticketId),
                            getMessage(messageSource, "exception.notFoundById") + ticketId);
                    ticketIdRemovalTaskMap.remove(ticketId);
                },
                new Date(System.currentTimeMillis() + BOOKING_DURATION_MILLIS));
    }

    private void terminateAutomaticRemovalTask(Long ticketId) {
        ticketIdRemovalTaskMap.get(ticketId).cancel(false);
        ticketIdRemovalTaskMap.remove(ticketId);
        LOG.info("Removal task terminated.");
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
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
