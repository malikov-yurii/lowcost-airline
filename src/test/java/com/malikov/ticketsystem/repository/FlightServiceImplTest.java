package com.malikov.ticketsystem.repository;

/**
 * @author Yurii Malikov
 */
public class FlightServiceImplTest extends AbstractServiceTest {
/*


    // TODO: 5/29/2017 Refactor
    @Autowired
    IFlightRepository repository;
     //TODO: 5/28/2017 transform dto repository integration test
    @Test
    public void getFlightTicketPriceMap() throws Exception {
        Map<Flight, Long> result = repository.getFilteredFlightTicketCountMap(AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW,
          //      FLIGHT_2.getDepartureUtcDateTime().minusMinutes(1L), FLIGHT_2.getDepartureUtcDateTime().plusMinutes(1L)
                DateTimeUtil.MIN, DateTimeUtil.MAX
                , 0, 10);
        System.out.println();
    }

    @Autowired
    protected IFlightService service;

    @Test
    public void create() throws Exception {
        Flight newFlight = getNewDummyFlightWithNullId(null);
        Flight created = service.create(newFlight);
        newFlight.setId(created.getId());
        FLIGHT_MATCHER.assertCollectionEquals(
                Arrays.asList(FLIGHT_1, FLIGHT_2, newFlight),
                service.getFiltered(AIRPORT_1_BORISPOL.getName(), AIRPORT_2_HEATHROW.getName(),
                        DateTimeUtil.MIN, DateTimeUtil.MAX, 0, 20));
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

    //@Test
    //public void getWithTickets() throws Exception {
    //    Flight expectedFlightWithTickets = new Flight(FLIGHT_4);
    //    expectedFlightWithTickets.setTickets(Arrays.asList(TICKET_7, TICKET_8));
    //
    //    Flight actualFlightWithTickets = service.getWithTickets(FLIGHT_4.getId());
    //
    //    FLIGHT_WITH_TICKETS_MATCHER.assertEquals(expectedFlightWithTickets, actualFlightWithTickets);
    //
    //}

    @Test
    public void getAll() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(FLIGHTS, service.getAll());
    }

    @Test
    public void getAllFilteredWithoutRestrictions() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(FLIGHTS, service.getFiltered(null, null, null ,null, 0, 20));
    }

    @Test
    public void testGetAllWithDepartureAirportCriteria() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(Arrays.asList(FLIGHT_1, FLIGHT_2, FLIGHT_4),
                service.getFiltered(AIRPORT_1_BORISPOL.getName(), null, null, null, 0, 20));
    }

    @Test
    public void testGetAllWithArrivalAirportCriteria() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(Arrays.asList(FLIGHT_3, FLIGHT_5, FLIGHT_6),
                service.getFiltered(null, AIRPORT_1_BORISPOL.getName(), null, null, 0, 20));
    }

    @Test
    public void testGetAllFromAirportToAirport() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(Arrays.asList(FLIGHT_1, FLIGHT_2),
                service.getFiltered(AIRPORT_1_BORISPOL.getName(), AIRPORT_2_HEATHROW.getName(),
                        null, null, 0, 20));
    }

    @Test
    public void testGetAllBetween() throws Exception {
        FLIGHT_MATCHER.assertCollectionEquals(Collections.singleton(FLIGHT_1),
                service.getFiltered(AIRPORT_1_BORISPOL.getName(), AIRPORT_2_HEATHROW.getName(),
                        FLIGHT_2.getDepartureUtcDateTime().plusDays(1), null, 0, 20));
    }

    @Test
    public void delete() throws Exception {
        service.delete(FLIGHT_2.getId());
        FLIGHT_MATCHER.assertCollectionEquals(
                Collections.singleton(FLIGHT_1),
                service.getFiltered(AIRPORT_1_BORISPOL.getName(), AIRPORT_2_HEATHROW.getName(),
                        null, null, 0, 20));

    }




    private Flight getNewDummyFlightWithNullId(Long id) {
        return new Flight(id, AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRCRAFT_1,
                DateTimeUtil.parseToLocalDateTime("2017-04-23 12:00"), DateTimeUtil.parseToLocalDateTime("2017-04-23 16:00"),
                new BigDecimal("50.00"), new BigDecimal("70.00"));
    }
*/

}
