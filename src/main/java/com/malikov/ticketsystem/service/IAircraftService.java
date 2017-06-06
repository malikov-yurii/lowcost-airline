package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Aircraft;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAircraftService {

    /**
     * @return aircraft which full name exactly matches parameter or null if not found
     */
    Aircraft getByName(String name);

    /**
     * @return list of aircraft names which matches nameMask or empty list if not found any
     */
    List<String> getNamesByNameMask(String nameMask);
}
