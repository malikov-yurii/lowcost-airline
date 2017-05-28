package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Flight;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    List<Flight> getAllFiltered( String departureAirportName, String arrivalAirportName,
                                LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime,
                                Integer first, Integer pageSize);

    boolean delete(long id);


    Map<Flight, BigDecimal> getFlightTicketPriceMap(String departureAirportName, String arrivalAirportName,
                                                    LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime,
                                                    Integer first, Integer pageSize);

}
