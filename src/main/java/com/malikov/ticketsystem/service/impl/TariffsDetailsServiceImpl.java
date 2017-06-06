package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.repository.ITariffsDetailsRepository;
import com.malikov.ticketsystem.service.ITariffsDetailsService;
import com.malikov.ticketsystem.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Yurii Malikov
 */
@Service
public class TariffsDetailsServiceImpl implements ITariffsDetailsService{

    @Autowired
    private ITariffsDetailsRepository repository;

    @Override
    public TariffsDetails getActive() {
        return repository.getActiveTariffsDetails();
    }

    @Override
    public void update(TariffsDetails tariffsDetails)
    {
        ValidationUtil.checkSuccess(repository.save(tariffsDetails),
                "not found tariffs details with id=" + tariffsDetails.getId());
    }
}
