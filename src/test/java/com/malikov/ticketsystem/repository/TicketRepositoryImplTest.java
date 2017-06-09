package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.service.impl.FlightServiceImplTest;
import mockit.Expectations;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.malikov.ticketsystem.FlightTestData.FLIGHT_4;
import static com.malikov.ticketsystem.SharedTestData.NOT_LIMITED;
import static com.malikov.ticketsystem.SharedTestData.STARTING_FROM_FIRST;
import static com.malikov.ticketsystem.TicketTestData.*;
import static com.malikov.ticketsystem.UserTestData.USER_2;
import static org.junit.Assert.assertEquals;

/**
 * @author Yurii Malikov
 */
public class TicketRepositoryImplTest extends AbstractRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger(FlightServiceImplTest.class);

    @Autowired
    private ITicketRepository ticketRepository;

    @Test
    public void testGetActiveByUserId() {
        new Expectations(LocalDateTime.class) {{
            LocalDateTime.now(ZoneId.of("UTC")); result = BEFORE_USER_2_LAST_TICKET_DEPARTURE_DATE_TIME;
        }};
        LOG.info("LocalDateTime.now()=" + LocalDateTime.now());

        List<Ticket> expected = Collections.singletonList(USER_2_LAST_TICKET);
        List<Ticket> actual = ticketRepository.getActiveByUserId(USER_2.getId(),
                STARTING_FROM_FIRST, NOT_LIMITED);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetArchivedByUserId() {
        new Expectations(LocalDateTime.class) {{
            LocalDateTime.now(ZoneId.of("UTC")); result = AFTER_USER_2_FIRST_TICKET_DEPARTURE_DATE_TIME;
        }};
        LOG.info("LocalDateTime.now()=" + LocalDateTime.now());

        List<Ticket> expected = Collections.singletonList(USER_2_FIRST_TICKET);
        List<Ticket> actual = ticketRepository.getArchivedByUserId(USER_2.getId(),
                STARTING_FROM_FIRST, NOT_LIMITED);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetOccupiedSeatNumbers() {
        List<Integer> expected = Arrays.asList(FLIGHT_4_USER_6_TICKET.getSeatNumber(),
                FLIGHT_4_USER_7_TICKET.getSeatNumber());

        List<Integer> actual = ticketRepository.getOccupiedSeatNumbers(FLIGHT_4.getId());

        assertEquals(expected, actual);
    }

    @Test
    public void testCountTickets() {
        Integer expected = FLIGHT_4_TICKET_QUANTITY;
        Integer actual = ticketRepository.countTickets(FLIGHT_4.getId());

        assertEquals(expected, actual);
    }
}
