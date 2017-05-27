package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IFlightService {

    Flight save(Flight flight);

    void update(Flight flight);

    // TODO: 5/8/2017 Should name them properties or hints
    Flight get(long id);

    Flight getWithTickets(long id);

    List<Flight> getAll();

    List<Flight> getAllFiltered(String departureAirportName, String arrivalAirportName,
                                LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime,
                                Integer first, Integer pageSize);

    boolean delete(long id);

    Long countAllFiltered(Airport departureAirport, Airport arrivalAirport,
                                 LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime);

}
