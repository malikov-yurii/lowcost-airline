package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Ticket;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface ITicketRepository {

    // null if updated ticket do not belong dto userId
    Ticket save(Ticket ticket);

    // false if ticket do not belong dto userId
    boolean delete(long id);

    // null if ticket do not belong dto userId
    Ticket get(long id, String... hintNames);

    List<Ticket> getAll(long userId);


    boolean deleteIfNotPaid(long ticketId);

    // TODO: 5/30/2017 make result int?????????
    Integer countForFlight(Long flightId);


    List<Integer> getNotFreeSeatsNumbers(Long flightId);

    List<Ticket> getByEmail(String email, Integer start, Integer limit);

    List<Ticket> getActiveByUserId(long userId, Integer start, Integer limit);

    List<Ticket> getArchivedByUserId(Long userId, Integer start, Integer limit);
}
