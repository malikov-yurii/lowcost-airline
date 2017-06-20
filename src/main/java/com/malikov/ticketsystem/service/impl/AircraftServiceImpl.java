package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.repository.AircraftRepository;
import com.malikov.ticketsystem.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@Service
public class AircraftServiceImpl implements AircraftService {

    @Autowired
    private AircraftRepository repository;

    @Override
    public Aircraft getByName(String name) {
        return repository.getByName(name);
    }

    @Override
    public List<String> getNamesByNameMask(String nameMask) {
        return repository.getByNameMask(nameMask).stream()
                .map(Aircraft::getName)
                .collect(Collectors.toList());
    }
}
