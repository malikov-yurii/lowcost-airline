package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.dto.FlightDTO;
import com.malikov.ticketsystem.dto.FlightManageableDTO;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class FlightUtil {

    public  static FlightDTO asDTO(Flight flight, BigDecimal ticketPrice){
        return new FlightDTO(flight.getId(),
                             flight.getDepartureAirport().getName(),
                             flight.getArrivalAirport().getName(),
                             DateTimeUtil.utcToZoneId(flight.getDepartureUtcDateTime(),
                                     flight.getDepartureAirport().getCity().getZoneId()),
                             DateTimeUtil.utcToZoneId(flight.getArrivalUtcDateTime(),
                                     flight.getArrivalAirport().getCity().getZoneId()),
                             ticketPrice
                );
    }


    public  static FlightManageableDTO asManageableDTO(Flight flight){
        return new FlightManageableDTO(flight.getId(),
                             flight.getDepartureAirport().getName(),
                             flight.getArrivalAirport().getName(),
                             DateTimeUtil.utcToZoneId(flight.getDepartureUtcDateTime(),
                                     flight.getDepartureAirport().getCity().getZoneId()),
                             DateTimeUtil.utcToZoneId(flight.getArrivalUtcDateTime(),
                                     flight.getArrivalAirport().getCity().getZoneId()),
                             flight.getAircraft().getName(),
                             flight.getInitialTicketBasePrice(),
                             flight.getMaxTicketBasePrice(),
                             flight.isCanceled()
                );
    }

}
