package com.malikov.ticketsystem;

import com.malikov.ticketsystem.model.TariffsDetails;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class TariffDetailsTestData {

    public static final TariffsDetails ACTIVE_TARIFF_DETAILS = new TariffsDetails(10,
            new BigDecimal(0.5), new BigDecimal(2), new BigDecimal(7), true);
}
