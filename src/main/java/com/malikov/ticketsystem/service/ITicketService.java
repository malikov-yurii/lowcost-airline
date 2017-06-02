package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Yurii Malikov
 */
public interface ITicketService {

    Ticket save(Ticket ticket, long userId);

    Ticket update(Ticket ticket, long userId);

    // TODO: 5/8/2017 Should name them properties or hints
    Ticket get(long id, long userId);

    List<Ticket> getAll(long userId);

    void delete(long id, long userId);

    Ticket getWithUser(long id, long userId);

    Ticket getWithFlight(long id, long userId);

    Ticket getWithUserAndFlight(long id, long userId);

    // TODO: 5/30/2017 add userid here

    /**
     * @param newTicket
     * @return persisted ticket or null if seat is now free
     */
    Ticket createNewBookedTicketAndScheduledTask(Ticket newTicket);

    // TODO: 5/30/2017 add userid here
    ResponseEntity processPayment(Long ticketId, OffsetDateTime purchaseOffsetDateTime);

    Set<Integer> getFreeSeats(Flight flight);

    boolean cancelBooking(Long ticketId, long userId);


}
