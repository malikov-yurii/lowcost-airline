package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author Yurii Malikov
 */
public interface IFlightRepository extends IGenericRepository<Flight>{

    List<Flight> getAllFiltered(Airport departureAirport, Airport arrivalAirport,
                                LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime,
                                Integer first, Integer limit);

    Map<Flight, Long> getFilteredFlightsTicketCountMap(Airport departureAirport, Airport arrivalAirport,
                                                       LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime,
                                                       Integer first, Integer limit);
//List<Object[]> getFilteredFlightsTicketCountMap(Airport departureAirport, Airport arrivalAirport,
//                               LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime,
//                                                          Integer first, Integer limit);


}
