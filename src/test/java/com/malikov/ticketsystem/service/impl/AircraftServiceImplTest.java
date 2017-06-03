package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.Aircraft;
import com.malikov.ticketsystem.service.AbstractServiceTest;
import com.malikov.ticketsystem.service.IAircraftService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.malikov.ticketsystem.AircraftModelTestData.BOEING_737;
import static com.malikov.ticketsystem.AircraftTestData.*;

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

    // TODO: 5/6/2017 How dto test delete despite constraints? At first I sould delete planes? perhaps
    @Test(expected = PersistenceException.class)
    public void delete() throws Exception {
        service.delete(AIRCRAFT_2.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(AIRCRAFT_1, AIRCRAFT_3), service.getAll());
    }

    @Test
    public void getByNameMask() throws Exception {
        List<Aircraft> aircraftByNameMask = service.getByNameMask("73");
        MATCHER.assertCollectionEquals(Collections.singleton(AIRCRAFT_1), aircraftByNameMask);
    }

    @Test
    public void getByName() throws Exception {
        Aircraft aircraftByName = service.getByName("B767-2");
        MATCHER.assertEquals(AIRCRAFT_2, aircraftByName);
    }

    private Aircraft getNewDummyAircraftWithNullId(Long id) {
        return new Aircraft(id, "newAircraftName", BOEING_737);
    }

}
