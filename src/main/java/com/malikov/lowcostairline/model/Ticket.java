package com.malikov.lowcostairline.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * @author Yurii Malikov
 */
public class Ticket extends BaseEntity {

    private User user;

    private Flight flight;

    private BigDecimal price;

    private ZonedDateTime purchaseDateTime;

    private boolean hasBaggage;

    private boolean hasPriorityRegistration;

    public Ticket(){}

    public Ticket(Long id, User user, Flight flight, BigDecimal price, ZonedDateTime purchaseDateTime, boolean hasBaggage, boolean hasPriorityRegistration) {
        super(id);
        this.user = user;
        this.flight = flight;
        this.price = price;
        this.purchaseDateTime = purchaseDateTime;
        this.hasBaggage = hasBaggage;
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    public Ticket(User user, Flight flight, BigDecimal price, ZonedDateTime purchaseDateTime, boolean hasBaggage, boolean hasPriorityRegistration) {
        this.user = user;
        this.flight = flight;
        this.price = price;
        this.purchaseDateTime = purchaseDateTime;
        this.hasBaggage = hasBaggage;
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ZonedDateTime getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(ZonedDateTime purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    public boolean isHasBaggage() {
        return hasBaggage;
    }

    public void setHasBaggage(boolean hasBaggage) {
        this.hasBaggage = hasBaggage;
    }

    public boolean isHasPriorityRegistration() {
        return hasPriorityRegistration;
    }

    public void setHasPriorityRegistration(boolean hasPriorityRegistration) {
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Ticket ticket = (Ticket) o;

        if (hasBaggage != ticket.hasBaggage) return false;
        if (hasPriorityRegistration != ticket.hasPriorityRegistration) return false;
        if (user != null ? !user.equals(ticket.user) : ticket.user != null) return false;
        if (flight != null ? !flight.equals(ticket.flight) : ticket.flight != null) return false;
        if (price != null ? !price.equals(ticket.price) : ticket.price != null) return false;
        return purchaseDateTime != null ? purchaseDateTime.equals(ticket.purchaseDateTime) : ticket.purchaseDateTime == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseDateTime != null ? purchaseDateTime.hashCode() : 0);
        result = 31 * result + (hasBaggage ? 1 : 0);
        result = 31 * result + (hasPriorityRegistration ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + getId() +
                ", user=" + user +
                ", flight=" + flight +
                ", price=" + price +
                ", purchaseDateTime=" + purchaseDateTime +
                ", hasBaggage=" + hasBaggage +
                ", hasPriorityRegistration=" + hasPriorityRegistration +
                '}';
    }
}
