package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.service.AbstractServiceTest;

/**
 * @author Yurii Malikov
 */
public class TicketServiceImplTest extends AbstractServiceTest {
/*

    @Autowired
    protected ITicketService service;

    @Test
    public void testSave() throws Exception {
        Ticket newTicket = getNewDummyTicketForUser(USER_2);
        Ticket created = service.create(newTicket, USER_2.getId());
        newTicket.setId(created.getId());
        MATCHER.assertCollectionEquals(
                Arrays.asList(TICKET_2_BELONGS_USER_2, TICKET_6_BELONGS_USER_2, newTicket),
                service.getAll(USER_2.getId()));
    }

    //@Test
    //public void testUpdate() throws Exception {
    //    Ticket updated = new Ticket(TICKET_2_BELONGS_USER_2);
    //    updated.setPrice(updated.getPrice().add(new BigDecimal("1.00")));
    //    service.update(updated);
    //    MATCHER.assertEquals(updated, service.get(updated.getId(), USER_2.getId()));
    //}

    @Test
    public void testGet() throws Exception {
        Ticket ticket = service.get(TICKET_3_BELONGS_USER_3.getId(), USER_3.getId());
        MATCHER.assertEquals(TICKET_3_BELONGS_USER_3, ticket);
    }

    //@Test
    //public void testGetWithUser() throws Exception {
    //    Ticket ticket = service.getWithUser(TICKET_3_BELONGS_USER_3.getId(), USER_3.getId());
    //    MATCHER_WITH_USER.assertEquals(TICKET_3_BELONGS_USER_3, ticket);
    //}
    //
    //@Test
    //public void testGetWithFlight() throws Exception {
    //    Ticket ticket = service.getWithFlight(TICKET_3_BELONGS_USER_3.getId(), USER_3.getId());
    //    MATCHER_WITH_FLIGHT.assertEquals(TICKET_3_BELONGS_USER_3, ticket);
    //}
    //
    //@Test
    //public void testGetWithUserAndFlight() throws Exception {
    //    Ticket ticket = service.getWithUserAndFlight(TICKET_3_BELONGS_USER_3.getId(), USER_3.getId());
    //    MATCHER_WITH_USER_AND_FLIGHT.assertEquals(TICKET_3_BELONGS_USER_3, ticket);
    //}

    @Test
    public void testGetAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(TICKET_2_BELONGS_USER_2, TICKET_6_BELONGS_USER_2),
                service.getAll(USER_2.getId()));
    }

    public void testDelete() throws Exception {
        service.delete(TICKET_2_BELONGS_USER_2.getId());
        MATCHER.assertCollectionEquals(Collections.singleton(TICKET_6_BELONGS_USER_2), service.getAll(USER_2.getId()));
    }

    //@Test
    //public void testUpdateNotFound() throws Exception {
    //    thrown.expect(NotFoundException.class);
    //    thrown.expectMessage("Not found entity with id=" + TICKET_2_BELONGS_USER_2.getId());
    //    service.update(TICKET_2_BELONGS_USER_2);
    //}

    private Ticket getNewDummyTicketForUser(User user) {
        return new Ticket(null, FLIGHT_1, user, user.getName(), user.getLastName(), new BigDecimal("40.00"),
                OffsetDateTime.parse("2017-03-20T08:33+01:00"), true, true,
                1, TicketStatus.PAID);
    }
*/

}
