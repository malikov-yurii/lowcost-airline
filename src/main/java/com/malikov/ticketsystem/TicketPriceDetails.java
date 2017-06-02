package com.malikov.ticketsystem;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class TicketPriceDetails {

    private BigDecimal baseTicketPrice;

    private BigDecimal baggagePrice;

    private BigDecimal priorityRegistrationPrice;

    public TicketPriceDetails(BigDecimal baseTicketPrice, BigDecimal baggagePrice, BigDecimal priorityRegistrationPrice) {
        this.baseTicketPrice = baseTicketPrice;
        this.baggagePrice = baggagePrice;
        this.priorityRegistrationPrice = priorityRegistrationPrice;
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

    public BigDecimal getPriorityRegistrationPrice() {
        return priorityRegistrationPrice;
    }

    public void setPriorityRegistrationPrice(BigDecimal priorityRegistrationPrice) {
        this.priorityRegistrationPrice = priorityRegistrationPrice;
    }
}
