package com.malikov.ticketsystem.util.dtoconverter;

import com.malikov.ticketsystem.dto.TicketDTO;
import com.malikov.ticketsystem.dto.TicketWithRemainingDelayDTO;
import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static com.malikov.ticketsystem.util.ValidationUtil.checkEquals;

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

    public static TicketWithRemainingDelayDTO getTicketWithRemaimingDelayDTO(Ticket ticket, Long remaininDelay) {
        return new TicketWithRemainingDelayDTO(ticket.getId(), ticket.getPassengerFirstName(),
                ticket.getPassengerLastName(), ticket.getDepartureAirportName(), ticket.getArrivalAirportName(),
                ticket.getDepartureCityName(), ticket.getArrivalCityName(),
                DateTimeUtil.utcToZoneId(ticket.getDepartureUtcDateTime(), ticket.getDepartureZoneId())
                , ticket.getArrivalOffsetDateTime().toLocalDateTime()
                , ticket.getPrice() , ticket.getWithBaggage(), ticket.getWithPriorityRegistration(),
                ticket.getSeatNumber(), ticket.getStatus(), remaininDelay);
    }

    public static Ticket updateFromDTOBeforeBooking(Ticket ticket, TicketDTO ticketDTO) {
        Flight flight = ticket.getFlight();
        LocalDateTime dtoDepartureUtcDateTime = DateTimeUtil.zoneIdToUtc(ticketDTO.getDepartureLocalDateTime(),
                flight.getDepartureAirport().getCity().getZoneId());
        checkEquals(flight.getDepartureUtcDateTime(), dtoDepartureUtcDateTime);

        OffsetDateTime dtoArrivalOffsetDateTime = OffsetDateTime.of(ticketDTO.getArrivalLocalDateTime(),
                flight.getArrivalAirport().getCity().getZoneId()
                        .getRules().getOffset(ticket.getFlight().getDepartureUtcDateTime().toInstant(ZoneOffset.UTC)));

        checkEquals(flight.getDepartureAirport().getName(), ticketDTO.getDepartureAirport());
        ticket.setDepartureAirportName(ticketDTO.getDepartureAirport());

        checkEquals(flight.getArrivalAirport().getName(), ticketDTO.getArrivalAirport());
        ticket.setArrivalAirportName(ticketDTO.getArrivalAirport());

        checkEquals(flight.getDepartureAirport().getCity().getName(), ticketDTO.getDepartureCity());
        ticket.setDepartureCityName(ticketDTO.getDepartureCity());

        checkEquals(flight.getArrivalAirport().getCity().getName(), ticketDTO.getArrivalCity());
        ticket.setArrivalCityName(ticketDTO.getArrivalCity());

        ticket.setDepartureUtcDateTime(dtoDepartureUtcDateTime);
        ticket.setArrivalOffsetDateTime(dtoArrivalOffsetDateTime);
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
}
