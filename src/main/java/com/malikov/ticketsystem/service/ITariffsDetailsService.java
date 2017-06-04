package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.TariffsDetails;

/**
 * @author Yurii Malikov
 */
public interface ITariffsDetailsService {

    TariffsDetails getActive();

    TariffsDetails update(TariffsDetails tariffsDetails);

}
