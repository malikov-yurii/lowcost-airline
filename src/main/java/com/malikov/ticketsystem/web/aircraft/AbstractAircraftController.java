package com.malikov.ticketsystem.web.aircraft;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.service.IAircraftService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
public class AbstractAircraftController {

    //@Autowired
    //IFlightService flightService;

    @Autowired
    IAircraftService aircraftService;

    //public List<FlightManageableDTO> getAll() {
    //    return flightService.getAll().stream().map(FlightUtil::asTo).collect(Collectors.toList());
    //}

    public List<String> getAircraftTosByNameMask(String nameMask) {
        return aircraftService.getByNameMask(nameMask).stream()
                .map(Aircraft::getName)
                .collect(Collectors.toList());
    }
}
