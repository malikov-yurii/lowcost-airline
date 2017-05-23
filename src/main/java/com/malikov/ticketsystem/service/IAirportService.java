package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Airport;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAirportService extends IService<Airport> {

    List<Airport> getByNameMask(String nameMask);

    Airport getByName(String name);
}
