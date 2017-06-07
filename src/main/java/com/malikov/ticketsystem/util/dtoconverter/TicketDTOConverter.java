package com.malikov.ticketsystem.util.dtoconverter;

import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.util.DateTimeUtil;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @author Yurii Malikov
 */
public class TicketDTOConverter {

    public static TicketDTO asDTO(Ticket ticket) {
        return new TicketDTO(ticket.getId(), ticket.getPassengerFirstName(), ticket.getPassengerLastName(),
                ticket.getDepartureAirportName(), ticket.getArrivalAirportName(), ticket.getDepartureCityName(),
                ticket.getArrivalCityName(), DateTimeUtil.utcToZoneId(ticket.getDepartureUtcDateTime(),
                ticket.getDepartureZoneId()), ticket.getArrivalOffsetDateTime().toLocalDateTime(), ticket.getPrice(),
                ticket.getWithBaggage(), ticket.getWithPriorityRegistration(),
                ticket.getSeatNumber(), ticket.getStatus());
    }

    public static Ticket updateFromDTO(Ticket ticket, TicketDTO ticketDTO) {
        Flight flight = ticket.getFlight();
        ticket.setDepartureAirportName(ticketDTO.getDepartureAirport());
        ticket.setArrivalAirportName(ticketDTO.getArrivalAirport());
        ticket.setDepartureCityName(ticketDTO.getDepartureCity());
        ticket.setArrivalCityName(ticketDTO.getArrivalCity());
        ticket.setDepartureUtcDateTime(DateTimeUtil.zoneIdToUtc(ticketDTO.getDepartureLocalDateTime(),
                flight.getDepartureAirport().getCity().getZoneId()));
        ticket.setArrivalOffsetDateTime(OffsetDateTime.of(ticketDTO.getArrivalLocalDateTime(),
                flight.getArrivalAirport().getCity().getZoneId()
                        .getRules().getOffset(ticket.getFlight().getDepartureUtcDateTime().toInstant(ZoneOffset.UTC))));
        ticket.setPassengerFirstName(ticketDTO.getPassengerFirstName());
        ticket.setPassengerLastName(ticketDTO.getPassengerLastName());
        ticket.setWithBaggage(ticketDTO.getWithBaggage() != null ? ticketDTO.getWithBaggage() : false);
        ticket.setWithPriorityRegistration(ticketDTO.getWithPriorityRegistrationAndBoarding() != null
                                              ? ticketDTO.getWithPriorityRegistrationAndBoarding()
                                              :false);
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        return ticket;
    }

    public static TicketWithRemainingDelayDTO getTicketWithRemaimingDelayDTO(Ticket ticket, Long remaininDelay) {
        return new TicketWithRemainingDelayDTO(ticket.getId(), ticket.getPassengerFirstName(),
                ticket.getPassengerLastName(), ticket.getDepartureAirportName(), ticket.getArrivalAirportName(),
                ticket.getDepartureCityName(), ticket.getArrivalCityName(),
                DateTimeUtil.utcToZoneId(ticket.getDepartureUtcDateTime(), ticket.getDepartureZoneId())
                , ticket.getArrivalOffsetDateTime().toLocalDateTime()
                , ticket.getPrice() , ticket.getWithBaggage(), ticket.getWithPriorityRegistration(),
                ticket.getSeatNumber(), ticket.getStatus(), remaininDelay);
    }
}
