package com.malikov.ticketsystem.dto;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class TicketPriceDetailsDTO {

    private BigDecimal baseTicketPrice;

    private BigDecimal baggagePrice;

    private BigDecimal priorityRegistrationAndBoardingPrice;

    public TicketPriceDetailsDTO() {
    }

    public TicketPriceDetailsDTO(BigDecimal baseTicketPrice, BigDecimal baggagePrice,
                                 BigDecimal priorityRegistrationAndBoardingPrice) {
        this.baseTicketPrice = baseTicketPrice.setScale(2);
        this.baggagePrice = baggagePrice.setScale(2);
        this.priorityRegistrationAndBoardingPrice = priorityRegistrationAndBoardingPrice.setScale(2);
    }

    public BigDecimal getBaseTicketPrice() {
        return baseTicketPrice;
    }

    public void setBaseTicketPrice(BigDecimal baseTicketPrice) {
        this.baseTicketPrice = baseTicketPrice.setScale(2);
    }

    public BigDecimal getBaggagePrice() {
        return baggagePrice;
    }

    public void setBaggagePrice(BigDecimal baggagePrice) {
        this.baggagePrice = baggagePrice.setScale(2);
    }

    public BigDecimal getPriorityRegistrationAndBoardingPrice() {
        return priorityRegistrationAndBoardingPrice;
    }

    public void setPriorityRegistrationAndBoardingPrice(BigDecimal priorityRegistrationAndBoardingPrice) {
        this.priorityRegistrationAndBoardingPrice = priorityRegistrationAndBoardingPrice.setScale(2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketPriceDetailsDTO that = (TicketPriceDetailsDTO) o;

        if (baseTicketPrice != null
                ? !baseTicketPrice.equals(that.baseTicketPrice)
                : that.baseTicketPrice != null) {
            return false;
        }
        if (baggagePrice != null
                ? !baggagePrice.equals(that.baggagePrice)
                : that.baggagePrice != null) {
            return false;
        }
        return priorityRegistrationAndBoardingPrice != null
                ? priorityRegistrationAndBoardingPrice.equals(that.priorityRegistrationAndBoardingPrice)
                : that.priorityRegistrationAndBoardingPrice == null;
    }

    @Override
    public int hashCode() {
        int result = baseTicketPrice != null ? baseTicketPrice.hashCode() : 0;
        result = 31 * result + (baggagePrice != null ? baggagePrice.hashCode() : 0);
        result = 31 * result + (priorityRegistrationAndBoardingPrice != null
                ? priorityRegistrationAndBoardingPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketPriceDetailsDTO{" +
                "baseTicketPrice=" + baseTicketPrice +
                ", baggagePrice=" + baggagePrice +
                ", priorityRegistrationAndBoardingPrice=" + priorityRegistrationAndBoardingPrice +
                '}';
    }
}
