package com.malikov.ticketsystem.web.ticket;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.to.TicketDTO;
import com.malikov.ticketsystem.util.DateTimeUtil;
import com.malikov.ticketsystem.util.TicketUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

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

        BigDecimal sessionTicketPrice = (BigDecimal) session.getAttribute("ticketPrice");
        if (!sessionTicketPrice.equals(ticketDTO.getPrice())) {
            // TODO: 6/1/2017 Send email to admin about fraud attempt (id of user, flight, price fraud)
            ticketDTO.setPrice(sessionTicketPrice);
        }

        TicketUtil.updateFromDTO(newTicket, ticketDTO);

        Ticket bookedTicket = ticketService.createNewBookedTicketAndScheduledTask(newTicket);

        ModelMap model = new ModelMap();

        if (bookedTicket == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("That seat has been purchased already.");
        }

        session.setAttribute("bookedTicketId", bookedTicket.getId());

        return ResponseEntity.ok(bookedTicket.getId());

    }

    @PutMapping(value = "/{id}/confirm-payment")
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity confirmPayment(@PathVariable("id") Long requestTicketId,
                                         @RequestParam(value = "purchaseOffsetDateTime") String  purchaseOffsetDateTime, HttpSession session) {


        //in cancel-booking there is code duplication
        Long sessionTicketId = (Long) session.getAttribute("bookedTicketId");
        if (requestTicketId == null || sessionTicketId == null || !sessionTicketId.equals(requestTicketId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing proper ticket id in session or request parameters");
        }


        // TODO: 6/1/2017 is it ok when service returns Response Entity (need that for transaction)
        return ticketService.processPayment(sessionTicketId);
    }

    @PutMapping(value = "/{id}/cancel-booking")
    // TODO: 6/1/2017 validate dto?
    public ResponseEntity cancelBooking(@PathVariable("id") Long requestTicketId, HttpSession session) {


        Long sessionTicketId = (Long) session.getAttribute("bookedTicketId");
        if (requestTicketId == null || sessionTicketId == null || !sessionTicketId.equals(requestTicketId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing proper ticket id in session or request parameters");
        }

        if (!ticketService.cancelBooking(sessionTicketId, AuthorizedUser.id())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed cancelling booking for ticket with provided id.");
        }
        return new ResponseEntity(HttpStatus.OK);
    }


    @GetMapping(value = "/details-with-free-seats")
    public ModelMap getDetailsWithFreeSeats(@RequestParam(value = "flightId") Long flightId, HttpSession session) {

        Flight flight = flightService.get(flightId);
        session.setAttribute("flightId", flight.getId());

        User user = userService.get(AuthorizedUser.id());

        ModelMap model = new ModelMap();


        /* We have to get all ticket data one more time
        in case it was modified between rendering table and pressing purchase ticket button */

        model.put("departureAirport", flight.getDepartureAirport().getName());
        model.put("arrivalAirport", flight.getArrivalAirport().getName());

        model.put("departureCity", flight.getDepartureAirport().getCity().getName());
        model.put("arrivalCity", flight.getArrivalAirport().getCity().getName());

        model.put("departureLocalDateTime", DateTimeUtil.utcToZoneId(flight.getDepartureUtcDateTime(),
                flight.getDepartureAirport().getCity().getZoneId()));
        model.put("arrivalLocalDateTime", DateTimeUtil.utcToZoneId(flight.getArrivalUtcDateTime(),
                flight.getArrivalAirport().getCity().getZoneId()));

        BigDecimal ticketPrice = flightService.getTicketPrice(flight);
        session.setAttribute("ticketPrice", ticketPrice);

        model.put("price", ticketPrice);
        model.put("flightId", flight.getId());

        // TODO: 6/1/2017 consider using it as a check
        //session.setAttribute("flightId", flight.getId());

        model.put("totalSeats", flight.getAircraft().getModel().getPassengersSeatsQuantity());
        model.put("freeSeats", ticketService.getFreeSeats(flight).toArray(new Integer[0]));

        return model;
    }

}
