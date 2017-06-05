package com.malikov.ticketsystem.web.controller.aircraft;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.service.IAircraftService;
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
@RequestMapping(value = "/ajax/admin/aircraft")
public class AircraftAdminAjaxController {

    @Autowired
    IAircraftService aircraftService;

    @GetMapping(value = "/autocomplete-by-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteAirport(@RequestParam("term") String nameMask) {
        return aircraftService.getByNameMask(nameMask).stream()
                .map(Aircraft::getName)
                .collect(Collectors.toList());
    }
}

