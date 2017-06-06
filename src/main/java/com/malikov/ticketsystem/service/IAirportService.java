package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Airport;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAirportService {

    /**
     * @return airport which full name exactly matches parameter or null if not found
     */
    Airport getByName(String name);

    /**
     * @return list of airport names which matches nameMask or empty list if not found any
     */
    List<String> getNamesByNameMask(String nameMask);
}
