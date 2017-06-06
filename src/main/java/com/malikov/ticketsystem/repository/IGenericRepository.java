package com.malikov.ticketsystem.repository;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IGenericRepository<T> {

    /**
     * @return persisted entity if it is new otherwise updated entity or null if not found by id
     */
    T save(T t);

    /**
     * @return true if successfully deleted, false if entity with id has not been found
     */
    boolean delete(long id);


    /**
     * @return entity by id or null if entity with such id does not exists
     */
    T get(long id);

    /**
     * @return all entities or empty list if not found any
     */
    List<T> getAll();

}
