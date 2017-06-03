package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
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

    Ticket update(TicketDTO ticketDTO);

    // TODO: 5/8/2017 Should name them properties or hints
    Ticket get(long id, long userId);

    List<Ticket> getAll(long userId);

    boolean delete(long id);

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

    boolean cancelBooking(Long ticketId);


    List<Ticket> getByUserEmail(String email, Integer startingFrom, Integer limit);

    /**
     * @param userId method searches for ticket of User with userId
     * @param start enforce query ignore particular quantity of first results in list.
     *              (first element is resultList[start])
     * @param limit indicates how many objects from result list required
     *             (last element is resultList[start + limit]
     * @return active tickets (which departure datetime is <b>after</b> current moment)
     *          with remaining delays in millis before tickets will be automatically removed
     *          if ticket has status BOOKED (or null if ticket status is not BOOKED)
     */
    List<TicketWithRemainingDelayDTO> getTicketsDelaysByUserId(long userId, Integer start, Integer limit);
}
