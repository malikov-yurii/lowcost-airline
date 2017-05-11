package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.AircraftModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.malikov.ticketsystem.AircraftModelTestData.*;

/**
 * @author Yurii Malikov
 */
public class AircraftModelServiceImplTest extends AbstractServiceTest {

    @Autowired
    protected IAircraftModelService service;

    @Test
    public void save() throws Exception {
        AircraftModel newAircraftModel = getNewDummyAircraftModelWithNullId(null);
        AircraftModel created = service.save(newAircraftModel);
        newAircraftModel.setId(created.getId());
        MATCHER.assertCollectionEquals(getTestDataAircraftModelsWith(newAircraftModel), service.getAll());
    }

    @Test
    public void update() throws Exception {
        AircraftModel updated = new AircraftModel(BOEING_737);
        updated.setName("NewModelName");
        updated.setPassengersSeatsQuantity(222);
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(updated.getId()));
    }

    @Test
    public void get() throws Exception {
        AircraftModel aircraftModel = service.get(BOEING_737.getId());
        MATCHER.assertEquals(BOEING_737, aircraftModel);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(BOEING_737, BOEING_767), service.getAll());
    }

    // TODO: 5/6/2017 How to test delete despite constraints? At first I sould delete planes? perhaps
    @Test(expected = PersistenceException.class)
    public void delete() throws Exception {
        service.delete(BOEING_767.getId());
        MATCHER.assertCollectionEquals(Collections.singleton(BOEING_737), service.getAll());
    }

    private AircraftModel getNewDummyAircraftModelWithNullId(Long id) {
        return new AircraftModel(id, "newModelName", 111);
    }

    private ArrayList<AircraftModel> getTestDataAircraftModelsWith(AircraftModel newAircraftModel) {
        ArrayList<AircraftModel> aircraftModelsWithNewAircraftModel = new ArrayList<>(Arrays.asList(BOEING_737, BOEING_767));
        aircraftModelsWithNewAircraftModel.add(newAircraftModel);
        return aircraftModelsWithNewAircraftModel;
    }

}
