package com.malikov.lowcostairline.dao;

/**
 * @author Yurii Malikov
 */
public interface IGenericRepository<T> {

    T get(long id);

    long create(T t);

    boolean update(T t);

    boolean delete(long id);

}
