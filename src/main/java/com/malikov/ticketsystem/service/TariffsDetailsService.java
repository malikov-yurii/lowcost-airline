package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.util.exception.NotFoundException;

/**
 * @author Yurii Malikov
 */
public interface TariffsDetailsService {

    /**
     * @return tariff details with active = true
     * @throws NotFoundException if not found active tariff details
     */
    TariffsDetails getActive() throws NotFoundException;

    /**
     * @throws NotFoundException if not found
     */
    void update(TariffsDetails tariffsDetails) throws NotFoundException;
}
