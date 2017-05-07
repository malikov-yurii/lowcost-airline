package com.malikov.lowcostairline.service.impl;

import com.malikov.lowcostairline.model.Airport;
import com.malikov.lowcostairline.repository.IAirportRepository;
import com.malikov.lowcostairline.service.IAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("airportService")
public class AirportServiceImpl implements IAirportService {

    @Autowired
    private IAirportRepository repository;

    @Override
    public Airport save(Airport airport) {
        Assert.notNull(airport, "airport should not be null");
        // TODO: 5/5/2017 prepare airport to save
        return repository.save(airport);
    }

    @Override
    public void update(Airport airport) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save airport
        Assert.notNull(airport, "airport should not be null");
        repository.save(airport);
    }

    @Override
    public Airport get(long id) {
        // TODO: 5/5/2017 check not found with id
        return repository.get(id);
    }

    @Override
    public List<Airport> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

}
