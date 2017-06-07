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
import com.malikov.ticketsystem.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.malikov.ticketsystem.util.DateTimeUtil.BOOKING_DURATION_MILLIS;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFoundById;
import static com.malikov.ticketsystem.util.ValidationUtil.checkSuccess;

/**
 * @author Yurii Malikov
 */
@Service
@Transactional
public class TicketServiceImpl implements ITicketService {


    @Autowired
    private ITicketRepository ticketRepository;

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private IUserRepository userRepository;

    //@Autowired
    // TODO: 6/3/2017
    private TaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());

    // TODO: 5/30/2017 Get rid of it in service!
    private static final Map<Long, ScheduledFuture> ticketIdRemovalTaskMap = new HashMap<>();


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
        checkSuccess(ticket != null
                        && ticket.getStatus().equals(TicketStatus.BOOKED)
                        && ticket.getUser().getId() == AuthorizedUser.id(),
                "not found booked ticket with id=" + ticketId + "for authorized user.");
        delete(ticketId);
    }

    @Override
    // TODO: 6/1/2017 It should be transactional
    @Transactional
    public void processPaymentByUser(Long ticketId, OffsetDateTime purchaseOffsetDateTime) {
        Ticket ticket = checkNotFoundById(ticketRepository.get(ticketId), ticketId);

        if (!ticket.getUser().getId().equals(AuthorizedUser.id())) {
            throw new AccessDeniedException("Access denied to ticket with id=" + ticketId);
        }

        if (!ticket.getStatus().equals(TicketStatus.BOOKED)) {
            throw new IllegalArgumentException("Only ticket booked ticket can by processed.");
        }

        checkSuccess(getWithdrawalStatus(AuthorizedUser.id(), ticket.getPrice()),"Rejected by bank.");

        ticket.setStatus(TicketStatus.PAID);
        ticket.setPurchaseOffsetDateTime(purchaseOffsetDateTime);

        // TODO: 6/6/2017 ValidationUtil.checkSuccess??
        ticketRepository.save(ticket);
        terminateAutomaticalRemovalTask(ticketId);
    }

    private void terminateAutomaticalRemovalTask(Long ticketId) {
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
                    checkNotFoundById(ticketRepository.deleteIfNotPaid(ticketId), ticketId);
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
        Flight flight = checkNotFoundById(flightRepository.get(flightId), flightId);

        ticketPrice = ticketPriceDetailsDTO.getBaseTicketPrice();

        if (ticketDTO.getWithBaggage() != null && ticketDTO.getWithBaggage()) {
            ticketPrice = ticketPrice.add(ticketPriceDetailsDTO.getBaggagePrice());
        }

        if (ticketDTO.getWithPriorityRegistrationAndBoarding() != null
                && ticketDTO.getWithPriorityRegistrationAndBoarding()) {
            ticketPrice = ticketPrice.add(ticketPriceDetailsDTO.getPriorityRegistrationAndBoardingPrice());
        }

        if (!ticketPrice.equals(ticketDTO.getPrice())) {
            // TODO: 6/1/2017 Send email to admin about fraud attempt (id of user, flight, pricefraud)
            ticketDTO.setPrice(ticketPrice);
        }

        newTicket.setFlight(flight);
        newTicket = TicketDTOConverter.updateFromDTO(newTicket, ticketDTO);
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
        Assert.notNull(ticketDTO, "ticket should not be null");
        Ticket ticket = ticketRepository.get(ticketDTO.getId());
        ValidationUtil.checkNotFoundById(ticketRepository.save(TicketDTOConverter.updateFromDTO(ticket, ticketDTO)),
                                         ticketDTO.getId());
    }

    @Override
    public void delete(long ticketId) {
        checkNotFoundById(ticketRepository.delete(ticketId), ticketId);
        terminateAutomaticalRemovalTask(ticketId);
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
}
