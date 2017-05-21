package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.Aircraft;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IAircraftService extends IService<Aircraft> {

    List<Aircraft> getByNameMask(String nameMask);
}
