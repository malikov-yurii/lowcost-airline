package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.repository.IAirportRepository;
import com.malikov.ticketsystem.repository.IFlightRepository;
import com.malikov.ticketsystem.repository.ITariffsDetailsRepository;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Yurii Malikov
 */
@Service("flightService")
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private IAirportRepository airportRepository;

    @Autowired
    private ITicketRepository ticketRepository;

    @Autowired
    private ITariffsDetailsRepository tariffsDetailsRepository;

    @Override
    public Flight save(Flight flight) {
        Assert.notNull(flight, "flight should not be null");
        // TODO: 5/5/2017 prepare flight to save
        return flightRepository.save(flight);
    }

    @Override
    public void update(Flight flight) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save flight
        Assert.notNull(flight, "flight should not be null");
        flightRepository.save(flight);
    }

    @Override
    public Flight get(long id) {
        // TODO: 5/5/2017 check not found with id
        return flightRepository.get(id);
    }

    @Override
    public Flight getWithTickets(long id) {
        // TODO: 5/5/2017 check not found with id
        return flightRepository.get(id, Flight.WITH_TICKETS);
    }

    @Override
    public List<Flight> getAll() {
        return flightRepository.getAll();
    }


    // TODO: 5/24/2017 does it need extra validation by userid or something?
    @Override
    @Transactional
    public List<Flight> getAllFiltered(String departureAirportName, String arrivalAirportName,
                                       LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime
            , Integer first, Integer pageSize) {
        Airport departureAirport, arrivalAirport;

        if (departureAirportName != null && departureAirportName.length() != 0) {
            departureAirport = airportRepository.getByName(departureAirportName);
        } else {
            departureAirport = null;
        }

        if (arrivalAirportName != null && arrivalAirportName.length() != 0) {
            arrivalAirport = airportRepository.getByName(arrivalAirportName);
        } else {
            arrivalAirport = null;
        }

        LocalDateTime fromDepartureUtcDateTime, toDepartureUtcDateTime;

        if (fromDepartureDateTime != null && departureAirport != null) {
            fromDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(fromDepartureDateTime, departureAirport.getCity().getZoneId());
        } else {
            fromDepartureUtcDateTime = fromDepartureDateTime;
        }

        if (toDepartureDateTime != null && departureAirport != null) {
            toDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(toDepartureDateTime, departureAirport.getCity().getZoneId());
        } else {
            toDepartureUtcDateTime = toDepartureDateTime;
        }

        return flightRepository.getAllFiltered(departureAirport, arrivalAirport,
                fromDepartureUtcDateTime, toDepartureUtcDateTime, first, pageSize);
    }

    @Override
    //@Transactional  // no need only select?
    public Map<Flight, BigDecimal> getFlightTicketPriceMap(String departureAirportName, String arrivalAirportName, LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime, Integer first, Integer pageSize) {
        Map<Flight, BigDecimal> flightTicketPriceMap = new HashMap<>();

        Airport departureAirport = airportRepository.getByName(departureAirportName);
        Airport arrivalAirport = airportRepository.getByName(arrivalAirportName);
        Map<Flight, Long> filteredFlightsTicketCountMap = flightRepository.getFilteredFlightsTicketCountMap(
                departureAirport, arrivalAirport, fromDepartureDateTime, toDepartureDateTime, first, pageSize);
        TariffsDetails tariffsDetails = tariffsDetailsRepository.getActiveTariffsDetails();

        BigDecimal ticketPrice, totalGrowthPotential,
                timeGrowthPotential,fillingGrowthPotential, perDayPriceGrowth, perTicketPriceGrowth;
        Flight flight;
        Long ticketsQuantity;
        for (Map.Entry<Flight, Long> entry : filteredFlightsTicketCountMap.entrySet()) {
            flight = entry.getKey();
            ticketsQuantity = entry.getValue();

            // TODO: 5/29/2017 try to do that in query
            if (flight.getAircraft().getModel().getPassengersSeatsQuantity() > ticketsQuantity) {
                ticketPrice = BigDecimal.ZERO;

                totalGrowthPotential = flight.getMaxTicketBasePrice().subtract(flight.getInitialTicketBasePrice());
                timeGrowthPotential = totalGrowthPotential.multiply(tariffsDetails.getWeightOfTimeGrowthFactor());
                fillingGrowthPotential = totalGrowthPotential.subtract(timeGrowthPotential);
                perTicketPriceGrowth = fillingGrowthPotential.divide(new BigDecimal(flight.getAircraft().getModel().getPassengersSeatsQuantity()));
                perDayPriceGrowth = timeGrowthPotential.divide(new BigDecimal(tariffsDetails.getDaysCountBeforeTicketPriceStartsToGrow()));


                LocalDateTime utcTimepointPriceStartsToGrow = flight.getDepartureUtcDateTime()
                        .plusDays(tariffsDetails.getDaysCountBeforeTicketPriceStartsToGrow());
                long daysBetweenGrowthStartAndNow = DAYS.between(utcTimepointPriceStartsToGrow,
                        LocalDateTime.now(ZoneId.of("UTC")));

                if (daysBetweenGrowthStartAndNow > 0) {
                    ticketPrice = ticketPrice.add(perDayPriceGrowth.multiply(new BigDecimal(daysBetweenGrowthStartAndNow)));
                }

                if (ticketsQuantity > 0) {
                    ticketPrice = ticketPrice.add(perTicketPriceGrowth.multiply(new BigDecimal(ticketsQuantity)));
                }

                flightTicketPriceMap.put(flight, ticketPrice);
            }
        }

        return flightTicketPriceMap;
    }


    //@Override
    //public Map<Flight, Long> getFlightTicketPriceMap(Airport departureAirport, Airport arrivalAirport,
    //                                                 LocalDateTime fromDepartureDateTime, LocalDateTime toDepartureDateTime,
    //                                                 Integer first, Integer pageSize){
    //    return flightRepository.getFilteredFlightsTicketCountMap(departureAirport, arrivalAirport,
    //            fromDepartureDateTime, toDepartureDateTime,
    //            first, pageSize);
    //}

    @Override
    public boolean delete(long id) {
        return flightRepository.delete(id);
    }

}
