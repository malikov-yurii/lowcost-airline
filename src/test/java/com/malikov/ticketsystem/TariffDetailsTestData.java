package com.malikov.ticketsystem;

import com.malikov.ticketsystem.model.TariffsDetails;

import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;

/**
 * @author Yurii Malikov
 */
public class TariffDetailsTestData {

    public static final TariffsDetails ACTIVE_TARIFF_DETAILS = new TariffsDetails(10,
            new BigDecimal(0.5).setScale(6, HALF_UP), new BigDecimal(2).setScale(6, HALF_UP),
            new BigDecimal(7).setScale(6, HALF_UP), true);
}
