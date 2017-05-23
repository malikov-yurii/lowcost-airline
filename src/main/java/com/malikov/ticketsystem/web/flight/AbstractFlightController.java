package com.malikov.ticketsystem.web.flight;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.service.IAircraftService;
import com.malikov.ticketsystem.service.IAirportService;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.to.FlightTo;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.FlightUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
public class AbstractFlightController {

    @Autowired
    IFlightService flightService;

    @Autowired
    IAirportService airportService;

    @Autowired
    IAircraftService aircraftService;

    public List<FlightTo> getAll() {
        return flightService.getAll().stream().map(FlightUtil::asTo).collect(Collectors.toList());
    }

    public List<String> getAirportTosByNameMask(String nameMask) {
        return airportService.getByNameMask(nameMask).stream()
                .map(Airport::getName)
                .collect(Collectors.toList());
    }

    public ResponseEntity<String> create(FlightTo flightTo) {

        Airport departureAirport = airportService.getByName(flightTo.getDepartureAirport()),
                arrivalAirport = airportService.getByName(flightTo.getArrivalAirport());

                flightService.save(
                        new Flight(
                                departureAirport,
                                arrivalAirport,
                                aircraftService.getByName(flightTo.getAircraftName()),
                                DateTimeUtil.zoneIdToUtc(flightTo.getDepartureLocalDateTime(), departureAirport.getCity().getZoneId()),
                                DateTimeUtil.zoneIdToUtc(flightTo.getArrivalLocalDateTime(), arrivalAirport.getCity().getZoneId()),
                                flightTo.getInitialBaseTicketPrice(),
                                flightTo.getMaxBaseTicketPrice()
                                )

                );
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> update(FlightTo flightTo) {
        Flight flight = flightService.get(flightTo.getId());
        Airport departureAirport = airportService.getByName(flightTo.getDepartureAirport()),
                arrivalAirport = airportService.getByName(flightTo.getArrivalAirport());

        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setAircraft(aircraftService.getByName(flightTo.getAircraftName()));
        flight.setDepartureUtcDateTime(DateTimeUtil.zoneIdToUtc(flightTo.getDepartureLocalDateTime(), departureAirport.getCity().getZoneId()));
        flight.setArrivalUtcDateTime(DateTimeUtil.zoneIdToUtc(flightTo.getArrivalLocalDateTime(), arrivalAirport.getCity().getZoneId()));
        flight.setInitialTicketBasePrice(flightTo.getInitialBaseTicketPrice());
        flight.setMaxTicketBasePrice(flightTo.getMaxBaseTicketPrice());

        flightService.save(flight);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
