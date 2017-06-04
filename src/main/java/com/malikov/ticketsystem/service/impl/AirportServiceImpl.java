package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.dto.AirportDTO;
import com.malikov.ticketsystem.model.Airport;
import com.malikov.ticketsystem.repository.IAirportRepository;
import com.malikov.ticketsystem.repository.ICityRepository;
import com.malikov.ticketsystem.service.IAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("airportService")
@Transactional
public class AirportServiceImpl implements IAirportService {

    @Autowired
    private IAirportRepository airportRepository;

    @Autowired
    private ICityRepository cityRepository;

    @Override
    public Airport save(Airport airport) {
        Assert.notNull(airport, "airport should not be null");

        return airportRepository.save(airport);
    }

    @Override
    public Airport update(AirportDTO airportDTO) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare dto save airport
        Assert.notNull(airportDTO, "airport should not be null");
        Airport airport = airportRepository.get(airportDTO.getId());
        airport.setName(airportDTO.getName());
        return airportRepository.save(airport);
    }

    @Override
    public Airport get(long id) {
        // TODO: 5/5/2017 check not found with id
        return airportRepository.get(id);
    }

    @Override
    public boolean delete(long id) {
        return airportRepository.delete(id);
    }

    @Override
    public List<Airport> getByNameMask(String nameMask) {
        return airportRepository.getByNameMask(nameMask);
    }

    @Override
    public Airport getByName(String name) {
        return airportRepository.getByName(name);
    }
}
