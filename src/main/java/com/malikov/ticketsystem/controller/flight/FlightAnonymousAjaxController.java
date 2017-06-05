package com.malikov.ticketsystem.controller.flight;

import com.malikov.ticketsystem.dto.FlightDTO;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.FlightUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */

@RestController
@RequestMapping(value = "/ajax/anonymous/flight")
public class FlightAnonymousAjaxController {

    @Autowired
    IFlightService flightService;

    //@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    // TODO: 5/31/2017 not need preauthorize (in xml permit all?
    //@PreAuthorize()
    public ModelMap getFilteredPage(
            @RequestParam(value = "fromDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime fromDepartureDateTime,
            @RequestParam(value = "toDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime toDepartureDateTime,
            @RequestParam(value = "departureAirportCondition", required = false) String departureAirportName,
            @RequestParam(value = "arrivalAirportCondition", required = false) String arrivalAirportName,
            @RequestParam(value = "draw") Integer draw,
            @RequestParam(value = "start") Integer startingFrom,
            @RequestParam(value = "length") Integer pageCapacity) {
        List<FlightDTO> flightDTOs = flightService.getFlightTicketPriceMapFilteredBy(departureAirportName,
                arrivalAirportName, fromDepartureDateTime,
                toDepartureDateTime, startingFrom,
                pageCapacity).entrySet()
                .stream()
                .map(entry -> FlightUtil.asDTO(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        if (flightDTOs.size() > pageCapacity) {
            flightDTOs.remove(flightDTOs.size() - 1);
        }
        ModelMap model = new ModelMap();
        model.put("draw", draw);
        int dataTableHasNextPageIndicator = startingFrom + flightDTOs.size() + 1;

        model.put("recordsTotal", dataTableHasNextPageIndicator);
        model.put("recordsFiltered", dataTableHasNextPageIndicator);
        model.put("data", flightDTOs);
        return model;
    }


}
