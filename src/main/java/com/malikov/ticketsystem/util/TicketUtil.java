package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.to.TicketDTO;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author Yurii Malikov
 */
public class TicketUtil {

    // TODO: 5/30/2017 where should be converting logic? Here or in dto constructor
    public static TicketDTO asDTO(Ticket ticket) {
        return new TicketDTO(ticket.getId(), ticket.getPassengerFirstName(), ticket.getPassengerLastName(),
                ticket.getDepartureAirportName(), ticket.getArrivalAirportName(),
                ticket.getDepartureCityName(), ticket.getArrivalCityName()
                , DateTimeUtil.utcToZoneId(ticket.getDepartureUtcDateTime(), ticket.getDepartureZoneId())
                , ticket.getArrivalOffsetDateTime().toLocalDateTime()
                , ticket.getPrice() , ticket.getWithBaggage(), ticket.getWithPriorityRegistration(), ticket.getSeatNumber());

    }

    public static void updateFromDTO(Ticket newTicket, TicketDTO ticketDTO) {
        Flight flight = newTicket.getFlight();
        newTicket.setDepartureAirportName(ticketDTO.getDepartureAirport());
        newTicket.setArrivalAirportName(ticketDTO.getArrivalAirport());

        newTicket.setDepartureCityName(ticketDTO.getDepartureCity());
        newTicket.setArrivalCityName(ticketDTO.getArrivalCity());

        // TODO: 6/1/2017 check if flight == null and consider refactoring of offsetdatetime transforming
        newTicket.setDepartureUtcDateTime(DateTimeUtil.zoneIdToUtc(ticketDTO.getDepartureLocalDateTime(),
                flight.getDepartureAirport().getCity().getZoneId()));
        // TODO: 6/1/2017 check if flight == null and consider refactoring of offsetdatetime transforming
        newTicket.setArrivalOffsetDateTime(OffsetDateTime.of(ticketDTO.getArrivalLocalDateTime(),
                flight.getArrivalAirport().getCity().getZoneId()
                        .getRules().getOffset(newTicket.getFlight().getDepartureUtcDateTime().toInstant(ZoneOffset.UTC))));

        newTicket.setPassengerFirstName(ticketDTO.getPassengerFirstName());
        newTicket.setPassengerLastName(ticketDTO.getPassengerLastName());
        newTicket.setWithBaggage(ticketDTO.getWithBaggage());
        newTicket.setWithPriorityRegistration(ticketDTO.getWithPriorityRegistration());
        newTicket.setPrice(ticketDTO.getPrice());
        newTicket.setSeatNumber(ticketDTO.getSeatNumber());
    }
}
