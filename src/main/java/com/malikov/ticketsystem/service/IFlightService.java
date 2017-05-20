package com.malikov.ticketsystem.service;

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

    List<Flight> getAllBetween(Long departureAirportId, Long arrivalAirportId, LocalDateTime fromUtcDateTime, LocalDateTime toUtcDateTime);

    void delete(long id);

}
