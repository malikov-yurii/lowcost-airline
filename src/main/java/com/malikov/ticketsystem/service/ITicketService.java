package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Ticket;

import java.util.List;

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

}
