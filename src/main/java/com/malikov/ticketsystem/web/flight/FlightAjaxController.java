package com.malikov.ticketsystem.web.flight;

import com.malikov.ticketsystem.to.FlightTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/profile/flight")
public class FlightAjaxController extends AbstractFlightController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FlightTo> getAll() {
        return super.getAll();
    }




}

