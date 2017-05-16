package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.service.AbstractServiceTest;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static com.malikov.ticketsystem.AircraftTestData.AIRCRAFT_1;
import static com.malikov.ticketsystem.AirportTestData.*;
import static com.malikov.ticketsystem.FlightTestData.*;
import static com.malikov.ticketsystem.TicketTestData.TICKET_7;
import static com.malikov.ticketsystem.TicketTestData.TICKET_8;

/**
 * @author Yurii Malikov
 */
public class FlightServiceImplTest extends AbstractServiceTest {

    @Autowired
    protected IFlightService service;

    @Test
    public void save() throws Exception {
        Flight newFlight = getNewDummyFlightWithNullId(null);
        Flight created = service.save(newFlight);
        newFlight.setId(created.getId());
        FLIGHT_MATCHER.assertCollectionEquals(
                Arrays.asList(FLIGHT_1, FLIGHT_2, newFlight),
                service.getAllBetween(AIRPORT_1_BORISPOL.getId(), AIRPORT_2_HEATHROW.getId(),
                        DateTimeUtil.MIN, DateTimeUtil.MAX));
    }

    @Test
    public void update() throws Exception {
        Flight updated = new Flight(FLIGHT_2);
        updated.setArrivalAirport(AIRPORT_3_LUTON);
        service.update(updated);
        FLIGHT_MATCHER.assertEquals(updated, service.get(updated.getId()));
    }

    @Test
    public void get() throws Exception {
        Flight flight = service.get(FLIGHT_3.getId());
        FLIGHT_MATCHER.assertEquals(FLIGHT_3, flight);
    }

    @Test
    public void getWithTickets() throws Exception {
        Flight expectedFlightWithTickets = new Flight(FLIGHT_4);
        expectedFlightWithTickets.setTickets(Arrays.asList(TICKET_7, TICKET_8));

        Flight actualFlightWithTickets = service.getWithTickets(FLIGHT_4.getId());

        FLIGHT_WITH_TICKETS_MATCHER.assertEquals(expectedFlightWithTickets, actualFlightWithTickets);

    }

    //@Test
    //public void getAll() throws Exception {
    //    FLIGHT_MATCHER.assertCollectionEquals(FLIGHTS, service.getAll());
    //}

    @Test
    public void testGetAllFromAirportToAirport() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(Arrays.asList(FLIGHT_1, FLIGHT_2),
                service.getAllBetween(AIRPORT_1_BORISPOL.getId(), AIRPORT_2_HEATHROW.getId(),
                        DateTimeUtil.MIN, DateTimeUtil.MAX));
    }

    @Test
    public void testGetAllBetween() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(Collections.singletonList(FLIGHT_1),
                service.getAllBetween(AIRPORT_1_BORISPOL.getId(), AIRPORT_2_HEATHROW.getId(),
                        FLIGHT_2.getDepartureUtcDateTime().plusDays(1), DateTimeUtil.MAX));
    }

    @Test
    public void delete() throws Exception {
        service.delete(FLIGHT_2.getId());
        FLIGHT_MATCHER.assertCollectionEquals(
                Collections.singletonList(FLIGHT_1),
                service.getAllBetween(AIRPORT_1_BORISPOL.getId(), AIRPORT_2_HEATHROW.getId(),
                        DateTimeUtil.MIN, DateTimeUtil.MAX));

    }

    private Flight getNewDummyFlightWithNullId(Long id) {
        return new Flight(id, AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
                LocalDateTime.parse("2017-04-23T12:00:00"), LocalDateTime.parse("2017-04-23T16:00:00"),
                new BigDecimal("50.00"), new BigDecimal("70.00"));
    }

}
