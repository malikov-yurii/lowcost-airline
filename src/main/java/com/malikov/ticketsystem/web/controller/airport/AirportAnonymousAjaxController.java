package com.malikov.ticketsystem.web.controller.airport;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.service.IAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/anonymous/airport")
public class AirportAnonymousAjaxController {

    @Autowired
    IAirportService airportService;

    @GetMapping(value = "/autocomplete-by-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteAirport(@RequestParam("term") String nameMask) {
        return airportService.getByNameMask(nameMask).stream()
                .map(Airport::getName)
                .collect(Collectors.toList());
    }
}

