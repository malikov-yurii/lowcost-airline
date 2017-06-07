package com.malikov.ticketsystem.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
@Entity
@Table(name = "tariffs_details")
public class TariffsDetails extends BaseEntity{

    @Column(name = "days_before_ticket_price_starts_to_grow")
    private Integer daysCountBeforeTicketPriceStartsToGrow;

    @Column(name = "weight_of_time_growth_factor")
    private BigDecimal weightOfTimeGrowthFactor;

    @Column(name = "baggage_surcharge_over_max_base_ticket_price")
    private BigDecimal baggageSurchargeOverMaxBaseTicketPrice;

    @Column(name = "priority_registration_and_boarding_tariff")
    private BigDecimal priorityRegistrationAndBoardingTariff;

    @Column(name = "active")
    // TODO: 5/28/2017 Do I need annotation below
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;

    public TariffsDetails() {
    }

    public TariffsDetails(Integer daysCountBeforeTicketPriceStartsToGrow, BigDecimal weightOfTimeGrowthFactor,
                          BigDecimal baggageSurchargeOverMaxBaseTicketPrice,
                          BigDecimal priorityRegistrationAndBoardingTariff, Boolean active) {
        this.daysCountBeforeTicketPriceStartsToGrow = daysCountBeforeTicketPriceStartsToGrow;
        this.weightOfTimeGrowthFactor = weightOfTimeGrowthFactor;
        this.baggageSurchargeOverMaxBaseTicketPrice = baggageSurchargeOverMaxBaseTicketPrice;
        this.priorityRegistrationAndBoardingTariff = priorityRegistrationAndBoardingTariff;
        this.active = active;
    }

    public Integer getDaysCountBeforeTicketPriceStartsToGrow() {
        return daysCountBeforeTicketPriceStartsToGrow;
    }

    public void setDaysCountBeforeTicketPriceStartsToGrow(Integer daysCountBeforeTicketPriceStartsToGrow) {
        this.daysCountBeforeTicketPriceStartsToGrow = daysCountBeforeTicketPriceStartsToGrow;
    }

    public BigDecimal getWeightOfTimeGrowthFactor() {
        return weightOfTimeGrowthFactor;
    }

    public void setWeightOfTimeGrowthFactor(BigDecimal weightOfTimeGrowthFactor) {
        this.weightOfTimeGrowthFactor = weightOfTimeGrowthFactor;
    }

    public BigDecimal getBaggageSurchargeOverMaxBaseTicketPrice() {
        return baggageSurchargeOverMaxBaseTicketPrice;
    }

    public void setBaggageSurchargeOverMaxBaseTicketPrice(BigDecimal baggageSurchargeOverMaxBaseTicketPrice) {
        this.baggageSurchargeOverMaxBaseTicketPrice = baggageSurchargeOverMaxBaseTicketPrice;
    }

    public BigDecimal getPriorityRegistrationAndBoardingTariff() {
        return priorityRegistrationAndBoardingTariff;
    }

    public void setPriorityRegistrationAndBoardingTariff(BigDecimal priorityRegistrationAndBoardingTariff) {
        this.priorityRegistrationAndBoardingTariff = priorityRegistrationAndBoardingTariff;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

