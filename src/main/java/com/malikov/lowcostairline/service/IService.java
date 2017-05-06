package com.malikov.lowcostairline.service;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IService<T> {

    T save(T t);

    void update(T t);

    T get(long id);

    List<T> getAll();

    void delete(long id);

}
