package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.to.AirportTo;

/**
 * @author Yurii Malikov
 */
public class AirportUtil {

    public static AirportTo asTo(Airport airport) {
        return new AirportTo(airport.getName());
    }

}
