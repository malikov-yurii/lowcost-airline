package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.model.Flight;
import com.malikov.ticketsystem.to.FlightTo;

/**
 * @author Yurii Malikov
 */
public class FlightUtil {

    public  static FlightTo asTo(Flight flight){
        return new FlightTo(flight.getId(),
                             flight.getDepartureAirport().getName(),
                             flight.getArrivalAirport().getName(),
                             DateTimeUtil.fromUtcToZoneId(flight.getDepartureUtcDateTime(),
                                     flight.getDepartureAirport().getCity().getZoneId()),
                             DateTimeUtil.fromUtcToZoneId(flight.getArrivalUtcDateTime(),
                                     flight.getArrivalAirport().getCity().getZoneId()),
                             flight.getAircraft().getName(),
                             flight.getInitialTicketBasePrice(),
                             flight.getMaxTicketBasePrice());
    }

}
