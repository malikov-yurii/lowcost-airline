package com.malikov.ticketsystem.repository;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IGenericRepository<T> {

    T save(T t);

    // false if not found
    boolean delete(long id);

    // null if not found
    T get(long id);

    List<T> getAll();

}
