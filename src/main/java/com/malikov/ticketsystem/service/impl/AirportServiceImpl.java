package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.repository.AirportRepository;
import com.malikov.ticketsystem.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yurii Malikov
 */
@Service
@Transactional
public class AirportServiceImpl implements AirportService {

    @Autowired
    private AirportRepository airportRepository;

    @Override
    public List<String> getNamesByNameMask(String nameMask) {
        return airportRepository.getByNameMask(nameMask).stream()
                .map(Airport::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Airport getByName(String name) {
        return airportRepository.getByName(name);
    }
}
