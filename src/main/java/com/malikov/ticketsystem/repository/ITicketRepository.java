package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Ticket;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface ITicketRepository extends IGenericRepository<Ticket> {

    boolean deleteIfNotPaid(long ticketId);

    // TODO: 5/30/2017 make result int?????????
    Integer countForFlight(Long flightId);

    List<Ticket> getAllByUserId(long userId);

    List<Integer> getNotFreeSeatsNumbers(Long flightId);

    List<Ticket> getByEmail(String email, Integer start, Integer limit);

    List<Ticket> getActiveByUserId(long userId, Integer start, Integer limit);

    List<Ticket> getArchivedByUserId(Long userId, Integer start, Integer limit);
}
