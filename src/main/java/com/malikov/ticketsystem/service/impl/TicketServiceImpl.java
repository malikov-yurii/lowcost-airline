package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Yurii Malikov
 */
@Service("ticketService")
public class TicketServiceImpl implements ITicketService {

    @Autowired
    private ITicketRepository repository;

    @Override
    public Ticket save(Ticket ticket, long userId) {
        Assert.notNull(ticket, "ticket should not be null");
        return repository.save(ticket, userId);
    }

    @Override
    public Ticket update(Ticket ticket, long userId) {
        // TODO: 5/5/2017 get rid of message  duplicating
        Assert.notNull(ticket, "ticket should not be null");
        return checkNotFoundWithId(repository.save(ticket, userId), ticket.getId());
    }

    @Override
    public Ticket get(long id, long userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public Ticket getWithUser(long id, long userId) {
        return checkNotFoundWithId(repository.get(id, userId, Ticket.WITH_USER), id);
    }

    @Override
    public Ticket getWithFlight(long id, long userId) {
        return checkNotFoundWithId(repository.get(id, userId, Ticket.WITH_FLIGHT), id);
    }

    @Override
    public Ticket getWithUserAndFlight(long id, long userId) {
        return checkNotFoundWithId(repository.get(id, userId, Ticket.WITH_USER_AND_FLIGHT), id);
    }

    @Override
    public void delete(long id, long userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Ticket> getAll(long userId) {
        return repository.getAll(userId);
    }

}
