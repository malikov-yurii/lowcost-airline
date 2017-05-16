package com.malikov.ticketsystem;

import com.malikov.ticketsystem.matchers.ModelMatcher;
import com.malikov.ticketsystem.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.malikov.ticketsystem.AircraftTestData.*;
import static com.malikov.ticketsystem.AirportTestData.*;

/**
 * @author Yurii Malikov
 */
public class FlightTestData {

    private static final Logger LOG = LoggerFactory.getLogger(FlightTestData.class);

    public static final Flight FLIGHT_1 = new Flight(1L, AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
            LocalDateTime.parse("2017-04-30T07:30:00"), LocalDateTime.parse("2017-04-30T11:00:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_2 = new Flight(2L, AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
            LocalDateTime.parse("2017-04-23T07:30:00"), LocalDateTime.parse("2017-04-23T11:00:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_3 = new Flight(3L, AIRPORT_2_HEATHROW, AIRPORT_1_BORISPOL, AIRCRAFT_1,
            LocalDateTime.parse("2017-04-27T11:30:00"), LocalDateTime.parse("2017-04-27T15:00:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_4 = new Flight(4L, AIRPORT_1_BORISPOL, AIRPORT_3_LUTON, AIRCRAFT_2,
            LocalDateTime.parse("2017-04-22T06:30:00"), LocalDateTime.parse("2017-04-22T08:00:00"),
            new BigDecimal("40.00"), new BigDecimal("60.00"));

    public static final Flight FLIGHT_5 = new Flight(5L, AIRPORT_3_LUTON, AIRPORT_1_BORISPOL, AIRCRAFT_2,
            LocalDateTime.parse("2017-04-26T12:00:00"), LocalDateTime.parse("2017-04-26T15:30:00"),
            new BigDecimal("40.00"), new BigDecimal("60.00"));

    public static final Flight FLIGHT_6 = new Flight(6L, AIRPORT_4_DA_VINCI, AIRPORT_1_BORISPOL, AIRCRAFT_3,
            LocalDateTime.parse("2017-04-27T12:00:00"), LocalDateTime.parse("2017-04-27T13:00:00"),
            new BigDecimal("20.00"), new BigDecimal("40.00"));

    public static final List<Flight> FLIGHTS = Arrays.asList(FLIGHT_1, FLIGHT_2, FLIGHT_3, FLIGHT_4, FLIGHT_5, FLIGHT_6);

    // TODO: 5/15/2017 Use patterng to get rid of dublicating??????????????
    public static final ModelMatcher<Flight> FLIGHT_MATCHER = ModelMatcher.of(Flight.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDepartureAirport(), actual.getDepartureAirport())
                            && Objects.equals(expected.getArrivalAirport(), actual.getArrivalAirport())
                            && Objects.equals(expected.getAircraft(), actual.getAircraft())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getStartTicketBasePrice(), actual.getStartTicketBasePrice())
                            && Objects.equals(expected.getMaxTicketBasePrice(), actual.getMaxTicketBasePrice())));

    public static final ModelMatcher<Flight> FLIGHT_WITH_TICKETS_MATCHER = ModelMatcher.of(Flight.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDepartureAirport(), actual.getDepartureAirport())
                            && Objects.equals(expected.getArrivalAirport(), actual.getArrivalAirport())
                            && Objects.equals(expected.getAircraft(), actual.getAircraft())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getStartTicketBasePrice(), actual.getStartTicketBasePrice())
                            && Objects.equals(expected.getMaxTicketBasePrice(), actual.getMaxTicketBasePrice())
                            && Objects.equals(expected.getTickets(), actual.getTickets())));

}
