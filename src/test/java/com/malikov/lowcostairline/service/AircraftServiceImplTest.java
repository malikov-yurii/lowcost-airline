package com.malikov.lowcostairline.service;

import com.malikov.lowcostairline.model.Aircraft;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.Arrays;

import static com.malikov.lowcostairline.AircraftModelTestData.BOEING_737;
import static com.malikov.lowcostairline.AircraftTestData.*;

/**
 * @author Yurii Malikov
 */
public class AircraftServiceImplTest extends AbstractServiceTest {

    @Autowired
    protected IAircraftService service;

    @Test
    public void save() throws Exception {
        Aircraft newAircraft = getNewDummyAircraftWithNullId(null);
        Aircraft created = service.save(newAircraft);
        newAircraft.setId(created.getId());
        MATCHER.assertCollectionEquals(
                Arrays.asList(AIRCRAFT_1, AIRCRAFT_2, AIRCRAFT_3, newAircraft),
                service.getAll());
    }

    @Test
    public void update() throws Exception {
        Aircraft updated = new Aircraft(AIRCRAFT_1);
        updated.setName("NewAircraftName");
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(updated.getId()));
    }

    @Test
    public void get() throws Exception {
        Aircraft aircraft = service.get(AIRCRAFT_2.getId());
        MATCHER.assertEquals(AIRCRAFT_2, aircraft);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(AIRCRAFT_1, AIRCRAFT_2, AIRCRAFT_3), service.getAll());
    }

    // TODO: 5/6/2017 How to test delete despite constraints? At first I sould delete planes? perhaps
    @Test(expected = PersistenceException.class)
    public void delete() throws Exception {
        service.delete(AIRCRAFT_2.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(AIRCRAFT_1, AIRCRAFT_3), service.getAll());
    }

    private Aircraft getNewDummyAircraftWithNullId(Long id) {
        return new Aircraft(id, "newAircraftName", BOEING_737);
    }

}
