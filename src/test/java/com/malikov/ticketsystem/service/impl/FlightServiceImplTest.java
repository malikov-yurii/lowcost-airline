package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AbstractTest;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.malikov.ticketsystem.AirportTestData.AIRPORT_BORISPOL;
import static com.malikov.ticketsystem.AirportTestData.AIRPORT_LUTON;
import static com.malikov.ticketsystem.FlightTestData.FLIGHT_4;
import static com.malikov.ticketsystem.TariffDetailsTestData.ACTIVE_TARIFF_DETAILS;
import static com.malikov.ticketsystem.TicketTestData.FLIGHT_4_TICKET_QUANTITY;
import static java.math.RoundingMode.HALF_UP;
import static org.mockito.Mockito.when;

/**
 * @author Yurii Malikov
 */
@RunWith(MockitoJUnitRunner.class)
public class FlightServiceImplTest extends AbstractTest{

    private static final Logger LOG = LoggerFactory.getLogger(FlightServiceImplTest.class);
    public static final LocalDateTime FIXED_DATE_TIME = LocalDateTime.of(2017, 6, 17, 12, 0);

    @Mock
    private IFlightRepository flightRepository;

    @Mock
    private ITariffsDetailsRepository tariffsDetailsRepository;

    @Mock
    private ITicketRepository ticketRepository;

    @Mock
    private IAirportRepository airportRepository;

    @Mock
    MessageSource messageSource;

    @InjectMocks
    private IFlightService flightService = new FlightServiceImpl();


    @Before
    public void initializeMockito(){
        MockitoAnnotations.initMocks(this);

        new Expectations(LocalDateTime.class) {{ LocalDateTime.now(ZoneId.of("UTC")); result = FIXED_DATE_TIME; }};
        LOG.info("LocalDateTime.now()=" + LocalDateTime.now() + ". FIXED_DATE_TIME = " + FIXED_DATE_TIME);

        when(tariffsDetailsRepository.getActiveTariffsDetails()).thenReturn(ACTIVE_TARIFF_DETAILS);
    }

    @Test
    public void testGetTicketPriceDetails() {
        when(messageSource.getMessage(Mockito.anyString(), Mockito.any(), Mockito.any(Locale.class)))
                .thenReturn("dummy text");
        when(ticketRepository.countTickets(FLIGHT_4.getId())).thenReturn(FLIGHT_4_TICKET_QUANTITY);
        when(flightRepository.get(FLIGHT_4.getId())).thenReturn(FLIGHT_4);

        TicketPriceDetailsDTO actual = flightService.getTicketPriceDetails(FLIGHT_4.getId());
        TicketPriceDetailsDTO expected = new TicketPriceDetailsDTO(new BigDecimal(47).setScale(6, HALF_UP),
                new BigDecimal(62).setScale(6, HALF_UP), new BigDecimal(7).setScale(6, HALF_UP));

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetFlightTicketPriceMap(){
        when(messageSource.getMessage(Mockito.anyString(), Mockito.any(), Mockito.any(Locale.class)))
                .thenReturn("dummy text");
        when(airportRepository.getByName(AIRPORT_BORISPOL.getName())).thenReturn(AIRPORT_BORISPOL);
        when(airportRepository.getByName(AIRPORT_LUTON.getName())).thenReturn(AIRPORT_LUTON);
        when(flightRepository.getFilteredFlightTicketCountMap(AIRPORT_BORISPOL, AIRPORT_LUTON,
                        DateTimeUtil.zoneIdToUtc(DateTimeUtil.MIN, AIRPORT_BORISPOL.getCity().getZoneId()),
                        DateTimeUtil.zoneIdToUtc(DateTimeUtil.MAX, AIRPORT_BORISPOL.getCity().getZoneId()),
                        0, 10))
                .thenReturn(new HashMap<Flight, Long>() {{ put(FLIGHT_4, 2L); }});

        Map<Flight, BigDecimal> expected = new HashMap<Flight, BigDecimal>() {{
            put(FLIGHT_4, new BigDecimal(47).setScale(6, HALF_UP)); }};

        Map<Flight, BigDecimal> actual = flightService.getFlightTicketPriceMap(AIRPORT_BORISPOL.getName(),
                AIRPORT_LUTON.getName(),DateTimeUtil.MIN, DateTimeUtil.MAX, 0, 10);

        Assert.assertEquals(expected, actual);
    }
}
