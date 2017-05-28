package com.malikov.ticketsystem.web.flight;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.service.IAircraftService;
import com.malikov.ticketsystem.service.IAirportService;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.to.FlightDTO;
import com.malikov.ticketsystem.to.FlightManageableDTO;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.FlightUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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

    public List<FlightManageableDTO> getAll() {
        return flightService.getAll().stream().map(FlightUtil::asManageableDTO).collect(Collectors.toList());
    }

    public ResponseEntity<String> create(FlightManageableDTO flightManageableDTO) {
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
    }

    public ResponseEntity<String> update(FlightManageableDTO flightManageableDTO) {
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

        flightService.save(flight);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<String> delete(int id) {
        flightService.delete(id);
        return !flightService.delete(id) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<String> setCanceled(int flightId, boolean isCanceled) {
        Flight flight = flightService.get(flightId);
        flight.setCanceled(isCanceled);
        flightService.save(flight);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public List<Flight> getFilteredPageContent(String departureAirportName, String arrivalAirportName,
                                               LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime,
                                               Integer startingFrom, Integer pageCapacity) {
        return flightService.getAllFiltered(
                departureAirportName, arrivalAirportName,
                fromDepartureDateTime, toDepartureDateTime,
                startingFrom, pageCapacity);
    }


    public List<FlightDTO> getFlightTicketPriceMap(String departureAirportName, String arrivalAirportName, LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime, Integer startingFrom, Integer pageCapacity) {
        return flightService.getFlightTicketPriceMap(departureAirportName,
                arrivalAirportName, fromDepartureDateTime,
                toDepartureDateTime, startingFrom,
                pageCapacity).entrySet()
                                .stream()
                                .map(entry -> FlightUtil.asDTO(entry.getKey(), entry.getValue()))
                                .collect(Collectors.toList());
    }
}
