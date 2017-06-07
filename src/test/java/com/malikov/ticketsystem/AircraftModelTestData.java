package com.malikov.ticketsystem;

import com.malikov.ticketsystem.model.AircraftModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yurii Malikov
 */
public class AircraftModelTestData {

    private static final Logger LOG = LoggerFactory.getLogger(AircraftModelTestData.class);

    public static final AircraftModel BOEING_737 = new AircraftModel(1L, "BOEING 737", 10);

    public static final AircraftModel BOEING_767 = new AircraftModel(2L, "BOEING 767", 10);
}
