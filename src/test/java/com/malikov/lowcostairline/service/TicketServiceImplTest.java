package com.malikov.lowcostairline.service;

import com.malikov.lowcostairline.model.Ticket;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

import static com.malikov.lowcostairline.FlightTestData.FLIGHT_1;
import static com.malikov.lowcostairline.TicketTestData.*;
import static com.malikov.lowcostairline.UserTestData.USER_1;

/**
 * @author Yurii Malikov
 */
public class TicketServiceImplTest extends AbstractServiceTest {

    @Autowired
    protected ITicketService service;

    @Test
    public void save() throws Exception {
        Ticket newTicket = getNewDummyTicketWithNullId(null);
        Ticket created = service.save(newTicket);
        newTicket.setId(created.getId());
        MATCHER.assertCollectionEquals(
                getTestListIncluding(newTicket),
                service.getAll());
    }

    @Test
    public void update() throws Exception {
        Ticket updated = new Ticket(TICKET_2);
        updated.setPrice(updated.getPrice().add(new BigDecimal("1.00")));
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(updated.getId()));
    }

    @Test
    public void getWithUserAndFlight() throws Exception {
        Ticket ticket = service.get(TICKET_3.getId());
        MATCHER.assertEquals(TICKET_3, ticket);
    }

    @Test
    public void get() throws Exception {
        Ticket ticket = service.get(TICKET_3.getId());
        MATCHER.assertEquals(TICKET_3, ticket);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(TICKETS, service.getAll());
    }

    public void delete() throws Exception {
        service.delete(TICKET_2.getId());
        MATCHER.assertCollectionEquals(getTestListExcluding(TICKET_2), service.getAll());
    }

    private Collection<Ticket> getTestListExcluding(Ticket ticket) {
        ArrayList<Ticket> resultList = new ArrayList<>(TICKETS);
        resultList.remove(ticket);
        return resultList;
    }

    private Ticket getNewDummyTicketWithNullId(Long id) {
        return new Ticket(id, FLIGHT_1, USER_1, new BigDecimal("40.00"), OffsetDateTime.parse("2017-03-20T08:33+01:00"), true, true, 1);
    }

    private ArrayList<Ticket> getTestListIncluding(Ticket newTicket) {
        ArrayList<Ticket> resultList = new ArrayList<>(TICKETS);
        resultList.add(newTicket);
        return resultList;
    }

}
