package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.TariffsDetails;

/**
 * @author Yurii Malikov
 */
public interface ITariffsDetailsRepository extends IGenericRepository<TariffsDetails> {

    /**
     * @return active tariff details
     */
    TariffsDetails getActiveTariffsDetails();
}
