package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.City;
import com.malikov.ticketsystem.repository.ICityRepository;
import com.malikov.ticketsystem.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("cityService")
public class CityServiceImpl implements ICityService {

    @Autowired
    private ICityRepository repository;

    @Override
    public City save(City city) {
        Assert.notNull(city, "city should not be null");
        // TODO: 5/5/2017 prepare city dto save
        return repository.save(city);
    }

    @Override
    public void update(City city) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare dto save city
        Assert.notNull(city, "city should not be null");
        repository.save(city);
    }

    @Override
    public City get(long id) {
        // TODO: 5/5/2017 check not found with id
        return repository.get(id);
    }

    @Override
    public List<City> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

}
