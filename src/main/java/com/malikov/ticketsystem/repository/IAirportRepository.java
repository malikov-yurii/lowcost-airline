package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.Airport;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAirportRepository extends IGenericRepository<Airport> {

    /**
     * @return null if not found
     * @throws IncorrectResultSizeDataAccessException if more than one
     * element has been found in the given Collection
     */
    Airport getByName(String name);

    /**
     * @return all entities which name matches nameMask
     */
    List<Airport> getByNameMask(String nameMask);
}
