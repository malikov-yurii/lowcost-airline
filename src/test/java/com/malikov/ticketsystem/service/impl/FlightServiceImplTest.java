package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.dto.TicketPriceDetailsDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.repository.IAirportRepository;
import com.malikov.ticketsystem.repository.IFlightRepository;
import com.malikov.ticketsystem.repository.ITariffsDetailsRepository;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import mockit.Expectations;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.malikov.ticketsystem.AirportTestData.AIRPORT_1_BORISPOL;
import static com.malikov.ticketsystem.AirportTestData.AIRPORT_3_LUTON;
import static com.malikov.ticketsystem.FlightTestData.FLIGHT_4;
import static com.malikov.ticketsystem.FlightTestData.FLIGHT_4_TICKET_QUANTITY;
import static com.malikov.ticketsystem.TariffDetailsTestData.ACTIVE_TARIFF_DETAILS;
import static org.mockito.Mockito.when;

/**
 * @author Yurii Malikov
 */
@RunWith(MockitoJUnitRunner.class)
public class FlightServiceImplTest{

    private static final Logger LOG = LoggerFactory.getLogger(FlightServiceImplTest.class);
    public static final LocalDateTime FIXED_DATE_TIME = LocalDateTime.of(2017, 6, 17, 12, 0);

    @Mock
    IFlightRepository flightRepository;

    @Mock
    ITariffsDetailsRepository tariffsDetailsRepository;

    @Mock
    ITicketRepository ticketRepository;

    @Mock
    IAirportRepository airportRepository;

    @InjectMocks
    IFlightService flightService= new FlightServiceImpl();


    @Before
    public void initializeMockito(){
        MockitoAnnotations.initMocks(this);
        new Expectations(LocalDateTime.class) {{
            LocalDateTime.now(); result = FIXED_DATE_TIME;
        }};
        LOG.info("LocalDateTime.now()=" + LocalDateTime.now() + ". FIXED_DATE_TIME = " + FIXED_DATE_TIME);
        when(tariffsDetailsRepository.getActiveTariffsDetails()).thenReturn(ACTIVE_TARIFF_DETAILS);
    }

    @Test
    public void testGetTicketPriceDetails() {
        when(ticketRepository.countTickets(FLIGHT_4.getId())).thenReturn(FLIGHT_4_TICKET_QUANTITY);
        when(flightRepository.get(FLIGHT_4.getId())).thenReturn(FLIGHT_4);

        TicketPriceDetailsDTO actual = flightService.getTicketPriceDetails(FLIGHT_4.getId());
        TicketPriceDetailsDTO expected = new TicketPriceDetailsDTO(new BigDecimal(48), new BigDecimal(62),
                new BigDecimal(7));

        Assert.assertEquals(expected, actual);
    }

    // TODO: 6/7/2017 Code style ok?
    @Test
    public void testGetFlightTicketPriceMap(){
        when(airportRepository.getByName(AIRPORT_1_BORISPOL.getName())).thenReturn(AIRPORT_1_BORISPOL);
        when(airportRepository.getByName(AIRPORT_3_LUTON.getName())).thenReturn(AIRPORT_3_LUTON);
        when(flightRepository.getFilteredFlightTicketCountMap(AIRPORT_1_BORISPOL, AIRPORT_3_LUTON,
                        DateTimeUtil.zoneIdToUtc(DateTimeUtil.MIN, AIRPORT_1_BORISPOL.getCity().getZoneId()),
                        DateTimeUtil.zoneIdToUtc(DateTimeUtil.MAX, AIRPORT_1_BORISPOL.getCity().getZoneId()),
                        0, 10))
                .thenReturn(new HashMap<Flight, Long>() {{ put(FLIGHT_4, 4L); }});

        Map<Flight, BigDecimal> expected = new HashMap<Flight, BigDecimal>() {{
            put(FLIGHT_4, new BigDecimal("48.00")); }};

        Map<Flight, BigDecimal> actual = flightService.getFlightTicketPriceMap(AIRPORT_1_BORISPOL.getName(),
                AIRPORT_3_LUTON.getName(),DateTimeUtil.MIN, DateTimeUtil.MAX, 0, 10);

        Assert.assertEquals(expected, actual);
    }
}
