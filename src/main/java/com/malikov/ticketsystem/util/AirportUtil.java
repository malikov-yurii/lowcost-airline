package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.dto.AirportDTO;

/**
 * @author Yurii Malikov
 */
public class AirportUtil {

    public static AirportDTO asTo(Airport airport) {
        return new AirportDTO(airport.getId(), airport.getName(), airport.getCity().getName());
    }


}
