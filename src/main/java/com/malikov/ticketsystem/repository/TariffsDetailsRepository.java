package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.TariffsDetails;

/**
 * @author Yurii Malikov
 */
public interface TariffsDetailsRepository extends GenericRepository<TariffsDetails> {

    /**
     * @return active tariff details
     */
    TariffsDetails getActiveTariffsDetails();
}
