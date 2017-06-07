package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.repository.ITariffsDetailsRepository;
import com.malikov.ticketsystem.service.ITariffsDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFoundById;
import static com.malikov.ticketsystem.util.ValidationUtil.checkSuccess;

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
    public void update(TariffsDetails tariffsDetailsDTO)
    {
        TariffsDetails tariffsDetails = checkSuccess(getActive(),"not found active tariff details");
        tariffsDetails.setBaggageSurchargeOverMaxBaseTicketPrice(tariffsDetailsDTO.getBaggageSurchargeOverMaxBaseTicketPrice());
        tariffsDetails.setPriorityRegistrationAndBoardingTariff(tariffsDetailsDTO.getPriorityRegistrationAndBoardingTariff());
        tariffsDetails.setDaysCountBeforeTicketPriceStartsToGrow(tariffsDetailsDTO.getDaysCountBeforeTicketPriceStartsToGrow());
        tariffsDetails.setWeightOfTimeGrowthFactor(tariffsDetailsDTO.getWeightOfTimeGrowthFactor());
        tariffsDetails.setActive(tariffsDetailsDTO.getActive());
        checkNotFoundById(repository.save(tariffsDetails), tariffsDetailsDTO.getId());
    }
}
