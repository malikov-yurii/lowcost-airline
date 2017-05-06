package com.malikov.lowcostairline;

import com.malikov.lowcostairline.matchers.ModelMatcher;
import com.malikov.lowcostairline.model.AircraftModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * @author Yurii Malikov
 */
public class AircraftModelTestData {

    private static final Logger LOG = LoggerFactory.getLogger(AircraftModelTestData.class);

    public static final AircraftModel BOEING_737 = new AircraftModel(1L, "BOEING 737", 10);
    public static final AircraftModel BOEING_767 = new AircraftModel(2L, "BOEING 767", 10);

    public static final ModelMatcher<AircraftModel> MATCHER = ModelMatcher.of(AircraftModel.class,
            (expected, actual) -> expected == actual ||
                    (
                            Objects.equals(expected.getId(), actual.getId())
                                    && Objects.equals(expected.getName(), actual.getName())
                                    && Objects.equals(expected.getPassengersSeatsQuantity(), actual.getPassengersSeatsQuantity())
                    )
    );

}
