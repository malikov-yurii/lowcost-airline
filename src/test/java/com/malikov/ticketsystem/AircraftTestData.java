package com.malikov.ticketsystem;

import com.malikov.ticketsystem.matchers.ModelMatcher;
import com.malikov.ticketsystem.model.Aircraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.malikov.ticketsystem.AircraftModelTestData.BOEING_737;
import static com.malikov.ticketsystem.AircraftModelTestData.BOEING_767;

/**
 * @author Yurii Malikov
 */
public class AircraftTestData {

    private static final Logger LOG = LoggerFactory.getLogger(AircraftTestData.class);

    public static final Aircraft AIRCRAFT_1 = new Aircraft(1L, "B737-1", BOEING_737);

    public static final Aircraft AIRCRAFT_2 = new Aircraft(2L, "B767-2", BOEING_767);

    public static final Aircraft AIRCRAFT_3 = new Aircraft(3L, "B767-3", BOEING_767);

    public static final ModelMatcher<Aircraft> MATCHER = ModelMatcher.of(Aircraft.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getModel(), actual.getModel())));

}
