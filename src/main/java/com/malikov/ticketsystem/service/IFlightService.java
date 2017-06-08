package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.dto.FlightManageableDTO;
import com.malikov.ticketsystem.dto.TicketPriceDetailsDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.util.exception.NotFoundException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Yurii Malikov
 */
public interface IFlightService {

    /**
     * @return flight found by flightId
     * @throws NotFoundException if not found by flightId
     */
    Flight get(long flightId) throws NotFoundException;

    /**
     * @param flightManageableDTO has all information about new flight
     * @return created flight
     */
    Flight create(FlightManageableDTO flightManageableDTO);

    /**
     * @param flightManageableDTO has all information about new flight
     * @throws NotFoundException if updated flight not found
     */
    void update(FlightManageableDTO flightManageableDTO) throws NotFoundException;

    /**
     * @throws NotFoundException if not found by flightId
     */
    void delete(long flightId) throws NotFoundException;

    /**
     * Updates flight with flightId setting cancelStatus
     * @throws NotFoundException if not found by flightId
     */
    void setCanceledStatus(long flightId, boolean cancelStatus) throws NotFoundException;

    /**
     * @return object which has information about pricing policy for flight tickets
     */
    TicketPriceDetailsDTO getTicketPriceDetails(Long flightId);

    /**
     * @return set of free seats numbers of flight
     */
    Set<Integer> getFreeSeats(Long flightId);

    /**
     * Any condition may be null.
     * @param departureAirportNameCondition filter flights by departure airport name
     * @param arrivalAirportNameCondition filter flights by arrival airport name
     * @param fromDepartureDateTimeCondition filter flights excluding previous flights
     *                                          (using departure local datetime)
     * @param toDepartureDateTimeCondition filter flights excluding next to that param flights
     *                                        (using departure local datetime)
     * @param first excludes from result list first flights
     * @param limit excludes from result list flights next to flights[start + limit]
     * @return filtered, limited by conditions and ordered by departure datetime desc flights
     *              Or returns empty list if not found any.
     */
    List<Flight> getAllFiltered( String departureAirportNameCondition, String arrivalAirportNameCondition,
                                 LocalDateTime fromDepartureDateTimeCondition,
                                 LocalDateTime toDepartureDateTimeCondition,
                                 Integer first, Integer limit);

    /**
     * All parameters should be not null.
     * @param departureAirportNameCondition filter flights by departure airport name
     * @param arrivalAirportNameCondition filter flights by arrival airport name
     * @param fromDepartureDateTimeCondition filter flights excluding previous flights
     *                                          (using departure local datetime)
     * @param toDepartureDateTimeCondition filter flights excluding next to that param flights
     *                                        (using departure local datetime)
     * @param first excludes from result list first flights
     * @param limit excludes from result list flights next to flights[start + limit]
     * @return filtered, limited by conditions and ordered by departure datetime desc flights
     *          with current ticket price.  Full flights excluded. Or returns empty map if not found any.
     */
    Map<Flight, BigDecimal> getFlightTicketPriceMap(String departureAirportNameCondition,
                                                    String arrivalAirportNameCondition,
                                                    LocalDateTime fromDepartureDateTimeCondition,
                                                    LocalDateTime toDepartureDateTimeCondition,
                                                    Integer first, Integer limit);
}
