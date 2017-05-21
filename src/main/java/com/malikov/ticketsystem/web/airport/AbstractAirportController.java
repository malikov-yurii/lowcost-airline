package com.malikov.ticketsystem.web.airport;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.service.IAircraftService;
import com.malikov.ticketsystem.service.IAirportService;
import com.malikov.ticketsystem.to.FlightTo;
import com.malikov.ticketsystem.util.FlightUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
public class AbstractAirportController {

    @Autowired
    IAirportService airportService;

    public List<String> getAirportTosByNameMask(String nameMask) {
        return airportService.getByNameMask(nameMask).stream()
                .map(Airport::getName)
                .collect(Collectors.toList());
    }
}
