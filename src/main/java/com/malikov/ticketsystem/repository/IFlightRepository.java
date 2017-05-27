package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IFlightRepository {

    Flight save(Flight t);

    // false if not found
    boolean delete(long id);

    // TODO: 5/8/2017 Should name them properties or hints
    // null if not found
    Flight get(long id, String... hintNames);

    List<Flight> getAll();

    List<Flight> getAllFiltered(Airport departureAirport, Airport arrivalAirportId,
                                LocalDateTime fromDepartureUtcDateTime, LocalDateTime toDepartureUtcDateTime,
                                Integer startingFrom, Integer pageCapacity);


}
