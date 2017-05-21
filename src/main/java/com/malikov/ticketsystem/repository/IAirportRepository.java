package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Airport;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAirportRepository extends IGenericRepository<Airport> {

    List<Airport> getByNameMask(String nameMask);
}
