package com.malikov.ticketsystem;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import static com.malikov.ticketsystem.AircraftTestData.*;
import static com.malikov.ticketsystem.AirportTestData.*;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author Yurii Malikov
 */
public class FlightTestData {

    private static final Logger LOG = LoggerFactory.getLogger(FlightTestData.class);

    public static final Flight FLIGHT_1 = new Flight(1L, AIRPORT_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
            DateTimeUtil.parseToLocalDateTime("2017-06-30 07:30"), DateTimeUtil.parseToLocalDateTime("2017-06-30 11:00"),
            new BigDecimal(30).setScale(6, HALF_UP), new BigDecimal(50).setScale(6, HALF_UP));

    public static final Flight FLIGHT_2 = new Flight(2L, AIRPORT_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
            DateTimeUtil.parseToLocalDateTime("2017-06-23 07:30"), DateTimeUtil.parseToLocalDateTime("2017-06-23 11:00"),
            new BigDecimal(30).setScale(6, HALF_UP), new BigDecimal(50).setScale(6, HALF_UP));

    public static final Flight FLIGHT_3 = new Flight(3L, AIRPORT_2_HEATHROW, AIRPORT_BORISPOL, AIRCRAFT_1,
            DateTimeUtil.parseToLocalDateTime("2017-06-27 11:30"), DateTimeUtil.parseToLocalDateTime("2017-06-27 15:00"),
            new BigDecimal(30).setScale(6, HALF_UP), new BigDecimal(50).setScale(6, HALF_UP));

    public static final Flight FLIGHT_4 = new Flight(4L, AIRPORT_BORISPOL, AIRPORT_LUTON, AIRCRAFT_2,
            DateTimeUtil.parseToLocalDateTime("2017-06-22 06:30"), DateTimeUtil.parseToLocalDateTime("2017-06-22 08:00"),
            new BigDecimal(40).setScale(6, HALF_UP), new BigDecimal(60).setScale(6, HALF_UP));

    public static final Flight FLIGHT_5 = new Flight(5L, AIRPORT_LUTON, AIRPORT_BORISPOL, AIRCRAFT_2,
            DateTimeUtil.parseToLocalDateTime("2017-06-26 12:00"), DateTimeUtil.parseToLocalDateTime("2017-06-26 15:30"),
            new BigDecimal(40).setScale(6, HALF_UP), new BigDecimal(60).setScale(6, HALF_UP));

    public static final Flight FLIGHT_6 = new Flight(6L, AIRPORT_4_DA_VINCI, AIRPORT_BORISPOL, AIRCRAFT_3,
            DateTimeUtil.parseToLocalDateTime("2017-06-27 12:00"), DateTimeUtil.parseToLocalDateTime("2017-06-27 13:00"),
            new BigDecimal(20).setScale(6, HALF_UP), new BigDecimal(40).setScale(6, HALF_UP));
}
