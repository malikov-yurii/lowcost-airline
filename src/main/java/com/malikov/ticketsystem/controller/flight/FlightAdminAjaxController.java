package com.malikov.ticketsystem.controller.flight;

import com.malikov.ticketsystem.dto.FlightManageableDTO;
import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.service.IAircraftService;
import com.malikov.ticketsystem.service.IAirportService;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.FlightUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class FlightAdminAjaxController {

    @Autowired
    IFlightService flightService;

    @Autowired
    IAirportService airportService;

    @Autowired
    IAircraftService aircraftService;

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
        List<FlightManageableDTO> flightManageableDTOS =  flightService.getAllFiltered(
                    departureAirportName, arrivalAirportName,
                    fromDepartureDateTime, toDepartureDateTime,
                    startingFrom, pageCapacity)
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
    public ResponseEntity<String> create(@Valid FlightManageableDTO flightManageableDTO) {
    //public ResponseEntity<String> createOrUpdate(FlightManageableDTO flightManageableDTO) {
        if (flightManageableDTO.isNew()) {
            Airport departureAirport = airportService.getByName(flightManageableDTO.getDepartureAirport()),
                    arrivalAirport = airportService.getByName(flightManageableDTO.getArrivalAirport());

            flightService.save(
                    new Flight(
                            departureAirport,
                            arrivalAirport,
                            aircraftService.getByName(flightManageableDTO.getAircraftName()),
                            DateTimeUtil.zoneIdToUtc(flightManageableDTO.getDepartureLocalDateTime(), departureAirport.getCity().getZoneId()),
                            DateTimeUtil.zoneIdToUtc(flightManageableDTO.getArrivalLocalDateTime(), arrivalAirport.getCity().getZoneId()),
                            flightManageableDTO.getInitialBaseTicketPrice(),
                            flightManageableDTO.getMaxBaseTicketPrice()
                    )

            );
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("id of new flight should be null or 0");
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid FlightManageableDTO flightManageableDTO) {
    //public ResponseEntity<String> createOrUpdate(FlightManageableDTO flightManageableDTO) {
        if (!flightManageableDTO.isNew()) {
            Flight flight = flightService.get(flightManageableDTO.getId());
            Airport departureAirport = airportService.getByName(flightManageableDTO.getDepartureAirport()),
                    arrivalAirport = airportService.getByName(flightManageableDTO.getArrivalAirport());

            flight.setDepartureAirport(departureAirport);
            flight.setArrivalAirport(arrivalAirport);
            flight.setAircraft(aircraftService.getByName(flightManageableDTO.getAircraftName()));
            flight.setDepartureUtcDateTime(DateTimeUtil.zoneIdToUtc(flightManageableDTO.getDepartureLocalDateTime(), departureAirport.getCity().getZoneId()));
            flight.setArrivalUtcDateTime(DateTimeUtil.zoneIdToUtc(flightManageableDTO.getArrivalLocalDateTime(), arrivalAirport.getCity().getZoneId()));
            flight.setInitialTicketBasePrice(flightManageableDTO.getInitialBaseTicketPrice());
            flight.setMaxTicketBasePrice(flightManageableDTO.getMaxBaseTicketPrice());

            flightService.update(flight);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Not valid flight id in request.");
        }
    }

    // todo Is it ok dto  use body for this parameter or i should use pathvariable??
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/{id}/set-canceled")
    public ResponseEntity<String> setCanceled(@PathVariable("id") int id, @RequestParam("canceled") boolean canceled) {
        Flight flight = flightService.get(id);
        flight.setCanceled(canceled);
        flightService.save(flight);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id){
        flightService.delete(id);
        return !flightService.delete(id) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

