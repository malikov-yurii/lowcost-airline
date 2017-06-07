package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.malikov.ticketsystem.AirportTestData.AIRPORT_BORISPOL;
import static com.malikov.ticketsystem.AirportTestData.AIRPORT_LUTON;
import static com.malikov.ticketsystem.FlightTestData.FLIGHT_4;
import static org.junit.Assert.assertEquals;

/**
 * @author Yurii Malikov
 */
public class FlightRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    private IFlightRepository flightRepository;

    @Test
    public void testGetFilteredFlightTicketCountMap(){
        Map<Flight, Long> expected = new HashMap<Flight, Long>() {{ put(FLIGHT_4, 2L); }};

        Map<Flight, Long> actual = flightRepository.getFilteredFlightTicketCountMap(AIRPORT_BORISPOL,
                AIRPORT_LUTON,
                DateTimeUtil.zoneIdToUtc(DateTimeUtil.MIN, AIRPORT_BORISPOL.getCity().getZoneId()),
                DateTimeUtil.zoneIdToUtc(DateTimeUtil.MAX, AIRPORT_BORISPOL.getCity().getZoneId()),
                0, 10);

        assertEquals(expected, actual);
    }

    @Test
    public void testGetFiltered(){

        List<Flight> expected = Collections.singletonList(FLIGHT_4);

        List<Flight> actual = flightRepository.getFiltered(AIRPORT_BORISPOL,
                AIRPORT_LUTON,
                DateTimeUtil.zoneIdToUtc(DateTimeUtil.MIN, AIRPORT_BORISPOL.getCity().getZoneId()),
                DateTimeUtil.zoneIdToUtc(DateTimeUtil.MAX, AIRPORT_BORISPOL.getCity().getZoneId()),
                0, 10);

        assertEquals(expected, actual);
    }
}
