package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.repository.IAirportRepository;
import com.malikov.ticketsystem.repository.IFlightRepository;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("flightService")
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private IAirportRepository airportRepository;

    @Override
    public Flight save(Flight flight) {
        Assert.notNull(flight, "flight should not be null");
        // TODO: 5/5/2017 prepare flight to save
        return flightRepository.save(flight);
    }

    @Override
    public void update(Flight flight) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save flight
        Assert.notNull(flight, "flight should not be null");
        flightRepository.save(flight);
    }

    @Override
    public Flight get(long id) {
        // TODO: 5/5/2017 check not found with id
        return flightRepository.get(id);
    }

    @Override
    public Flight getWithTickets(long id) {
        // TODO: 5/5/2017 check not found with id
        return flightRepository.get(id, Flight.WITH_TICKETS);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.getAll();
    }

    // TODO: 5/24/2017 does it need extra validation by userid or something?
    @Override
    public List<Flight> getAllFiltered(String departureAirportName, String arrivalAirportName,
                                       LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime) {
        Airport departureAirport, arrivalAirport;

        if (departureAirportName != null && departureAirportName.length() != 0) {
            departureAirport = airportRepository.getByName(departureAirportName);
        } else {
            departureAirport = null;
        }

        if (arrivalAirportName != null && arrivalAirportName.length() != 0) {
            arrivalAirport = airportRepository.getByName(arrivalAirportName);
        } else {
            arrivalAirport = null;
        }

        LocalDateTime fromDepartureUtcDateTime, toDepartureUtcDateTime;

        if (fromDepartureDateTime != null && departureAirport != null) {
            fromDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(fromDepartureDateTime, departureAirport.getCity().getZoneId());
        } else {
            fromDepartureUtcDateTime = fromDepartureDateTime;
        }

        if (toDepartureDateTime != null && departureAirport != null) {
            toDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(toDepartureDateTime, departureAirport.getCity().getZoneId());
        } else {
            toDepartureUtcDateTime = toDepartureDateTime;
        }

        return flightRepository.getAllBetween(departureAirport, arrivalAirport, fromDepartureUtcDateTime, toDepartureUtcDateTime);
    }

    @Override
    public boolean delete(long id) {
        return flightRepository.delete(id);
    }

}
