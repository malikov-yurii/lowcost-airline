package com.malikov.ticketsystem.model;

import com.malikov.ticketsystem.util.converters.OffsetDateTimeConverter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Ticket.DELETE, query = "DELETE FROM Ticket t WHERE t.id=:id AND t.user.id=:userId"),
        @NamedQuery(name = Ticket.ALL_SORTED, query = "SELECT t FROM Ticket t WHERE t.user.id=:userId ORDER BY t.id ASC")
})
@Entity
@NamedEntityGraphs({
        @NamedEntityGraph(name = Ticket.WITH_USER, attributeNodes = {@NamedAttributeNode("user")}),
        @NamedEntityGraph(name = Ticket.WITH_FLIGHT, attributeNodes = {@NamedAttributeNode("flight")}),
        @NamedEntityGraph(name = Ticket.WITH_USER_AND_FLIGHT, attributeNodes = {@NamedAttributeNode("user"),
                @NamedAttributeNode("flight")})
})
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    public static final String WITH_USER = "Ticket.graphWithUser";
    public static final String WITH_FLIGHT = "Ticket.graphWithFlight";
    public static final String WITH_USER_AND_FLIGHT = "Ticket.graphWithUserAndFlight";

    public static final String DELETE = "Ticket.delete";
    public static final String ALL_SORTED = "Ticket.allSorted";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
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

    // TODO: 5/8/2017 Maybe I need to store ticket information in a single column and parse if I need to??? But in a such case I cannot easily add column with additional ticket information
    @Column(name = "passanger_full_name")
    private String passangerFullName;

    @Column(name = "departure_airport_full_name")
    private String departureAirportFullName;

    @Column(name = "arrival_airport_full_name")
    private String arrivalAirportFullName;

    @Column(name = "departure_offsetdatetime")
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime departureOffsetDateTime;

    @Column(name = "arrival_offsetdatetime")
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime arrivalOffsetDateTime;

    @Column(name = "seat_number")
    private Integer seatNumber;

    public Ticket() {
    }

    public Ticket(Flight flight, User user, BigDecimal price, OffsetDateTime purchaseOffsetDateTime, Boolean hasBaggage, Boolean hasPriorityRegistration) {
        this.flight = flight;
        this.user = user;
        this.price = price;
        this.purchaseOffsetDateTime = purchaseOffsetDateTime;
        this.hasBaggage = hasBaggage;
        this.hasPriorityRegistration = hasPriorityRegistration;
    }

    public Ticket(Long id, Flight flight, User user, BigDecimal price, OffsetDateTime purchaseOffsetDateTime, Boolean hasBaggage, Boolean hasPriorityRegistration, Integer seatNumber) {
        super(id);
        this.flight = flight;
        this.user = user;
        this.price = price;
        this.purchaseOffsetDateTime = purchaseOffsetDateTime;
        this.hasBaggage = hasBaggage;
        this.hasPriorityRegistration = hasPriorityRegistration;
        passangerFullName = user.getName() + " " + user.getLastName();
        departureAirportFullName = flight.getDepartureAirport().getName() + " (" + flight.getDepartureAirport().getCity().getName() + ")";
        arrivalAirportFullName = flight.getArrivalAirport().getName() + " (" + flight.getArrivalAirport().getCity().getName() + ")";
        departureOffsetDateTime = OffsetDateTime.of(flight.getDepartureLocalDateTime(),
                flight.getDepartureAirport().getCity().getZoneId().getRules().getOffset(flight.getDepartureLocalDateTime()));
        arrivalOffsetDateTime = OffsetDateTime.of(flight.getArrivalLocalDateTime(),
                flight.getArrivalAirport().getCity().getZoneId().getRules().getOffset(flight.getArrivalLocalDateTime()));
        this.seatNumber = seatNumber;
    }

    public Ticket(Ticket ticket) {
        super(ticket.getId());
        flight = ticket.getFlight();
        user = ticket.getUser();
        price = ticket.getPrice();
        purchaseOffsetDateTime = ticket.getPurchaseOffsetDateTime();
        hasBaggage = ticket.hasBaggage;
        hasPriorityRegistration = ticket.hasPriorityRegistration;
        passangerFullName = ticket.getPassangerFullName();
        departureAirportFullName = ticket.getDepartureAirportFullName();
        arrivalAirportFullName = ticket.getArrivalAirportFullName();
        departureOffsetDateTime = ticket.getDepartureOffsetDateTime();
        arrivalOffsetDateTime = ticket.getArrivalOffsetDateTime();
        seatNumber = ticket.getSeatNumber();
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

    public Boolean getHasBaggage() {
        return hasBaggage;
    }

    public Boolean getHasPriorityRegistration() {
        return hasPriorityRegistration;
    }

    public String getPassangerFullName() {
        return passangerFullName;
    }

    public void setPassangerFullName(String passangerFullName) {
        this.passangerFullName = passangerFullName;
    }

    public String getDepartureAirportFullName() {
        return departureAirportFullName;
    }

    public void setDepartureAirportFullName(String departureAirportFullName) {
        this.departureAirportFullName = departureAirportFullName;
    }

    public String getArrivalAirportFullName() {
        return arrivalAirportFullName;
    }

    public void setArrivalAirportFullName(String arrivalAirportFullName) {
        this.arrivalAirportFullName = arrivalAirportFullName;
    }

    public OffsetDateTime getDepartureOffsetDateTime() {
        return departureOffsetDateTime;
    }

    public void setDepartureOffsetDateTime(OffsetDateTime departureOffsetDateTime) {
        this.departureOffsetDateTime = departureOffsetDateTime;
    }

    public OffsetDateTime getArrivalOffsetDateTime() {
        return arrivalOffsetDateTime;
    }

    public void setArrivalOffsetDateTime(OffsetDateTime arrivalOffsetDateTime) {
        this.arrivalOffsetDateTime = arrivalOffsetDateTime;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Ticket ticket = (Ticket) o;

        if (price != null ? !price.equals(ticket.price) : ticket.price != null) return false;
        if (purchaseOffsetDateTime != null ? !purchaseOffsetDateTime.equals(ticket.purchaseOffsetDateTime) : ticket.purchaseOffsetDateTime != null)
            return false;
        if (hasBaggage != null ? !hasBaggage.equals(ticket.hasBaggage) : ticket.hasBaggage != null) return false;
        return hasPriorityRegistration != null ? hasPriorityRegistration.equals(ticket.hasPriorityRegistration) : ticket.hasPriorityRegistration == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
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
                ", price=" + price +
                ", purchaseOffsetDateTime=" + purchaseOffsetDateTime +
                ", hasBaggage=" + hasBaggage +
                ", hasPriorityRegistration=" + hasPriorityRegistration +
                ", passangerFullName=" + passangerFullName +
                ", departureAirportFullName=" + departureAirportFullName +
                ", arrivalAirportFullName=" + arrivalAirportFullName +
                ", departureOffsetDateTime=" + departureOffsetDateTime +
                ", arrivalOffsetDateTime=" + arrivalOffsetDateTime +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
