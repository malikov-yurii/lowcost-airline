package com.malikov.lowcostairline.service.impl;

import com.malikov.lowcostairline.model.Flight;
import com.malikov.lowcostairline.repository.IFlightRepository;
import com.malikov.lowcostairline.service.IFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("flightService")
public class FlightServiceImpl implements IFlightService {

    @Autowired
    private IFlightRepository repository;

    @Override
    public Flight save(Flight flight) {
        Assert.notNull(flight, "flight should not be null");
        // TODO: 5/5/2017 prepare flight to save
        return repository.save(flight);
    }

    @Override
    public void update(Flight flight) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save flight
        Assert.notNull(flight, "flight should not be null");
        repository.save(flight);
    }

    @Override
    public Flight get(long id) {
        // TODO: 5/5/2017 check not found with id
        return repository.get(id);
    }

    @Override
    public List<Flight> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

}
