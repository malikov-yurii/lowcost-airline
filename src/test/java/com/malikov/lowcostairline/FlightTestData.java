package com.malikov.lowcostairline;

import com.malikov.lowcostairline.matchers.ModelMatcher;
import com.malikov.lowcostairline.model.Flight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.malikov.lowcostairline.AircraftTestData.AIRCRAFT_1;
import static com.malikov.lowcostairline.AircraftTestData.AIRCRAFT_2;
import static com.malikov.lowcostairline.AircraftTestData.AIRCRAFT_3;
import static com.malikov.lowcostairline.AirportTestData.*;

/**
 * @author Yurii Malikov
 */
public class FlightTestData {

    private static final Logger LOG = LoggerFactory.getLogger(FlightTestData.class);

    public static final Flight FLIGHT_1 = new Flight(1L, AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
            LocalDateTime.parse("2017-04-30T10:30:00"), LocalDateTime.parse("2017-04-30T12:00:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_2 = new Flight(2L, AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
            LocalDateTime.parse("2017-04-23T10:30:00"), LocalDateTime.parse("2017-04-23T12:00:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_3 = new Flight(3L, AIRPORT_2_HEATHROW, AIRPORT_1_BORISPOL, AIRCRAFT_1,
            LocalDateTime.parse("2017-04-27T12:30:00"), LocalDateTime.parse("2017-04-27T18:00:00"),
            new BigDecimal("30.00"), new BigDecimal("50.00"));

    public static final Flight FLIGHT_4 = new Flight(4L, AIRPORT_1_BORISPOL, AIRPORT_3_LUTON, AIRCRAFT_2,
            LocalDateTime.parse("2017-04-22T09:30:00"), LocalDateTime.parse("2017-04-22T09:00:00"),
            new BigDecimal("40.00"), new BigDecimal("60.00"));

    public static final Flight FLIGHT_5 = new Flight(5L, AIRPORT_3_LUTON, AIRPORT_1_BORISPOL, AIRCRAFT_2,
            LocalDateTime.parse("2017-04-26T13:00:00"), LocalDateTime.parse("2017-04-26T18:30:00"),
            new BigDecimal("40.00"), new BigDecimal("60.00"));

    public static final Flight FLIGHT_6 = new Flight(6L, AIRPORT_4_DA_VINCI, AIRPORT_1_BORISPOL, AIRCRAFT_3,
            LocalDateTime.parse("2017-04-27T14:00:00"), LocalDateTime.parse("2017-04-27T16:00:00"),
            new BigDecimal("20.00"), new BigDecimal("40.00"));

    public static final List<Flight> FLIGHTS = Arrays.asList(FLIGHT_1, FLIGHT_2, FLIGHT_3, FLIGHT_4, FLIGHT_5, FLIGHT_6);


    public static final ModelMatcher<Flight> MATCHER = ModelMatcher.of(Flight.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getDepartureAirport(), actual.getDepartureAirport())
                            && Objects.equals(expected.getArrivalAirport(), actual.getArrivalAirport())
                            && Objects.equals(expected.getAircraft(), actual.getAircraft())
                            && Objects.equals(expected.getDepartureLocalDateTime(), actual.getDepartureLocalDateTime())
                            && Objects.equals(expected.getStartTicketBasePrice(), actual.getStartTicketBasePrice())
                            && Objects.equals(expected.getMaxTicketBasePrice(), actual.getMaxTicketBasePrice())));

}
