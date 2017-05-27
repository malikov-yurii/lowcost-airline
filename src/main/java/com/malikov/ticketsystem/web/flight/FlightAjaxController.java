package com.malikov.ticketsystem.web.flight;

import com.malikov.ticketsystem.to.FlightTo;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/profile/flight")
public class FlightAjaxController extends AbstractFlightController {

    //@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping
    //public List<FlightTo> getFiltered(
    public ModelMap getFiltered(
            @RequestParam(value = "fromDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime fromDepartureDateTime,
            @RequestParam(value = "toDepartureDateTimeCondition", required = false) @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN) LocalDateTime toDepartureDateTime,
            @RequestParam(value = "departureAirportCondition", required = false) String departureAirportName,
            @RequestParam(value = "arrivalAirportCondition", required = false) String arrivalAirportName,
            @RequestParam(value = "draw") Integer draw,
            @RequestParam(value = "start") Integer startingFrom,
            @RequestParam(value = "length") Integer pageCapacity
            ) {
        List<FlightTo> flightTos = super.getFilteredPageContent(departureAirportName, arrivalAirportName,
                fromDepartureDateTime, toDepartureDateTime, startingFrom, pageCapacity);
        //int totalFiltered = super.contFiltered();
        ModelMap model = new ModelMap();
        model.put("draw", draw);
        model.put("recordsTotal", flightTos.size() + 1 + startingFrom);
        model.put("recordsFiltered", flightTos.size() + 1 + startingFrom);
        //model.put("recordsFiltered", flightTos.size() + 1);
        //model.put("recordsFiltered", flightTos.size());
        model.put("data", flightTos);
        return  model;
    }
    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid FlightTo flightTo) {
    //public ResponseEntity<String> createOrUpdate(FlightTo flightTo) {
        if (flightTo.isNew()) {
            return super.create(flightTo);
        } else {
            return super.update(flightTo);
        }
    }

    // todo Is it ok to  use body for this parameter or i should use pathvariable??
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{id}/set-canceled")
    public ResponseEntity<String> setCanceled(@PathVariable("id") int id, @RequestParam("canceled") boolean canceled) {
        return super.setCanceled(id, canceled);
    }

    //@PreAuthorize("hasRole('SUPER_ADMIN')") // TODO: 5/23/2017 Why not working preauthorize??
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

