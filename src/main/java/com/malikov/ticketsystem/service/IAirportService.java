package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.dto.AirportDTO;
import com.malikov.ticketsystem.model.Airport;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAirportService {

    Airport save(Airport airport);

    Airport update(AirportDTO airportDTO);

    Airport get(long id);

    boolean delete(long id);

    List<Airport> getByNameMask(String nameMask);

    Airport getByName(String name);
}
