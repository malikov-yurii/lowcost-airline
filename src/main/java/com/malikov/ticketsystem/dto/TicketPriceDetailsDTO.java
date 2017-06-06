package com.malikov.ticketsystem.dto;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class TicketPriceDetailsDTO {

    private BigDecimal baseTicketPrice;

    private BigDecimal baggagePrice;

    private BigDecimal priorityRegistrationAndBoardingPrice;

    public TicketPriceDetailsDTO(BigDecimal baseTicketPrice, BigDecimal baggagePrice, BigDecimal priorityRegistrationAndBoardingPrice) {
        this.baseTicketPrice = baseTicketPrice;
        this.baggagePrice = baggagePrice;
        this.priorityRegistrationAndBoardingPrice = priorityRegistrationAndBoardingPrice;
    }

    public BigDecimal getBaseTicketPrice() {
        return baseTicketPrice;
    }

    public void setBaseTicketPrice(BigDecimal baseTicketPrice) {
        this.baseTicketPrice = baseTicketPrice;
    }

    public BigDecimal getBaggagePrice() {
        return baggagePrice;
    }

    public void setBaggagePrice(BigDecimal baggagePrice) {
        this.baggagePrice = baggagePrice;
    }

    public BigDecimal getPriorityRegistrationAndBoardingPrice() {
        return priorityRegistrationAndBoardingPrice;
    }

    public void setPriorityRegistrationAndBoardingPrice(BigDecimal priorityRegistrationAndBoardingPrice) {
        this.priorityRegistrationAndBoardingPrice = priorityRegistrationAndBoardingPrice;
    }
}
