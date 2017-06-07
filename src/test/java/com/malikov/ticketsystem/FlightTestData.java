package com.malikov.ticketsystem;

import com.malikov.ticketsystem.matchers.ModelMatcher;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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
            DateTimeUtil.parseToLocalDateTime("2017-06-30 07:30"), DateTimeUtil.parseToLocalDateTime("2017-06-30 11:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_2 = new Flight(2L, AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
            DateTimeUtil.parseToLocalDateTime("2017-06-23 07:30"), DateTimeUtil.parseToLocalDateTime("2017-06-23 11:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_3 = new Flight(3L, AIRPORT_2_HEATHROW, AIRPORT_1_BORISPOL, AIRCRAFT_1,
            DateTimeUtil.parseToLocalDateTime("2017-06-27 11:30"), DateTimeUtil.parseToLocalDateTime("2017-06-27 15:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_4 = new Flight(4L, AIRPORT_1_BORISPOL, AIRPORT_3_LUTON, AIRCRAFT_2,
            DateTimeUtil.parseToLocalDateTime("2017-06-22 06:30"), DateTimeUtil.parseToLocalDateTime("2017-06-22 08:00"),
            new BigDecimal("40.00"), new BigDecimal("60.00"));

    public static final Flight FLIGHT_5 = new Flight(5L, AIRPORT_3_LUTON, AIRPORT_1_BORISPOL, AIRCRAFT_2,
            DateTimeUtil.parseToLocalDateTime("2017-06-26 12:00"), DateTimeUtil.parseToLocalDateTime("2017-06-26 15:30"),
            new BigDecimal("40.00"), new BigDecimal("60.00"));

    public static final Flight FLIGHT_6 = new Flight(6L, AIRPORT_4_DA_VINCI, AIRPORT_1_BORISPOL, AIRCRAFT_3,
            DateTimeUtil.parseToLocalDateTime("2017-06-27 12:00"), DateTimeUtil.parseToLocalDateTime("2017-06-27 13:00"),
            new BigDecimal("20.00"), new BigDecimal("40.00"));

    public static final int FLIGHT_4_TICKET_QUANTITY = 4;

    public static final List<Flight> FLIGHTS = Arrays.asList(FLIGHT_1, FLIGHT_2, FLIGHT_3, FLIGHT_4, FLIGHT_5, FLIGHT_6);

    // TODO: 5/15/2017 Use patterng dto get rid of dublicating??????????????
    public static final ModelMatcher<Flight> FLIGHT_MATCHER = ModelMatcher.of(Flight.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDepartureAirport(), actual.getDepartureAirport())
                            && Objects.equals(expected.getArrivalAirport(), actual.getArrivalAirport())
                            && Objects.equals(expected.getAircraft(), actual.getAircraft())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getInitialTicketBasePrice(), actual.getInitialTicketBasePrice())
                            && Objects.equals(expected.getMaxTicketBasePrice(), actual.getMaxTicketBasePrice()))
                            && Objects.equals(expected.isCanceled(), actual.isCanceled()));

    public static final ModelMatcher<Flight> FLIGHT_WITH_TICKETS_MATCHER = ModelMatcher.of(Flight.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDepartureAirport(), actual.getDepartureAirport())
                            && Objects.equals(expected.getArrivalAirport(), actual.getArrivalAirport())
                            && Objects.equals(expected.getAircraft(), actual.getAircraft())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getInitialTicketBasePrice(), actual.getInitialTicketBasePrice())
                            && Objects.equals(expected.getMaxTicketBasePrice(), actual.getMaxTicketBasePrice())
                            && Objects.equals(expected.isCanceled(), actual.isCanceled())
                            && Objects.equals(expected.getTickets(), actual.getTickets())));

}
