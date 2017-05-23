package com.malikov.ticketsystem.web.flight;

import com.malikov.ticketsystem.to.FlightTo;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrUpdate(@Valid FlightTo flightTo) {
    //public ResponseEntity<String> createOrUpdate(FlightTo flightTo) {
        if (flightTo.isNew()) {
            return super.create(flightTo);
        } else {
            return super.update(flightTo);
        }
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

