package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.dto.FlightManageableDTO;
import com.malikov.ticketsystem.dto.TicketPriceDetailsDTO;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static com.malikov.ticketsystem.util.MessageUtil.getMessage;
import static com.malikov.ticketsystem.util.ValidationUtil.*;
import static java.math.RoundingMode.HALF_UP;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Yurii Malikov
 */
@Service("flightService")
public class FlightServiceImpl implements IFlightService, MessageSourceAware {

    private static final Logger LOG = LoggerFactory.getLogger(FlightServiceImpl.class);

    private MessageSource messageSource;

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
        return checkNotFound(flightRepository.get(flightId),
                getMessage(messageSource, "exception.notFoundById") + flightId);
    }


    @Override
    public Flight create(FlightManageableDTO flightManageableDTO) {
        ValidationUtil.checkNew(flightManageableDTO,
                getMessage(messageSource, "exception.mustBeNew"));

        Airport departureAirport = airportRepository.getByName(flightManageableDTO.getDepartureAirport());
        checkNotFound(departureAirport, getMessage(messageSource, "exception.notFoundByName")
                + flightManageableDTO.getDepartureAirport());

        Airport arrivalAirport = airportRepository.getByName(flightManageableDTO.getArrivalAirport());
        checkNotFound(arrivalAirport, getMessage(messageSource, "exception.notFoundByName")
                + flightManageableDTO.getArrivalAirport());

        checkNotEqual(departureAirport, arrivalAirport,
                getMessage(messageSource, "exception.mustNotBeSame"));

        LocalDateTime departureUtcDateTime = DateTimeUtil.zoneIdToUtc(flightManageableDTO.getDepartureLocalDateTime(),
                departureAirport.getCity().getZoneId());
        LocalDateTime arrivalUtcDateTime = DateTimeUtil.zoneIdToUtc(flightManageableDTO.getArrivalLocalDateTime(),
                arrivalAirport.getCity().getZoneId());

        validateFromToDates(departureUtcDateTime, arrivalUtcDateTime,
                getMessage(messageSource, "exception.fromCannotBeAfterTo"));

        Aircraft aircraft = aircraftService.getByName(flightManageableDTO.getAircraftName());
        checkNotFound(aircraft, getMessage(messageSource, "exception.notFoundByName")
                + flightManageableDTO.getAircraftName());

        return flightRepository.save(new Flight(departureAirport, arrivalAirport, aircraft,
                departureUtcDateTime, arrivalUtcDateTime, flightManageableDTO.getInitialBaseTicketPrice(),
                flightManageableDTO.getMaxBaseTicketPrice()));
    }

    @Override
    @Transactional
    public void update(FlightManageableDTO flightManageableDTO) {
        ValidationUtil.checkNotNew(flightManageableDTO,
                getMessage(messageSource, "exception.mustBeNotNew"));

        Flight flight = get(flightManageableDTO.getId());

        Airport departureAirport = airportRepository.getByName(flightManageableDTO.getDepartureAirport());
        checkNotFound(departureAirport, getMessage(messageSource, "exception.notFoundByName")
                + flightManageableDTO.getDepartureAirport());
        flight.setDepartureAirport(departureAirport);

        Airport arrivalAirport = airportRepository.getByName(flightManageableDTO.getArrivalAirport());
        checkNotFound(arrivalAirport, getMessage(messageSource, "exception.notFoundByName")
                + flightManageableDTO.getArrivalAirport());
        flight.setArrivalAirport(arrivalAirport);

        checkNotEqual(departureAirport, arrivalAirport,
                getMessage(messageSource, "exception.mustNotBeSame"));

        Aircraft aircraft = aircraftService.getByName(flightManageableDTO.getAircraftName());
        checkNotFound(aircraft, getMessage(messageSource, "exception.notFoundByName")
                + flightManageableDTO.getAircraftName());
        flight.setAircraft(aircraft);

        LocalDateTime departureUtcDateTime = DateTimeUtil.zoneIdToUtc(flightManageableDTO.getDepartureLocalDateTime(),
                departureAirport.getCity().getZoneId());
        LocalDateTime arrivalUtcDateTime = DateTimeUtil.zoneIdToUtc(flightManageableDTO.getArrivalLocalDateTime(),
                arrivalAirport.getCity().getZoneId());
        validateFromToDates(departureUtcDateTime, arrivalUtcDateTime,
                getMessage(messageSource, "exception.fromCannotBeAfterTo"));
        flight.setDepartureUtcDateTime(departureUtcDateTime);
        flight.setArrivalUtcDateTime(arrivalUtcDateTime);

        flight.setInitialTicketBasePrice(flightManageableDTO.getInitialBaseTicketPrice());
        flight.setMaxTicketBasePrice(flightManageableDTO.getMaxBaseTicketPrice());

        flightRepository.save(flight);

        LOG.info("{} updated.", flight);
    }

    @Override
    public void delete(long flightId) {
        checkNotFound(flightRepository.delete(flightId),
                getMessage(messageSource, "exception.notFoundById") + flightId);
        LOG.info("Flight with id={} deleted.", flightId);
    }

    @Override
    @Transactional
    public void setCanceledStatus(long flightId, boolean cancelStatus) {
        Flight flight = checkNotFound(flightRepository.get(flightId),
                getMessage(messageSource, "exception.notFoundById") + flightId);
        flight.setCanceled(cancelStatus);
        flightRepository.save(flight);
        LOG.info("Flight with id= {} changed status to.", flightId, cancelStatus);
    }

    @Override
    public TicketPriceDetailsDTO getTicketPriceDetails(Long flightId) {
        Flight flight = get(flightId);
        Integer bookedTicketsQuantity = ticketRepository.countTickets(flightId);

        TariffsDetails tariffsDetails = tariffsDetailsRepository.getActiveTariffsDetails();
        checkNotFound(tariffsDetails,
                getMessage(messageSource, "exception.notFoundByActiveTariffDetails"));

        BigDecimal ticketPrice = calculateTicketPrice(tariffsDetails, flight, bookedTicketsQuantity.longValue());
        BigDecimal baggagePrice = tariffsDetails.getBaggageSurchargeOverMaxBaseTicketPrice()
                .add(flight.getMaxTicketBasePrice());

        return new TicketPriceDetailsDTO(ticketPrice, baggagePrice,
                tariffsDetails.getPriorityRegistrationAndBoardingTariff());
    }

    @Override
    public Set<Integer> getFreeSeats(Long flightId) {
        List<Integer> occupiedSeatNumbers = ticketRepository.getOccupiedSeatNumbers(flightId);
        Set<Integer> freeSeats = new HashSet<>();

        for (int i = 1; i <= get(flightId).getAircraft().getModel().getPassengerSeatsQuantity(); i++) {
            if (!occupiedSeatNumbers.contains(i)) {
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
        if (departureAirportNameCondition != null && departureAirportNameCondition.length() != 0) {
            departureAirport = airportRepository.getByName(departureAirportNameCondition);
            checkNotFound(departureAirport, getMessage(messageSource, "exception.notFoundByName")
                    + departureAirportNameCondition);
        } else {
            departureAirport = null;
        }

        Airport arrivalAirport;
        if (arrivalAirportNameCondition != null && arrivalAirportNameCondition.length() != 0) {
            arrivalAirport = airportRepository.getByName(arrivalAirportNameCondition);
            checkNotFound(arrivalAirport, getMessage(messageSource, "exception.notFoundByName")
                    + arrivalAirportNameCondition);
        } else {
            arrivalAirport = null;
        }
        checkNotEqual(departureAirport, arrivalAirport,
                getMessage(messageSource, "exception.mustNotBeSame"));

        LocalDateTime fromDepartureUtcDateTime;
        if (fromDepartureDateTimeCondition != null && departureAirport != null) {
            fromDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(fromDepartureDateTimeCondition,
                    departureAirport.getCity().getZoneId());
        } else {
            fromDepartureUtcDateTime = fromDepartureDateTimeCondition;
        }

        LocalDateTime toDepartureUtcDateTime;
        if (toDepartureDateTimeCondition != null && departureAirport != null) {
            toDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(toDepartureDateTimeCondition,
                    departureAirport.getCity().getZoneId());
        } else {
            toDepartureUtcDateTime = toDepartureDateTimeCondition;
        }

        ValidationUtil.validateFromToDates(fromDepartureUtcDateTime, toDepartureUtcDateTime,
                getMessage(messageSource, "exception.fromCannotBeAfterTo"));

        return flightRepository.getFiltered(departureAirport, arrivalAirport,
                fromDepartureUtcDateTime, toDepartureUtcDateTime, first, limit);
    }

    @Override
    public Map<Flight, BigDecimal> getFlightTicketPriceMap(String departureAirportNameCondition,
                                                           String arrivalAirportNameCondition,
                                                           LocalDateTime fromDepartureDateTimeCondition,
                                                           LocalDateTime toDepartureDateTimeCondition,
                                                           Integer first, Integer limit) {
        TariffsDetails tariffsDetails = tariffsDetailsRepository.getActiveTariffsDetails();

        Airport departureAirport = airportRepository.getByName(departureAirportNameCondition);
        checkNotFound(departureAirport, getMessage(messageSource, "exception.notFoundByName")
                + departureAirportNameCondition);

        Airport arrivalAirport = airportRepository.getByName(arrivalAirportNameCondition);
        checkNotFound(arrivalAirport, getMessage(messageSource, "exception.notFoundByName")
                + arrivalAirportNameCondition);
        checkNotEqual(departureAirport, arrivalAirport,
                getMessage(messageSource, "exception.mustNotBeSame"));

        LocalDateTime fromDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(fromDepartureDateTimeCondition,
                departureAirport.getCity().getZoneId());
        LocalDateTime toDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(toDepartureDateTimeCondition,
                departureAirport.getCity().getZoneId());
        validateFromToDates(fromDepartureUtcDateTime, toDepartureUtcDateTime,
                getMessage(messageSource, "exception.fromCannotBeAfterTo"));

        Map<Flight, Long> filteredFlightsTicketCountMap = flightRepository.getFilteredFlightTicketCountMap(
                departureAirport, arrivalAirport, fromDepartureUtcDateTime, toDepartureUtcDateTime, first, limit);

        if (filteredFlightsTicketCountMap.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Flight, BigDecimal> flightTicketPriceMap = new HashMap<>();
        filteredFlightsTicketCountMap
                .forEach((flight, ticketsQuantity) -> {
                    if (flight.getAircraft().getModel().getPassengerSeatsQuantity() > ticketsQuantity) {
                        flightTicketPriceMap.put(flight,
                                calculateTicketPrice(tariffsDetails, flight, ticketsQuantity)
                                        .setScale(6, HALF_UP));
                    }
                });

        return flightTicketPriceMap;
    }

    /**
     * @param ticketsQuantity current quantity of purchased or booked tickets
     * @return current ticket price for flight calculated using tariff details and ticketsQuantity
     */
    private BigDecimal calculateTicketPrice(TariffsDetails tariffsDetails, Flight flight, Long ticketsQuantity) {
        BigDecimal ticketPrice = flight.getInitialTicketBasePrice();

        BigDecimal totalGrowthPotential = flight.getMaxTicketBasePrice().subtract(flight.getInitialTicketBasePrice());
        BigDecimal timeGrowthPotential = totalGrowthPotential.multiply(tariffsDetails.getWeightOfTimeGrowthFactor());
        BigDecimal fillingGrowthPotential = totalGrowthPotential.subtract(timeGrowthPotential);

        BigDecimal perTicketPriceGrowth = fillingGrowthPotential.divide(new BigDecimal(flight.getAircraft().getModel()
                .getPassengerSeatsQuantity()), HALF_UP);
        BigDecimal perDayPriceGrowth = timeGrowthPotential.divide(new BigDecimal(tariffsDetails
                .getDaysCountBeforeTicketPriceStartsToGrow()), HALF_UP);

        LocalDateTime utcTimePointPriceStartsToGrow = flight.getDepartureUtcDateTime()
                .minusDays(tariffsDetails.getDaysCountBeforeTicketPriceStartsToGrow());
        long daysBetweenGrowthStartAndNow = utcTimePointPriceStartsToGrow.until(LocalDateTime.now(ZoneId.of("UTC")), DAYS);

        if (daysBetweenGrowthStartAndNow > 0) {
            ticketPrice = ticketPrice.add(perDayPriceGrowth.multiply(new BigDecimal(daysBetweenGrowthStartAndNow)));
        }

        if (ticketsQuantity > 0) {
            ticketPrice = ticketPrice.add(perTicketPriceGrowth.multiply(new BigDecimal(ticketsQuantity)));
        }

        return ticketPrice;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
