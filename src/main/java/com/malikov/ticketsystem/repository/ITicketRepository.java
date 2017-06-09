package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Ticket;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface ITicketRepository extends IGenericRepository<Ticket> {

    /**
     * Checks if ticket has status booked before update
     * @return false if not found
     */
    boolean deleteIfNotPaid(long ticketId);


    /**
     * @return quantity of booked or purchased tickets for flight
     */
    Integer countTickets(Long flightId);

    /**
     * @return numbers of not free seats (which have booked of purchased status) for flight
     */
    List<Integer> getOccupiedSeatNumbers(Long flightId);

    /**
     * @param start excludes from result list first tickets
     * @param limit excludes from result list tickets next to tickets[start + limit]
     * @return filtered by user email, limited by conditions tickets
     *              Or returns empty list if not found any.
     */
    List<Ticket> getByEmail(String userEmail, Integer start, Integer limit);

    /**
     * @param start excludes from result list first tickets
     * @param limit excludes from result list tickets next to tickets[start + limit]
     * @return filtered by user id, limited by conditions active (which flight departure is after current moment)
     *          tickets. Or returns empty list if not found any.
     */
    List<Ticket> getActiveByUserId(long userId, Integer start, Integer limit);

    /**
     * @param start excludes from result list first tickets
     * @param limit excludes from result list tickets next to tickets[start + limit]
     * @return filtered by user id, limited by conditions archived (which flight departure was before current moment)
     *          tickets. Or returns empty list if not found any.
     */
    List<Ticket> getArchivedByUserId(Long userId, Integer start, Integer limit);
}
