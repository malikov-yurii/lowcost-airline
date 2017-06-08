package com.malikov.ticketsystem.util.dtoconverter;

import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.util.DateTimeUtil;

import java.time.LocalDateTime;
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
                ticket.isHasBaggage(), ticket.isHasPriorityRegistration(),
                ticket.getSeatNumber(), ticket.getStatus());
    }

    public static TicketWithRemainingDelayDTO getTicketWithRemaimingDelayDTO(Ticket ticket, Long remaininDelay) {
        return new TicketWithRemainingDelayDTO(ticket.getId(), ticket.getPassengerFirstName(),
                ticket.getPassengerLastName(), ticket.getDepartureAirportName(), ticket.getArrivalAirportName(),
                ticket.getDepartureCityName(), ticket.getArrivalCityName(),
                DateTimeUtil.utcToZoneId(ticket.getDepartureUtcDateTime(), ticket.getDepartureZoneId()),
                ticket.getArrivalOffsetDateTime().toLocalDateTime(),
                ticket.getPrice(), ticket.isHasBaggage(), ticket.isHasPriorityRegistration(),
                ticket.getSeatNumber(), ticket.getStatus(), remaininDelay);
    }

    public static Ticket updateFromDTOBeforeBooking(Ticket ticket, TicketDTO ticketDTO) {
        Flight flight = ticket.getFlight();
        LocalDateTime dtoDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(ticketDTO.getDepartureLocalDateTime(),
                flight.getDepartureAirport().getCity().getZoneId());

        OffsetDateTime dtoArrivalOffsetDateTime = OffsetDateTime.of(ticketDTO.getArrivalLocalDateTime(),
                flight.getArrivalAirport().getCity().getZoneId()
                        .getRules().getOffset(ticket.getFlight().getDepartureUtcDateTime().toInstant(ZoneOffset.UTC)));

        ticket.setDepartureAirportName(ticketDTO.getDepartureAirport());
        ticket.setArrivalAirportName(ticketDTO.getArrivalAirport());
        ticket.setDepartureCityName(ticketDTO.getDepartureCity());
        ticket.setArrivalCityName(ticketDTO.getArrivalCity());
        ticket.setDepartureUtcDateTime(dtoDepartureUtcDateTime);
        ticket.setArrivalOffsetDateTime(dtoArrivalOffsetDateTime);
        ticket.setPassengerFirstName(ticketDTO.getPassengerFirstName());
        ticket.setPassengerLastName(ticketDTO.getPassengerLastName());
        ticket.setHasBaggage(ticketDTO.isHasBaggage() != null ? ticketDTO.isHasBaggage() : false);
        ticket.setHasPriorityRegistration(ticketDTO.isHasPriorityRegistrationAndBoarding() != null
                ? ticketDTO.isHasPriorityRegistrationAndBoarding()
                : false);
        ticket.setPrice(ticketDTO.getPrice());
        ticket.setSeatNumber(ticketDTO.getSeatNumber());
        return ticket;
    }
}
