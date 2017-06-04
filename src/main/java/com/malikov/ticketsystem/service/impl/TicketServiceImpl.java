package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.TicketStatus;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.util.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
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
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Yurii Malikov
 */
@Service("ticketService")
@Transactional
public class TicketServiceImpl implements ITicketService {


    @Autowired
    private ITicketRepository ticketRepository;

    //@Autowired
    // TODO: 6/3/2017
    private TaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());

    // TODO: 5/30/2017 Get rid of it in service!
    private static final Map<Long, ScheduledFuture> ticketIdRemovalTaskMap = new HashMap<>();


    @Override
    public List<TicketWithRemainingDelayDTO> getActiveTicketsDelaysByUserId(long userId, Integer start, Integer limit) {
        return ticketRepository
                .getActiveByUserId(userId, start, limit)
                .stream()
                .map(ticket -> {
                    ScheduledFuture ticketRemovalTask = ticketIdRemovalTaskMap.get(ticket.getId());
                    return TicketUtil.getTicketWithRemaimingDelayDTO(ticket,
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
                .map(TicketUtil::asDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelBooking(Long ticketId) {
        Ticket ticket = ticketRepository.get(ticketId);

        if (ticket == null || !ticket.getStatus().equals(TicketStatus.BOOKED) || ticket.getUser().getId() != AuthorizedUser.id()) {
            return false;
        }

        if (!ticketRepository.delete(ticketId)) {
            return false;
        }

        // TODO: 6/3/2017 move that to private method?
        ticketIdRemovalTaskMap.get(ticketId).cancel(false);
        ticketIdRemovalTaskMap.remove(ticketId);

        return true;
    }

    @Override
    // TODO: 6/1/2017 It should be transactional
    public ResponseEntity processPayment(Long ticketId, OffsetDateTime purchaseOffsetDateTime) {
        Ticket ticket = ticketRepository.get(ticketId);

        // TODO: 6/3/2017 to many returns, ha?

        if (ticket == null) {
            // TODO: 6/1/2017 Consider using custom Error for Httpresponse as in topjava
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested booked ticket has not been found");
        }

        if (!ticket.getUser().getId().equals(AuthorizedUser.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User can pay only for tickets booked by him");
        }


        if (!getWithdrawalStatus(AuthorizedUser.id(), ticket.getPrice())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed. Rejected by user's bank");
        }

        ticket.setStatus(TicketStatus.PAID);
        ticket.setPurchaseOffsetDateTime(purchaseOffsetDateTime);

        if (ticketRepository.save(ticket) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed while saving PAID status.");
        }

        ticketIdRemovalTaskMap.get(ticketId).cancel(false);
        ticketIdRemovalTaskMap.remove(ticket.getId());

        return new ResponseEntity(HttpStatus.OK);
    }

    // TODO: 5/31/2017 considerRefactoring
    @Override
    public Set<Integer> getFreeSeats(Flight flight) {
        List<Integer> notFreeSeatsNumbers = ticketRepository.getNotFreeSeatsNumbers(flight.getId());
        Set<Integer> freeSeats = new HashSet<>();

        for (int i = 1; i <= flight.getAircraft().getModel().getPassengersSeatsQuantity(); i++) {
            if (!notFreeSeatsNumbers.contains(i)) {
                freeSeats.add(i);
            }
        }

        return freeSeats;
    }


    // TODO: 5/30/2017 remove annotation?
    // TODO: 6/1/2017 Should i delete unnecessary ifNotPaid checking?
    @Async
    public ScheduledFuture getDeleteIfNotPaidTask(long ticketId) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);

        return scheduler.schedule(() -> {
                    ticketRepository.deleteIfNotPaid(ticketId);
                    ticketIdRemovalTaskMap.remove(ticketId);
                },
                new Date(System.currentTimeMillis() + BOOKING_DURATION_MILLIS));
    }

    // TODO: 5/30/2017 make it transactional??
    // TODO: 5/30/2017 consider splitting into several methods
    @Override
    public Ticket createNewBookedTicketAndScheduledTask(Ticket newTicket) {

        Integer ticketQuantity = ticketRepository.countForFlight(newTicket.getFlight().getId());

        Ticket bookedTicket;

        if (ticketQuantity >= newTicket.getFlight().getAircraft().getModel().getPassengersSeatsQuantity()) {
            bookedTicket = null;
        } else {
            newTicket.setStatus(TicketStatus.BOOKED);
            bookedTicket = save(newTicket, AuthorizedUser.id());
        }

        if (bookedTicket != null) {
            ticketIdRemovalTaskMap.put(bookedTicket.getId(), getDeleteIfNotPaidTask(bookedTicket.getId()));
        }
        return bookedTicket;
    }

    @Override
    public Ticket save(Ticket ticket, long userId) {
        Assert.notNull(ticket, "ticket should not be null");
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket update(TicketDTO ticketDTO) {
        // TODO: 5/5/2017 get rid of message  duplicating
        Assert.notNull(ticketDTO, "ticket should not be null");
        Ticket ticket = ticketRepository.get(ticketDTO.getId());
        return ticketRepository.save(TicketUtil.updateFromDTO(ticket, ticketDTO));
    }

    @Override
    public Ticket get(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id), id);
    }

    @Override
    public Ticket getWithUser(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id, Ticket.WITH_USER), id);
    }

    @Override
    public Ticket getWithFlight(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id, Ticket.WITH_FLIGHT), id);
    }

    @Override
    public Ticket getWithUserAndFlight(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id, Ticket.WITH_USER_AND_FLIGHT), id);
    }

    @Override
    public boolean delete(long ticketId) {
        if (!ticketRepository.delete(ticketId)) {
            return false;
        }
        ticketIdRemovalTaskMap.get(ticketId).cancel(false);
        ticketIdRemovalTaskMap.remove(ticketId);
        return true;
    }

    @Override
    public List<Ticket> getAll(long userId) {
        return ticketRepository.getAll(userId);
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
    public List<Ticket> getByUserEmail(String email, Integer startingFrom, Integer limit) {
        return ticketRepository.getByEmail(email, startingFrom, limit);
    }


}
