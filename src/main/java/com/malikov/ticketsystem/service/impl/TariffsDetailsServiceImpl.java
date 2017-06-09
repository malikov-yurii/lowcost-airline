package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.model.TariffsDetails;
import com.malikov.ticketsystem.repository.ITariffsDetailsRepository;
import com.malikov.ticketsystem.service.ITariffsDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import static com.malikov.ticketsystem.util.MessageUtil.getMessage;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFound;

/**
 * @author Yurii Malikov
 */
@Service("tariffService")
public class TariffsDetailsServiceImpl implements ITariffsDetailsService, MessageSourceAware {

    private static final Logger LOG = LoggerFactory.getLogger(TariffsDetailsServiceImpl.class);

    private MessageSource messageSource;

    @Autowired
    private ITariffsDetailsRepository repository;

    @Override
    public TariffsDetails getActive() {
        return repository.getActiveTariffsDetails();
    }

    @Override
    public void update(TariffsDetails tariffsDetailsDTO) {
        TariffsDetails tariffsDetails = checkNotFound(getActive(),
                getMessage(messageSource, "exception.notFoundByActiveTariffDetails"));
        tariffsDetails.setBaggageSurchargeOverMaxBaseTicketPrice(tariffsDetailsDTO
                .getBaggageSurchargeOverMaxBaseTicketPrice());
        tariffsDetails.setPriorityRegistrationAndBoardingTariff(tariffsDetailsDTO
                .getPriorityRegistrationAndBoardingTariff());
        tariffsDetails.setDaysCountBeforeTicketPriceStartsToGrow(tariffsDetailsDTO
                .getDaysCountBeforeTicketPriceStartsToGrow());
        tariffsDetails.setWeightOfTimeGrowthFactor(tariffsDetailsDTO.getWeightOfTimeGrowthFactor());
        tariffsDetails.setActive(tariffsDetailsDTO.getActive());
        checkNotFound(repository.save(tariffsDetails),
                getMessage(messageSource, "exception.notFoundById") + tariffsDetailsDTO.getId());
        LOG.info("Tariff {} updated.", tariffsDetails);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
