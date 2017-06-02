package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.TicketStatus;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Yurii Malikov
 */
@Service("ticketService")
public class TicketServiceImpl implements ITicketService {

    private static final long BOOKING_DELAY_MILLIS = DateTimeUtil.ONE_MINUTE_IN_MILLIS;

    @Autowired
    private ITicketRepository ticketRepository;

    //@Autowired
    private TaskScheduler scheduler = new ConcurrentTaskScheduler(Executors.newSingleThreadScheduledExecutor());

    // TODO: 5/30/2017 Get rid of it in service!
    private static Map<Long, ScheduledFuture> ticketTaskMap = new HashMap<>();


    @Override
    public boolean cancelBooking(Long ticketId, long userId) {
        Ticket ticket = ticketRepository.get(ticketId, AuthorizedUser.id());

        if (ticket == null || !ticket.getStatus().equals(TicketStatus.BOOKED)){
            return false;
        }

        return ticketRepository.delete(ticketId, userId);
    }

    @Override
    // TODO: 6/1/2017 It should be transactional
    public ResponseEntity processPayment(Long ticketId, OffsetDateTime purchaseOffsetDateTime) {
        Ticket ticket = ticketRepository.get(ticketId, AuthorizedUser.id());

        if (ticket == null){
            // TODO: 6/1/2017 Consider using custom Error for Httpresponse as in topjava
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested booked ticket has not been found");
        }


        if (!getWithdrawalStatus(AuthorizedUser.id(), ticket.getPrice())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment failed. Rejected by user's bank");
        }

        ticket.setStatus(TicketStatus.PAID);
        ticket.setPurchaseOffsetDateTime(purchaseOffsetDateTime);

        if (ticketRepository.save(ticket, AuthorizedUser.id()) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed while saving PAID status.");
        }

        ticketTaskMap.remove(ticket.getId());

        return new ResponseEntity(HttpStatus.OK);
    }




    // TODO: 5/31/2017 considerRefactoring
    @Override
    public Set<Integer> getFreeSeats(Flight flight) {
        List<Integer> notFreeSeatsNumbers= ticketRepository.getNotFreeSeatsNumbers(flight.getId());
        Set<Integer> freeSeats = new HashSet<>();

        for (int i = 1; i <= flight.getAircraft().getModel().getPassengersSeatsQuantity(); i++) {
            if (!notFreeSeatsNumbers.contains(i)){
                freeSeats.add(i);
            }
        }

        return freeSeats;
    }

    // TODO: 5/30/2017 remove annotation?
    // TODO: 6/1/2017 Should i delete unnecessary ifNotPaid checking?
    @Async
    public ScheduledFuture getDeleteInNotPaidTask(long ticketId) {
        ScheduledExecutorService localExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduler = new ConcurrentTaskScheduler(localExecutor);

        return scheduler.schedule(() -> ticketRepository.deleteIfNotPaid(ticketId),
                new Date(System.currentTimeMillis() + BOOKING_DELAY_MILLIS));
    }

    // TODO: 5/30/2017 make it transactional??
    // TODO: 5/30/2017 consider splitting into several methods
    @Override
    public Ticket createNewBookedTicketAndScheduledTask(Ticket newTicket) {

        Integer ticketQuantity = ticketRepository.countForFlight(newTicket.getFlight().getId());

        Ticket bookedTicket;

        if (ticketQuantity >= newTicket.getFlight().getAircraft().getModel().getPassengersSeatsQuantity()) {
            bookedTicket = null;
        }else{
            newTicket.setStatus(TicketStatus.BOOKED);
            bookedTicket = save(newTicket, AuthorizedUser.id());
        }

        if (bookedTicket != null){
            ticketTaskMap.put(bookedTicket.getId(), getDeleteInNotPaidTask(bookedTicket.getId()));
        }
        return bookedTicket;
    }

    @Override
    public Ticket save(Ticket ticket, long userId) {
        Assert.notNull(ticket, "ticket should not be null");
        return ticketRepository.save(ticket, userId);
    }

    @Override
    public Ticket update(Ticket ticket, long userId) {
        // TODO: 5/5/2017 get rid of message  duplicating
        Assert.notNull(ticket, "ticket should not be null");
        return checkNotFoundWithId(ticketRepository.save(ticket, userId), ticket.getId());
    }

    @Override
    public Ticket get(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id, userId), id);
    }

    @Override
    public Ticket getWithUser(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id, userId, Ticket.WITH_USER), id);
    }

    @Override
    public Ticket getWithFlight(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id, userId, Ticket.WITH_FLIGHT), id);
    }

    @Override
    public Ticket getWithUserAndFlight(long id, long userId) {
        return checkNotFoundWithId(ticketRepository.get(id, userId, Ticket.WITH_USER_AND_FLIGHT), id);
    }

    @Override
    public void delete(long id, long userId) {
        checkNotFoundWithId(ticketRepository.delete(id, userId), id);
    }

    @Override
    public List<Ticket> getAll(long userId) {
        return ticketRepository.getAll(userId);
    }

    /**
     * Method imitates withdrawal processing using bank API
     * @param userId
     * @return true if payment was successful, true if bank returned fail status
     */
    private boolean getWithdrawalStatus(long userId, BigDecimal price) {
        return true;
    }

}
