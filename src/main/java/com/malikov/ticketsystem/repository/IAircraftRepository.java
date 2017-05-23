package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Aircraft;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAircraftRepository extends IGenericRepository<Aircraft> {

    List<Aircraft> getByNameMask(String nameMask);

    Aircraft getByName(String name);
}
