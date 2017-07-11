package com.malikov.ticketsystem.repository;

/*

*/
/**
 * @author Yurii Malikov
 *//*

public class FlightRepositoryImplTest extends AbstractRepositoryTest {

    @Autowired
    private FlightRepository flightRepository;

    //@After
    //public void cleanUpAspects() {
    //    AnnotationTransactionAspect.aspectOf().setTransactionManager(null);
    //}

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
*/
