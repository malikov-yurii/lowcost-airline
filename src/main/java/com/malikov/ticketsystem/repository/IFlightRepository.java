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

    /**
     * All parameters should be not null.
     * @param departureAirportCondition filter flights by departure airport
     * @param arrivalAirportCondition filter flights by arrival airport
     * @param fromDepartureUtcDateTimeCondition filter flights excluding previous flights
     *                                          (using departure datetime)
     * @param toDepartureUtcDateTimeCondition filter flights excluding next to that param flights
     *                                        (using departure datetime)
     * @param first excludes from result list firsts flights from
     *              filtered and ordered by departure date time desc list of flights
     * @param limit excludes from result list flights next to flights[start + limit] from
     *              filtered and ordered by departure date time desc list of flights
     * @return filtered, limited by conditions map of flight as keys and current ticket price
     *              calculated using tariff details from database.
     *              Flights without available free tickets are excluded from result map.
     *              Or returns empty map if not found any
     */
    Map<Flight, Long> getFilteredFlightTicketCountMap(Airport departureAirportCondition, Airport arrivalAirportCondition,
                                                      LocalDateTime fromDepartureUtcDateTimeCondition,
                                                      LocalDateTime toDepartureUtcDateTimeCondition,
                                                      Integer first, Integer limit);

    /**
     * Any condition may be null.
     * @param departureAirportCondition filter flights by departure airport
     * @param arrivalAirportCondition filter flights by arrival airport
     * @param fromDepartureUtcDateTimeCondition filter flights excluding previous flights
     *                                          (using departure datetime)
     * @param toDepartureUtcDateTimeCondition filter flights excluding next to that param flights
     *                                        (using departure datetime)
     * @param first excludes from result list first flights
     * @param limit excludes from result list flights next to flights[start + limit]
     * @return filtered, limited by conditions and ordered by departure datetime desc flights.
     *              Or returns empty list if not found any.
     */
    List<Flight> getFiltered(Airport departureAirportCondition, Airport arrivalAirportCondition,
                             LocalDateTime fromDepartureUtcDateTimeCondition,
                             LocalDateTime toDepartureUtcDateTimeCondition,
                             Integer first, Integer limit);
}
