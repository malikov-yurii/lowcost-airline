package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.to.TicketDTO;

/**
 * @author Yurii Malikov
 */
public class TicketUtil {

    // TODO: 5/30/2017 where should be converting logic? Here or in dto constructor
    public static TicketDTO asDTO(Ticket ticket) {
        return new TicketDTO(ticket.getId(), ticket.getDepartureAirportName(), ticket.getArrivalAirportName(),
                ticket.getDepartureCityName(), ticket.getArrivalCityName()
                , DateTimeUtil.utcToZoneId(ticket.getDepartureUtcDateTime(), ticket.getDepartureZoneId())
                , ticket.getArrivalOffsetDateTime().toLocalDateTime()
                , ticket.getPrice() , ticket.getWithBaggage(), ticket.getWithPriorityRegistration(), ticket.getSeatNumber());

    }

}
