package com.malikov.lowcostairline.service.impl;

import com.malikov.lowcostairline.model.AircraftModel;
import com.malikov.lowcostairline.repository.IAircraftModelRepository;
import com.malikov.lowcostairline.service.IAircraftModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("aircraftModelService")
public class AircraftModelServiceImpl implements IAircraftModelService {

    @Autowired
    private IAircraftModelRepository repository;

    @Override
    public AircraftModel save(AircraftModel aircraftModel) {
        Assert.notNull(aircraftModel, "aircraftModel should not be null");
        // TODO: 5/5/2017 prepare aircraftModel to save
        return repository.save(aircraftModel);
    }

    @Override
    public void update(AircraftModel aircraftModel) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save aircraftModel
        Assert.notNull(aircraftModel, "aircraftModel should not be null");
        repository.save(aircraftModel);
    }

    @Override
    public AircraftModel get(long id, String... hintNames) {
        // TODO: 5/5/2017 check not found with id
        return repository.get(id, hintNames);
    }

    @Override
    public List<AircraftModel> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        repository.delete(id);
    }

}
