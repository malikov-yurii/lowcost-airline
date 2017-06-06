package com.malikov.ticketsystem.web.controller.aircraft;

import com.malikov.ticketsystem.service.IAircraftService;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/aircraft")
public class AircraftAdminAjaxController {

    @Autowired
    private IAircraftService aircraftService;

    @GetMapping(value = "/autocomplete-by-name", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> autocompleteAirport(@RequestParam("term") @NotNull @SafeHtml
                                                @Size(min = 2, message = "must be >= 2") String nameMask) {
        return aircraftService.getNamesByNameMask(nameMask);
    }
}

