package com.malikov.ticketsystem;

import com.malikov.ticketsystem.model.City;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;

/**
 * @author Yurii Malikov
 */
public class CityTestData {

    private static final Logger LOG = LoggerFactory.getLogger(CityTestData.class);

    public static final City KYIV = new City(1L, "Kyiv", ZoneId.of("Europe/Kiev"));

    public static final City LONDON = new City(2L, "London", ZoneId.of("Europe/London"));

    public static final City ROME = new City(3L, "Rome", ZoneId.of("Europe/Rome"));
}
