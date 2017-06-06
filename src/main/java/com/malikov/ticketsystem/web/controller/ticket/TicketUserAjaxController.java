package com.malikov.ticketsystem.web.controller.ticket;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketPriceDetailsDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.service.IFlightService;
import com.malikov.ticketsystem.service.ITicketService;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
    private ITicketService ticketService;

    @Autowired
    private IFlightService flightService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelMap getActiveUserTicketsWithRemainingDelaysPage(@RequestParam(value = "draw") Integer draw,
                                                                @RequestParam(value = "start") Integer startingFrom,
                                                                @RequestParam(value = "length") Integer pageCapacity) {
        List<TicketWithRemainingDelayDTO> ticketWithRemainingDelayDTOs =
                ticketService.getActiveTicketsDelays(AuthorizedUser.id(),startingFrom, pageCapacity);
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


    @GetMapping(value = "/details-with-free-seats")
    public ModelMap getTicketDetailsWithFreeSeats(@RequestParam(value = "flightId") long flightId,
                                                  HttpSession session) {
        ModelMap model = new ModelMap();
        Flight flight = flightService.get(flightId);
        TicketPriceDetailsDTO ticketPriceDetailsDTO = flightService.getTicketPriceDetails(flight.getId());

        session.setAttribute("flightId", flight.getId());
        session.setAttribute("ticketPriceDetails", ticketPriceDetailsDTO);

        model.put("flightId", flight.getId());
        model.put("ticketPriceDetails", ticketPriceDetailsDTO);

        model.put("departureAirport", flight.getDepartureAirport().getName());
        model.put("arrivalAirport", flight.getArrivalAirport().getName());

        model.put("departureCity", flight.getDepartureAirport().getCity().getName());
        model.put("arrivalCity", flight.getArrivalAirport().getCity().getName());

        model.put("departureLocalDateTime", DateTimeUtil.utcToZoneId(flight.getDepartureUtcDateTime(),
                flight.getDepartureAirport().getCity().getZoneId()));
        model.put("arrivalLocalDateTime", DateTimeUtil.utcToZoneId(flight.getArrivalUtcDateTime(),
                flight.getArrivalAirport().getCity().getZoneId()));

        model.put("totalSeats", flight.getAircraft().getModel().getPassengerSeatsQuantity());
        model.put("freeSeats", flightService.getFreeSeats(flight.getId()).toArray(new Integer[0]));

        // TODO: 6/6/2017 should i use return ResponseEntity.ok(model); instead
        return model;
    }

    @PostMapping
    public ModelMap createNewBookedTicket(@Valid TicketDTO ticketDTO, HttpSession session) {
        Long flightId = (Long) session.getAttribute("flightId");
        Ticket bookedTicket = ticketService.createNewBookedTicketAndScheduledTask(ticketDTO, flightId,
                (TicketPriceDetailsDTO) session.getAttribute("ticketPriceDetails"));
        ModelMap model = new ModelMap();
        model.put("bookedTicketId", bookedTicket.getId());
        model.put("bookedTicketTotalPrice", bookedTicket.getPrice());
        model.put("bookingDuration", BOOKING_DURATION_MILLIS);

        return model;
    }

    @PutMapping(value = "/{id}/confirm-payment")
    public void confirmPayment(@PathVariable("id") Long requestTicketId,
                                         @RequestParam(value = "purchaseOffsetDateTime") /* todo not working OffsetDateTime*/ String purchaseOffsetDateTime) {
        ticketService.processPaymentByUser(requestTicketId, OffsetDateTime.parse(purchaseOffsetDateTime));
    }


    @PutMapping(value = "/{id}/cancel-booking")
    public void cancelBooking(@PathVariable("id") Long ticketId) {
        ticketService.cancelBooking(ticketId);
    }
}
