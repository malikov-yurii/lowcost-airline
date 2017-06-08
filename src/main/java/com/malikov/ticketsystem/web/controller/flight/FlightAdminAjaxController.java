package com.malikov.ticketsystem.web.controller.flight;

import com.malikov.ticketsystem.dto.FlightManageableDTO;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.dtoconverter.FlightDTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/flight")
public class FlightAdminAjaxController {

    @Autowired
    private IFlightService flightService;

    @PostMapping
    public ResponseEntity<String> create(@Valid FlightManageableDTO flightManageableDTO) {
        flightService.create(flightManageableDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid FlightManageableDTO flightManageableDTO) {
        flightService.update(flightManageableDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/set-canceled")
    public ResponseEntity<String> setCanceled(@PathVariable("id") int id,
                                              @RequestParam("canceled") @NotNull boolean canceled) {
        flightService.setCanceledStatus(id, canceled);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        flightService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ModelMap getFilteredPage(
            @RequestParam(value = "fromDepartureDateTimeCondition", required = false)
                    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime fromDepartureDateTime,
            @RequestParam(value = "toDepartureDateTimeCondition", required = false)
                    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime toDepartureDateTime,
            @RequestParam(value = "departureAirportCondition", required = false) String departureAirportName,
            @RequestParam(value = "arrivalAirportCondition", required = false) String arrivalAirportName,
            @RequestParam(value = "draw") Integer draw,
            @RequestParam(value = "start") Integer startingFrom,
            @RequestParam(value = "length") Integer pageCapacity) {

        List<FlightManageableDTO> flightManageableDTOS = flightService.getAllFiltered(departureAirportName,
                    arrivalAirportName, fromDepartureDateTime, toDepartureDateTime,
                    startingFrom, pageCapacity).stream()
                .map(FlightDTOConverter::asManageableDTO)
                .collect(Collectors.toList());
        ModelMap model = new ModelMap();
        int dataTableHasNextPageIndicator;

        if (flightManageableDTOS.size() > pageCapacity) {
            flightManageableDTOS.remove(flightManageableDTOS.size() - 1);
        }

        dataTableHasNextPageIndicator = startingFrom + flightManageableDTOS.size() + 1;
        model.put("draw", draw);
        model.put("recordsTotal", dataTableHasNextPageIndicator);
        model.put("recordsFiltered", dataTableHasNextPageIndicator);
        model.put("data", flightManageableDTOS);

        return model;
    }
}

