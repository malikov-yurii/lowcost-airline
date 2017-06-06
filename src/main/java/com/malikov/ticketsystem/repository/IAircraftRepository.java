package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Aircraft;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAircraftRepository extends IGenericRepository<Aircraft> {

    /**
     * @return entity found by name or null if entity with such name does not exists
     */
    Aircraft getByName(String name);

    /**
     * @return all entities which name matches nameMask
     */
    List<Aircraft> getByNameMask(String nameMask);
}
