package com.malikov.ticketsystem.web.aircraft;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/profile/aircraft")
public class AircraftAjaxController extends AbstractAircraftController {

    //@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    //public List<FlightTo> getAll() {
    //    return super.getAll();
    //}
    //
    @GetMapping(value = "/autocomplete-by-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteAirport(@RequestParam("term") String nameMask) {
        return super.getAircraftTosByNameMask(nameMask);
    }


}

