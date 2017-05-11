package com.malikov.ticketsystem;

import com.malikov.ticketsystem.matchers.ModelMatcher;
import com.malikov.ticketsystem.model.Airport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.malikov.ticketsystem.CityTestData.*;

/**
 * @author Yurii Malikov
 */
public class AirportTestData {

    private static final Logger LOG = LoggerFactory.getLogger(AirportTestData.class);

    public static final Airport AIRPORT_1_BORISPOL = new Airport(1L, "Boryspil International Airport", KYIV);

    public static final Airport AIRPORT_2_HEATHROW = new Airport(2L, "Heathrow Airport", LONDON);

    public static final Airport AIRPORT_3_LUTON = new Airport(3L, "London Luton Airport", LONDON);

    public static final Airport AIRPORT_4_DA_VINCI = new Airport(4L, "Leonardo da Vinci International Airport", ROME);

    public static final List<Airport> AIRPORTS =
            Arrays.asList(AIRPORT_1_BORISPOL, AIRPORT_2_HEATHROW, AIRPORT_3_LUTON, AIRPORT_4_DA_VINCI);

    public static final ModelMatcher<Airport> MATCHER = ModelMatcher.of(Airport.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getCity(), actual.getCity())));

}
