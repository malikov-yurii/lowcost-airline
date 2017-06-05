package com.malikov.ticketsystem.controller.ticket;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.TicketPriceDetails;
import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import static com.malikov.ticketsystem.util.DateTimeUtil.BOOKING_DURATION_MILLIS;

/**
 * @author Yurii Malikov
 */
@RestController
@RequestMapping(value = "/ajax/user/ticket")
public class TicketUserAjaxController {

    @Autowired
    ITicketService ticketService;

    @Autowired
    IFlightService flightService;

    @Autowired
    IUserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelMap getActiveUserTicketsWithRemainingDelaysPage(@RequestParam(value = "draw") Integer draw,
                                                                @RequestParam(value = "start") Integer startingFrom,
                                                                @RequestParam(value = "length") Integer pageCapacity) {
        List<TicketWithRemainingDelayDTO> ticketWithRemainingDelayDTOs =
                ticketService.getActiveTicketsDelaysByUserId(AuthorizedUser.id(),startingFrom, pageCapacity);
        ModelMap model = new ModelMap();
        int dataTableHasNextPageIndicator = startingFrom + ticketWithRemainingDelayDTOs.size() + 1;

        if (ticketWithRemainingDelayDTOs.size() > pageCapacity) {
            ticketWithRemainingDelayDTOs.remove(ticketWithRemainingDelayDTOs.size() - 1);
        }

        model.put("draw", draw);
        model.put("recordsTotal", dataTableHasNextPageIndicator);
        model.put("recordsFiltered", dataTableHasNextPageIndicator);
        model.put("data", ticketWithRemainingDelayDTOs);

        return model;
    }

    @GetMapping(value = "/archived", produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelMap getArchivedUserTickets(@RequestParam(value = "draw") Integer draw,
                                                                @RequestParam(value = "start") Integer startingFrom,
                                                                @RequestParam(value = "length") Integer pageCapacity) {
        List<TicketDTO> ticketDTOs =
                ticketService.getArchivedTickets(AuthorizedUser.id(),startingFrom, pageCapacity);
        ModelMap model = new ModelMap();

        int dataTableHasNextPageIndicator = startingFrom + ticketDTOs.size() + 1;

        if (ticketDTOs.size() > pageCapacity) {
            ticketDTOs.remove(ticketDTOs.size() - 1);
        }

        model.put("draw", draw);
        model.put("recordsTotal", dataTableHasNextPageIndicator);
        model.put("recordsFiltered", dataTableHasNextPageIndicator);
        model.put("data", ticketDTOs);

        return model;
    }


    @PostMapping
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity createNewBookedTicket(TicketDTO ticketDTO, HttpSession session) {

        Long flightId = (Long) session.getAttribute("flightId");

        Ticket newTicket = new Ticket();

        Flight flight = flightService.get(flightId);
        newTicket.setFlight(flight);
        newTicket.setDepartureZoneId(flight.getDepartureAirport().getCity().getZoneId());

        User user = userService.get(AuthorizedUser.id());
        newTicket.setUser(user);

        TicketPriceDetails sessionTicketPriceDetails = (TicketPriceDetails) session.getAttribute("ticketPriceDetails");
        BigDecimal ticketPrice = sessionTicketPriceDetails.getBaseTicketPrice();
        if (ticketDTO.getWithBaggage() != null && ticketDTO.getWithBaggage()) {
            ticketPrice = ticketPrice.add(sessionTicketPriceDetails.getBaggagePrice());
        }
        if (ticketDTO.getWithPriorityRegistrationAndBoarding() != null && ticketDTO.getWithPriorityRegistrationAndBoarding()) {
            ticketPrice = ticketPrice.add(sessionTicketPriceDetails.getPriorityRegistrationAndBoardingPrice());
        }
        if (!ticketPrice.equals(ticketDTO.getPrice())) {
            // TODO: 6/1/2017 Send email dto admin about fraud attempt (id of user, flight, price fraud)
            ticketDTO.setPrice(ticketPrice);
        }

        TicketUtil.updateFromDTO(newTicket, ticketDTO);

        Ticket bookedTicket = ticketService.createNewBookedTicketAndScheduledTask(newTicket);

        if (bookedTicket == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("That seat has been purchased already.");
        }

        session.setAttribute("bookedTicketId", bookedTicket.getId());
        //session.setAttribute("bookedTicketTotalPrice", bookedTicket.getPrice());

        ModelMap modelMap = new ModelMap();
        modelMap.put("bookedTicketId", bookedTicket.getId());
        modelMap.put("bookedTicketTotalPrice", bookedTicket.getPrice());
        modelMap.put("bookingDuration", BOOKING_DURATION_MILLIS);

        return ResponseEntity.ok(modelMap);

    }

    @PutMapping(value = "/{id}/confirm-payment")
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity confirmPayment(@PathVariable("id") Long requestTicketId,
                                         @RequestParam(value = "purchaseOffsetDateTime") /* todo not working OffsetDateTime*/ String purchaseOffsetDateTime                                         ) {


        // TODO: 6/1/2017 is it ok when service returns Response Entity (need that for transaction)
        return ticketService.processPayment(requestTicketId, OffsetDateTime.parse(purchaseOffsetDateTime));
    }

    @PutMapping(value = "/{id}/cancel-booking")
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity cancelBooking(@PathVariable("id") Long ticketId, HttpSession session) {
        if (!ticketService.cancelBooking(ticketId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed cancelling booking for ticket with provided id.");
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(value = "/details-with-free-seats")
    public ModelMap getTicketDetailsWithFreeSeats(@RequestParam(value = "flightId") Long flightId,
                                                  HttpSession session) {
        Flight flight = flightService.get(flightId);
        session.setAttribute("flightId", flight.getId());

        User user = userService.get(AuthorizedUser.id());

        ModelMap model = new ModelMap();


        /* We have dto get all ticket data one more time
        in case it was modified between rendering table and pressing purchase ticket button */

        model.put("departureAirport", flight.getDepartureAirport().getName());
        model.put("arrivalAirport", flight.getArrivalAirport().getName());

        model.put("departureCity", flight.getDepartureAirport().getCity().getName());
        model.put("arrivalCity", flight.getArrivalAirport().getCity().getName());

        model.put("departureLocalDateTime", DateTimeUtil.utcToZoneId(flight.getDepartureUtcDateTime(),
                flight.getDepartureAirport().getCity().getZoneId()));
        model.put("arrivalLocalDateTime", DateTimeUtil.utcToZoneId(flight.getArrivalUtcDateTime(),
                flight.getArrivalAirport().getCity().getZoneId()));

        TicketPriceDetails ticketPriceDetails = flightService.getTicketPriceDetails(flight);
        session.setAttribute("ticketPriceDetails", ticketPriceDetails);

        model.put("ticketPriceDetails", ticketPriceDetails);
        model.put("flightId", flight.getId());

        // TODO: 6/1/2017 consider using it as a check
        //session.setAttribute("flightId", flight.getId());

        model.put("totalSeats", flight.getAircraft().getModel().getPassengerSeatsQuantity());
        model.put("freeSeats", ticketService.getFreeSeats(flight).toArray(new Integer[0]));

        return model;
    }

}
