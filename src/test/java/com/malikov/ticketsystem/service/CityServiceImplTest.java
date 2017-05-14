package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.City;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.time.ZoneId;
import java.util.Arrays;

import static com.malikov.ticketsystem.CityTestData.*;

/**
 * @author Yurii Malikov
 */
public class CityServiceImplTest extends AbstractServiceTest {

    @Autowired
    protected ICityService service;

    @Test
    public void save() throws Exception {
        City newCity = getNewDummyCityWithNullId(null);
        City created = service.save(newCity);
        newCity.setId(created.getId());
        MATCHER.assertCollectionEquals(
                Arrays.asList(KYIV, LONDON, ROME, newCity),
                service.getAll());
    }

    @Test
    public void update() throws Exception {
        City updated = new City(ROME);
        updated.setName("NewCityName");
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(updated.getId()));
    }

    @Test
    public void get() throws Exception {
        City city = service.get(LONDON.getId());
        MATCHER.assertEquals(LONDON, city);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(KYIV, LONDON, ROME), service.getAll());
    }

    // TODO: 5/6/2017 How to test delete despite constraints? At first I sould delete planes? perhaps
    @Test(expected = PersistenceException.class)
    public void delete() throws Exception {
        service.delete(ROME.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(KYIV, LONDON), service.getAll());
    }

    private City getNewDummyCityWithNullId(Long id) {
        return new City(id, "newCityName", ZoneId.of("Europe/Isle_of_Man"));
    }

}