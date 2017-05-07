package com.malikov.lowcostairline.model;

import com.malikov.lowcostairline.util.converters.OffsetDateTimeConverter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author Yurii Malikov
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Ticket.DELETE, query = "DELETE FROM Ticket t WHERE t.id=:id"),
        @NamedQuery(name = Ticket.ALL_SORTED, query = "SELECT t FROM Ticket t ORDER BY t.id ASC")
})
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    public static final String DELETE = "Ticket.delete";
    public static final String ALL_SORTED = "Ticket.allSorted";

    @OneToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "purchase_offsetdatetime")
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime purchaseOffsetDateTime;

    @Column(name = "has_baggage")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean hasBaggage;

    @Column(name = "has_priority_registration")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean hasPriorityRegistration;

    public Ticket(){}

    public Ticket(Flight flight, User user, BigDecimal price, OffsetDateTime purchaseOffsetDateTime, Boolean hasBaggage, Boolean hasPriorityRegistration) {
        this.flight = flight;
        this.user = user;
        this.price = price;
        this.purchaseOffsetDateTime = purchaseOffsetDateTime;
        this.hasBaggage = hasBaggage;
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    public Ticket(Long id, Flight flight, User user, BigDecimal price, OffsetDateTime purchaseOffsetDateTime, Boolean hasBaggage, Boolean hasPriorityRegistration) {
        super(id);
        this.flight = flight;
        this.user = user;
        this.price = price;
        this.purchaseOffsetDateTime = purchaseOffsetDateTime;
        this.hasBaggage = hasBaggage;
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    public Ticket(Ticket ticket) {
        super(ticket.getId());
        flight = ticket.getFlight();
        user = ticket.getUser();
        price = ticket.getPrice();
        purchaseOffsetDateTime = ticket.getPurchaseOffsetDateTime();
        hasBaggage = ticket.hasBaggage;
        hasPriorityRegistration = ticket.hasPriorityRegistration;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OffsetDateTime getPurchaseOffsetDateTime() {
        return purchaseOffsetDateTime;
    }

    public void setPurchaseOffsetDateTime(OffsetDateTime purchaseOffsetDateTime) {
        this.purchaseOffsetDateTime = purchaseOffsetDateTime;
    }

    public Boolean isHasBaggage() {
        return hasBaggage;
    }

    public void setHasBaggage(Boolean hasBaggage) {
        this.hasBaggage = hasBaggage;
    }

    public Boolean isHasPriorityRegistration() {
        return hasPriorityRegistration;
    }

    public void setHasPriorityRegistration(Boolean hasPriorityRegistration) {
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Ticket ticket = (Ticket) o;

        if (flight != null ? !flight.equals(ticket.flight) : ticket.flight != null) return false;
        if (user != null ? !user.equals(ticket.user) : ticket.user != null) return false;
        if (price != null ? !price.equals(ticket.price) : ticket.price != null) return false;
        if (purchaseOffsetDateTime != null ? !purchaseOffsetDateTime.equals(ticket.purchaseOffsetDateTime) : ticket.purchaseOffsetDateTime != null)
            return false;
        if (hasBaggage != null ? !hasBaggage.equals(ticket.hasBaggage) : ticket.hasBaggage != null) return false;
        return hasPriorityRegistration != null ? hasPriorityRegistration.equals(ticket.hasPriorityRegistration) : ticket.hasPriorityRegistration == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseOffsetDateTime != null ? purchaseOffsetDateTime.hashCode() : 0);
        result = 31 * result + (hasBaggage ? 1 : 0);
        result = 31 * result + (hasPriorityRegistration ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + getId() +
                ", flight=" + flight +
                ", user=" + user +
                ", price=" + price +
                ", purchaseOffsetDateTime=" + purchaseOffsetDateTime +
                ", hasBaggage=" + hasBaggage +
                ", hasPriorityRegistration=" + hasPriorityRegistration +
                '}';
    }
}
