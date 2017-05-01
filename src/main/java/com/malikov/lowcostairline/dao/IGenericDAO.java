package com.malikov.lowcostairline.dao;

/**
 * @author Yurii Malikov
 */
public interface IGenericDAO<T> {

    T get(long id);

    long create(T t);

    boolean update(T t);

    boolean delete(long id);

}
