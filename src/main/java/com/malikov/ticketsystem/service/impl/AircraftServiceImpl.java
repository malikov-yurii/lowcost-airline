package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.repository.IAircraftRepository;
import com.malikov.ticketsystem.service.IAircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("aircraftService")
public class AircraftServiceImpl implements IAircraftService {

    @Autowired
    private IAircraftRepository repository;

    @Override
    public Aircraft save(Aircraft aircraft) {
        Assert.notNull(aircraft, "aircraft should not be null");
        // TODO: 5/5/2017 prepare aircraft to save
        return repository.save(aircraft);
    }

    @Override
    public void update(Aircraft aircraft) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save aircraft
        Assert.notNull(aircraft, "aircraft should not be null");
        repository.save(aircraft);
    }

    @Override
    public Aircraft get(long id, String... hintNames) {
        // TODO: 5/5/2017 check not found with id
        return repository.get(id, hintNames);
    }

    @Override
    public List<Aircraft> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

}
