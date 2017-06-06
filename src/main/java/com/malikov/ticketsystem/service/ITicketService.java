package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketPriceDetailsDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.util.exception.NotFoundException;

import java.time.OffsetDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface ITicketService {

    /**
     * Updates ticket using data from ticketDTO
     * @throws NotFoundException it not found
     */
    void update(TicketDTO ticketDTO) throws NotFoundException;

    /**
     * Removes ticket with provided id
     * @throws NotFoundException if not found with id
     */
    void delete(long ticketId) throws NotFoundException;

    /**
     * @return persisted ticket or null if seat is now free already
     */
    Ticket createNewBookedTicketAndScheduledTask(TicketDTO ticketDTO, long flightId,
                                                 TicketPriceDetailsDTO ticketPriceDetailsDTO);

    /**
     * @param purchaseOffsetDateTime has information about local datetime and offset from place
     *                               where ticket has been purchased
     * @throws NotFoundException if not found ticket with id
     */
    void processPaymentByUser(Long ticketId, OffsetDateTime purchaseOffsetDateTime) throws NotFoundException;


    /**
     * Cancel booking of ticket with provided id
     */
    void cancelBooking(Long ticketId);

    /**
     * @param email method searches for ticket of User with email
     * @param start enforce query ignore particular quantity of first results in list.
     *              (first element is resultList[start])
     * @param limit indicates how many objects from result list required
     *             (last element is resultList[start + limit]
     * @return filtered and limited list of tickets for user with provided email
     */
    List<Ticket> getByUserEmail(String email, Integer start, Integer limit);

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
    List<TicketWithRemainingDelayDTO> getActiveTicketsDelays(long userId, Integer start, Integer limit);

    /**
     * @param userId method searches for ticket of User with userId
     * @param start enforce query ignore particular quantity of first results in list.
     *              (first element is resultList[start])
     * @param limit indicates how many objects from result list required
     *             (last element is resultList[start + limit]
     * @return active tickets (which departure datetime is <b>before</b> current moment)
     */
    List<TicketDTO> getArchivedTickets(Long userId, Integer start, Integer limit);
}
