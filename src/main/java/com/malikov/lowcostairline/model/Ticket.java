package com.malikov.lowcostairline.model;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class Ticket extends BaseEntity {

    private User user;

    private Flight flight;

    private BigDecimal price;

    private boolean hasBaggage;

    private boolean hasPriorityRegistration;

    public Ticket(long id, User user, Flight flight, BigDecimal price, boolean hasBaggage, boolean hasPriorityRegistration) {
        super(id);
        this.user = user;
        this.flight = flight;
        this.price = price;
        this.hasBaggage = hasBaggage;
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    public Ticket(User user, Flight flight, BigDecimal price, boolean hasBaggage, boolean hasPriorityRegistration) {
        this.user = user;
        this.flight = flight;
        this.price = price;
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
}
