package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Ticket;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface ITicketRepository {

    // null if updated ticket do not belong to userId
    Ticket save(Ticket ticket, long userId);

    // false if ticket do not belong to userId
    boolean delete(long id, long userId);

    // null if ticket do not belong to userId
    Ticket get(long id, long userId, String... hintNames);

    List<Ticket> getAll(long userId);



}
