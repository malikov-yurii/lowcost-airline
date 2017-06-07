package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.dto.TicketPriceDetailsDTO;
import com.malikov.ticketsystem.dto.FlightManageableDTO;
import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.repository.IAirportRepository;
import com.malikov.ticketsystem.repository.IFlightRepository;
import com.malikov.ticketsystem.repository.ITariffsDetailsRepository;
import com.malikov.ticketsystem.repository.ITicketRepository;
import com.malikov.ticketsystem.service.IAircraftService;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Yurii Malikov
 */
@Service
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository flightRepository;

    @Autowired
    private IAirportRepository airportRepository;

    @Autowired
    private IAircraftService aircraftService;

    @Autowired
    private ITicketRepository ticketRepository;

    @Autowired
    private ITariffsDetailsRepository tariffsDetailsRepository;


    @Override
    public Flight get(long flightId) {
        return ValidationUtil.checkSuccess(flightRepository.get(flightId),
                "not found flight with flightId=" + flightId);
    }

    @Override
    public Flight create(FlightManageableDTO flightManageableDTO) {
        ValidationUtil.checkNew(flightManageableDTO);

        Airport departureAirport = airportRepository.getByName(flightManageableDTO.getDepartureAirport());
        ValidationUtil.checkSuccess(departureAirport, "not found departure airport with name="
                + flightManageableDTO.getDepartureAirport());

        Airport arrivalAirport = airportRepository.getByName(flightManageableDTO.getArrivalAirport());
        ValidationUtil.checkSuccess(arrivalAirport, "not found arrival airport with name="
                + flightManageableDTO.getArrivalAirport());

        Aircraft aircraft = aircraftService.getByName(flightManageableDTO.getAircraftName());
        ValidationUtil.checkSuccess(aircraft, "not found arrival airport with name="
                + flightManageableDTO.getAircraftName());

        return flightRepository.save(new Flight(departureAirport, arrivalAirport, aircraft,
                DateTimeUtil.zoneIdToUtc(flightManageableDTO.getDepartureLocalDateTime(),
                        departureAirport.getCity().getZoneId()),
                DateTimeUtil.zoneIdToUtc(flightManageableDTO.getArrivalLocalDateTime(),
                        arrivalAirport.getCity().getZoneId()),
                flightManageableDTO.getInitialBaseTicketPrice(), flightManageableDTO.getMaxBaseTicketPrice()));
    }

    @Override
    @Transactional
    public void update(FlightManageableDTO flightManageableDTO) {
        Flight flight;
        Airport departureAirport;
        Airport arrivalAirport;
        Aircraft aircraft;

        ValidationUtil.checkNotNew(flightManageableDTO);

        flight = get(flightManageableDTO.getId());

        departureAirport = airportRepository.getByName(flightManageableDTO.getDepartureAirport());
        ValidationUtil.checkSuccess(departureAirport, "not found departure airport with name="
                + flightManageableDTO.getDepartureAirport());
        flight.setDepartureAirport(departureAirport);

        arrivalAirport = airportRepository.getByName(flightManageableDTO.getArrivalAirport());
        ValidationUtil.checkSuccess(arrivalAirport, "not found arrival airport with name="
                + flightManageableDTO.getArrivalAirport());
        flight.setArrivalAirport(arrivalAirport);

        aircraft = aircraftService.getByName(flightManageableDTO.getAircraftName());
        ValidationUtil.checkSuccess(aircraft, "not found arrival airport with name="
                + flightManageableDTO.getAircraftName());
        flight.setAircraft(aircraft);

        flight.setDepartureUtcDateTime(DateTimeUtil.zoneIdToUtc(flightManageableDTO.getDepartureLocalDateTime(),
                departureAirport.getCity().getZoneId()));
        flight.setArrivalUtcDateTime(DateTimeUtil.zoneIdToUtc(flightManageableDTO.getArrivalLocalDateTime(),
                arrivalAirport.getCity().getZoneId()));

        flight.setInitialTicketBasePrice(flightManageableDTO.getInitialBaseTicketPrice());
        flight.setMaxTicketBasePrice(flightManageableDTO.getMaxBaseTicketPrice());

        flightRepository.save(flight);
    }

    @Override
    public void delete(long flightId) {
        ValidationUtil.checkSuccess(flightRepository.delete(flightId),
                "not found flight with flightId = " + flightId);
    }

    @Override
    @Transactional
    public void setCanceledStatus(long flightId, boolean cancelStatus) {
        Flight flight = flightRepository.get(flightId);
        ValidationUtil.checkSuccess(flight, "not found flight with flightId = " + flightId);
        flight.setCanceled(cancelStatus);
    }

    @Override
    public TicketPriceDetailsDTO getTicketPriceDetails(Long flightId) {
        Flight flight = get(flightId);
        Integer bookedTicketsQuantity = ticketRepository.countTickets(flightId);

        TariffsDetails tariffsDetails = tariffsDetailsRepository.getActiveTariffsDetails();
        ValidationUtil.checkSuccess(tariffsDetails, "not found active tariff policy");

        BigDecimal ticketPrice = calculateTicketPrice(tariffsDetails, flight, bookedTicketsQuantity.longValue());
        BigDecimal baggagePrice = tariffsDetails.getBaggageSurchargeOverMaxBaseTicketPrice()
                .add(flight.getMaxTicketBasePrice());

        return new TicketPriceDetailsDTO(ticketPrice, baggagePrice,
                tariffsDetails.getPriorityRegistrationAndBoardingTariff());
    }

    @Override
    public Set<Integer> getFreeSeats(Long flightId) {
        List<Integer> notFreeSeatsNumbers = ticketRepository.getOccupiedSeatsNumbers(flightId);
        Set<Integer> freeSeats = new HashSet<>();

        for (int i = 1; i <= get(flightId).getAircraft().getModel().getPassengerSeatsQuantity(); i++) {
            if (!notFreeSeatsNumbers.contains(i)) {
                freeSeats.add(i);
            }
        }

        return freeSeats;
    }

    @Override
    @Transactional
    public List<Flight> getAllFiltered(String departureAirportNameCondition, String arrivalAirportNameCondition,
                                       LocalDateTime fromDepartureDateTimeCondition,
                                       LocalDateTime toDepartureDateTimeCondition,
                                       Integer first, Integer limit) {
        Airport departureAirport;
        Airport arrivalAirport;
        LocalDateTime fromDepartureUtcDateTime;
        LocalDateTime toDepartureUtcDateTime;

        if (departureAirportNameCondition != null && departureAirportNameCondition.length() != 0) {
            departureAirport = airportRepository.getByName(departureAirportNameCondition);
            ValidationUtil.checkSuccess(departureAirport, "not found departure airport with name="
                    + departureAirportNameCondition);
        } else {
            departureAirport = null;
        }

        if (arrivalAirportNameCondition != null && arrivalAirportNameCondition.length() != 0) {
            arrivalAirport = airportRepository.getByName(arrivalAirportNameCondition);
            ValidationUtil.checkSuccess(arrivalAirport, "not found arrival airport with name="
                    + arrivalAirportNameCondition);
        } else {
            arrivalAirport = null;
        }

        if (fromDepartureDateTimeCondition != null && departureAirport != null) {
            fromDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(fromDepartureDateTimeCondition,
                                                                departureAirport.getCity().getZoneId());
        } else {
            fromDepartureUtcDateTime = fromDepartureDateTimeCondition;
        }

        if (toDepartureDateTimeCondition != null && departureAirport != null) {
            toDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(toDepartureDateTimeCondition,
                                                              departureAirport.getCity().getZoneId());
        } else {
            toDepartureUtcDateTime = toDepartureDateTimeCondition;
        }

        return flightRepository.getFiltered(departureAirport, arrivalAirport,
                fromDepartureUtcDateTime, toDepartureUtcDateTime, first, limit);
    }

    @Override
    public Map<Flight, BigDecimal> getFlightTicketPriceMap(String departureAirportNameCondition,
                                                           String arrivalAirportNameCondition,
                                                           LocalDateTime fromDepartureDateTimeCondition,
                                                           LocalDateTime toDepartureDateTimeCondition,
                                                           Integer first, Integer limit) {
        Airport departureAirport;
        Airport arrivalAirport;
        LocalDateTime fromDepartureUtcDateTime;
        LocalDateTime toDepartureUtcDateTime;
        Map<Flight, Long> filteredFlightsTicketCountMap;
        Map<Flight, BigDecimal> flightTicketPriceMap;
        TariffsDetails tariffsDetails = tariffsDetailsRepository.getActiveTariffsDetails();

        departureAirport = airportRepository.getByName(departureAirportNameCondition);
        ValidationUtil.checkSuccess(departureAirport, "not found departure airport with name="
                + departureAirportNameCondition);

        arrivalAirport = airportRepository.getByName(arrivalAirportNameCondition);
        ValidationUtil.checkSuccess(arrivalAirport, "not found arrival airport with name="
                + arrivalAirportNameCondition);

        fromDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(fromDepartureDateTimeCondition,
                departureAirport.getCity().getZoneId());
        toDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(toDepartureDateTimeCondition,
                departureAirport.getCity().getZoneId());

        filteredFlightsTicketCountMap = flightRepository.getFilteredFlightTicketCountMap(
                departureAirport, arrivalAirport, fromDepartureUtcDateTime, toDepartureUtcDateTime, first, limit);

        if(filteredFlightsTicketCountMap.isEmpty()) {
            return Collections.emptyMap();
        }

        flightTicketPriceMap = new HashMap<>();
        filteredFlightsTicketCountMap.forEach((flight, ticketsQuantity) -> flightTicketPriceMap.put(flight,
                calculateTicketPrice(tariffsDetails, flight, ticketsQuantity).setScale(2)));

        return flightTicketPriceMap;
    }

    /**
     * @param ticketsQuantity current quantity of purchased or booked tickets
     * @return current ticket price for flight calculated using tariff details and ticketsQuantity
     */
    private BigDecimal calculateTicketPrice(TariffsDetails tariffsDetails, Flight flight, Long ticketsQuantity) {
        BigDecimal ticketPrice;
        BigDecimal totalGrowthPotential;
        BigDecimal timeGrowthPotential;
        BigDecimal fillingGrowthPotential;
        BigDecimal perTicketPriceGrowth;
        BigDecimal perDayPriceGrowth;
        LocalDateTime utcTimePointPriceStartsToGrow;
        ticketPrice = flight.getInitialTicketBasePrice();

        totalGrowthPotential = flight.getMaxTicketBasePrice().subtract(flight.getInitialTicketBasePrice());
        timeGrowthPotential = totalGrowthPotential.multiply(tariffsDetails.getWeightOfTimeGrowthFactor());
        fillingGrowthPotential = totalGrowthPotential.subtract(timeGrowthPotential);

        perTicketPriceGrowth = fillingGrowthPotential.divide(new BigDecimal(flight.getAircraft().getModel()
                .getPassengerSeatsQuantity()));
        perDayPriceGrowth = timeGrowthPotential.divide(new BigDecimal(tariffsDetails
                .getDaysCountBeforeTicketPriceStartsToGrow()));

        utcTimePointPriceStartsToGrow = flight.getDepartureUtcDateTime()
                .minusDays(tariffsDetails.getDaysCountBeforeTicketPriceStartsToGrow());
        long daysBetweenGrowthStartAndNow = DAYS.between(LocalDateTime.now(ZoneId.of("UTC")),
                utcTimePointPriceStartsToGrow);

        if (daysBetweenGrowthStartAndNow > 0) {
            ticketPrice = ticketPrice.add(perDayPriceGrowth.multiply(new BigDecimal(daysBetweenGrowthStartAndNow)));
        }

        if (ticketsQuantity > 0) {
            ticketPrice = ticketPrice.add(perTicketPriceGrowth.multiply(new BigDecimal(ticketsQuantity)));
        }

        return ticketPrice;
    }
}
