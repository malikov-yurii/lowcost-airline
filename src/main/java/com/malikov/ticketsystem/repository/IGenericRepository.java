package com.malikov.ticketsystem.repository;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IGenericRepository<T> {

    T save(T t);

    // false if not found
    boolean delete(long id);

    // TODO: 5/8/2017 Should name them properties or hints
    // null if not found
    T get(long id, String... hintNames);

    List<T> getAll();

}
