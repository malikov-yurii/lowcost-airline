package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Flight;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static com.malikov.ticketsystem.AircraftTestData.AIRCRAFT_1;
import static com.malikov.ticketsystem.AirportTestData.AIRPORT_1_BORISPOL;
import static com.malikov.ticketsystem.AirportTestData.AIRPORT_3_LUTON;
import static com.malikov.ticketsystem.FlightTestData.*;

/**
 * @author Yurii Malikov
 */
public class FlightServiceTest extends AbstractServiceTest {

    @Autowired
    protected IFlightService service;

    @Test
    public void save() throws Exception {
        Flight newFlight = getNewDummyFlightWithNullId(null);
        Flight created = service.save(newFlight);
        newFlight.setId(created.getId());
        MATCHER.assertCollectionEquals(
                getTestDataFlightsWith(newFlight),
                service.getAll());
    }

    @Test
    public void update() throws Exception {
        Flight updated = new Flight(FLIGHT_2);
        updated.setArrivalAirport(AIRPORT_3_LUTON);
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(updated.getId()));
    }

    @Test
    public void get() throws Exception {
        Flight flight = service.get(FLIGHT_3.getId());
        MATCHER.assertEquals(FLIGHT_3, flight);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(FLIGHTS, service.getAll());
    }

    public void delete() throws Exception {
        service.delete(FLIGHT_4.getId());
        MATCHER.assertCollectionEquals(
                Arrays.asList(FLIGHT_1, FLIGHT_2, FLIGHT_3, FLIGHT_5, FLIGHT_6),
                service.getAll());

    }

    private Flight getNewDummyFlightWithNullId(Long id) {
        return new Flight(id, AIRPORT_3_LUTON, AIRPORT_1_BORISPOL, AIRCRAFT_1,
                LocalDateTime.parse("2017-04-23T12:00:00"), LocalDateTime.parse("2017-04-23T16:00:00"),
                new BigDecimal("50.00"), new BigDecimal("70.00"));
    }

    private ArrayList<Flight> getTestDataFlightsWith(Flight newFlight) {
        ArrayList<Flight> flightsWithNewFlight = new ArrayList<>(FLIGHTS);
        flightsWithNewFlight.add(newFlight);
        return flightsWithNewFlight;
    }

}
