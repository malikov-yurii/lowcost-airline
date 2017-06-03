package com.malikov.ticketsystem.controller.flight;

import com.malikov.ticketsystem.dto.FlightManageableDTO;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.FlightUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/admin/flight")
public class FlightAdminAjaxController extends AbstractFlightController {

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
            @RequestParam(value = "length") Integer pageCapacity
            ) {
        List<FlightManageableDTO> flightManageableDTOS = super.getFilteredPageContent(departureAirportName, arrivalAirportName,
                fromDepartureDateTime, toDepartureDateTime, startingFrom, pageCapacity)

                .stream()
                .map(FlightUtil::asManageableDTO)
                .collect(Collectors.toList());
        //int totalFiltered = super.contFiltered();
        ModelMap model = new ModelMap();
        model.put("draw", draw);
        model.put("recordsTotal", flightManageableDTOS.size() + 1 + startingFrom);
        model.put("recordsFiltered", flightManageableDTOS.size() + 1 + startingFrom);
        //model.put("recordsFiltered", flightManageableDTOS.size() + 1);
        //model.put("recordsFiltered", flightManageableDTOS.size());
        model.put("data", flightManageableDTOS);
        return  model;
    }
    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid FlightManageableDTO flightManageableDTO) {
    //public ResponseEntity<String> createOrUpdate(FlightManageableDTO flightManageableDTO) {
        if (flightManageableDTO.isNew()) {
            return super.create(flightManageableDTO);
        } else {
            return super.update(flightManageableDTO);
        }
    }

    // todo Is it ok dto  use body for this parameter or i should use pathvariable??
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{id}/set-canceled")
    public ResponseEntity<String> setCanceled(@PathVariable("id") int id, @RequestParam("canceled") boolean canceled) {
        return super.setCanceled(id, canceled);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id){
        return super.delete(id);
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

