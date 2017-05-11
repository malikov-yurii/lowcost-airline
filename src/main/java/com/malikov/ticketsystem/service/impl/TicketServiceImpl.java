package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("ticketService")
public class TicketServiceImpl implements ITicketService {

    @Autowired
    private ITicketRepository repository;

    @Override
    public Ticket save(Ticket ticket) {
        Assert.notNull(ticket, "ticket should not be null");
        // TODO: 5/5/2017 prepare ticket to save
        return repository.save(ticket);
    }

    @Override
    public void update(Ticket ticket) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save ticket
        Assert.notNull(ticket, "ticket should not be null");
        repository.save(ticket);
    }

    @Override
    public Ticket get(long id, String... hintNames) {
        // TODO: 5/5/2017 check not found with id
        return repository.get(id, hintNames);
    }

    @Override
    public List<Ticket> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

}
