package com.malikov.ticketsystem.controller.flight;

import com.malikov.ticketsystem.dto.FlightDTO;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/user/flight")
public class FlightUserAjaxController extends AbstractFlightController {

    //@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    //public List<FlightManageableDTO> getFilteredPage(
    public ModelMap getFilteredPage(
            @RequestParam(value = "fromDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime fromDepartureDateTime,
            @RequestParam(value = "toDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime toDepartureDateTime,
            @RequestParam(value = "departureAirportCondition", required = false) String departureAirportName,
            @RequestParam(value = "arrivalAirportCondition", required = false) String arrivalAirportName,
            @RequestParam(value = "draw") Integer draw,
            @RequestParam(value = "start") Integer startingFrom,
            @RequestParam(value = "length") Integer pageCapacity) {
        List<FlightDTO> flightDTOs = super.getFlightTicketPriceMap(departureAirportName, arrivalAirportName,
                fromDepartureDateTime, toDepartureDateTime, startingFrom, pageCapacity + 1);
        if (flightDTOs.size() > pageCapacity) {
            flightDTOs.remove(flightDTOs.size() - 1);
        }
        ModelMap model = new ModelMap();
        model.put("draw", draw);
        int dataTableHasNextPageIndicator = startingFrom + flightDTOs.size() + 1;

        model.put("recordsTotal", dataTableHasNextPageIndicator);
        model.put("recordsFiltered", dataTableHasNextPageIndicator);
        model.put("data", flightDTOs);
        return  model;
    }


/*// TODO: 5/22/2017 i don't need that
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat(DateTimeUtil.DATE_TIME_PATTERN);//edit for the    format you need
        dateTimeFormat.setLenient(false);
        binder.registerCustomEditor(LocalDateTime.class, new CustomDateEditor(dateTimeFormat, true));
    }


*/


}

