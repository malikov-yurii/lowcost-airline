package com.malikov.ticketsystem.model;

import com.malikov.ticketsystem.util.DateTimeUtil;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Flight.DELETE, query = "DELETE FROM Flight f WHERE f.id = :id"),
        @NamedQuery(name = Flight.ALL_SORTED, query = "SELECT f FROM Flight f ORDER BY f.id ASC")
        //,@NamedQuery(name = Flight.ALL_FILTERED_SORTED_BY_ID, query =
        //        "SELECT f FROM Flight f" +
        //        " WHERE ((:departureAirport is null or f.departureAirport = :departureAirport)" +
        //        " AND (:arrivalAirport is null or f.arrivalAirport=:arrivalAirport)" +
        //        " AND (:fromDepartureUtcDateTime is null or f.departureUtcDateTime >= :fromDepartureUtcDateTime)" +
        //        " AND (:toDepartureUtcDateTime is null or f.departureUtcDateTime <= :toDepartureUtcDateTime))" +
        //        " ORDER BY f.id ASC")
})
@Entity
@NamedEntityGraph(name = Flight.WITH_TICKETS, attributeNodes = {@NamedAttributeNode("tickets")})
@Table(name = "flights")
public class Flight extends BaseEntity {

    public static final String DELETE = "Flight.delete";
    public static final String ALL_SORTED = "Flight.allSorted";
    //public static final String ALL_FILTERED_SORTED_BY_ID = "Flight.allBetween";

    public static final String WITH_TICKETS = "Flight.withTickets";


    @OneToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @OneToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    @OneToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @Column(name = "departure_utc_datetime")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureUtcDateTime;

    @Column(name = "arrival_utc_datetime")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalUtcDateTime;

    @Column(name = "initial_ticket_base_price")
    private BigDecimal initialTicketBasePrice;

    @Column(name = "max_ticket_base_price")
    private BigDecimal maxTicketBasePrice;

    @Column(name = "canceled")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean canceled;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "flight")
    private List<Ticket> tickets;

    public Flight() {
    }

    public Flight(Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, LocalDateTime departureUtcDateTime, LocalDateTime arrivalUtcDateTime, BigDecimal initialTicketBasePrice, BigDecimal maxTicketBasePrice) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.departureUtcDateTime = departureUtcDateTime;
        this.arrivalUtcDateTime = arrivalUtcDateTime;
        this.initialTicketBasePrice = initialTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
        canceled = false; // TODO: 5/23/2017 Do I need to do it explicitly??
    }

    public Flight(Long id, Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, LocalDateTime departureUtcDateTime, LocalDateTime arrivalUtcDateTime, BigDecimal initialTicketBasePrice, BigDecimal maxTicketBasePrice) {
        super(id);
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.departureUtcDateTime = departureUtcDateTime;
        this.arrivalUtcDateTime = arrivalUtcDateTime;
        this.initialTicketBasePrice = initialTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
        canceled = false; // TODO: 5/23/2017 Do I need to do it explicitly??
    }

    // TODO: 5/15/2017 How to add tickets??? refactor to do it by copying??
    public Flight(Long id, Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, LocalDateTime departureUtcDateTime, LocalDateTime arrivalUtcDateTime, BigDecimal initialTicketBasePrice, BigDecimal maxTicketBasePrice, List<Ticket> tickets) {
        super(id);
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.departureUtcDateTime = departureUtcDateTime;
        this.arrivalUtcDateTime = arrivalUtcDateTime;
        this.initialTicketBasePrice = initialTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
        canceled = false; // TODO: 5/23/2017 Do I need to do it explicitly??

        this.tickets = tickets;
    }

    public Flight(Flight flight) {
        super(flight.getId());
        departureAirport = flight.getDepartureAirport();
        arrivalAirport = flight.getArrivalAirport();
        aircraft = flight.getAircraft();
        departureUtcDateTime = flight.getDepartureUtcDateTime();
        arrivalUtcDateTime = flight.getUtcLocalDateTime();
        initialTicketBasePrice = flight.getInitialTicketBasePrice();
        maxTicketBasePrice = flight.getMaxTicketBasePrice();
        canceled = false; // TODO: 5/23/2017 Do I need to do it explicitly??
    }

    public LocalDateTime getDepartureUtcDateTime() {
        return departureUtcDateTime;
    }

    public void setDepartureUtcDateTime(LocalDateTime departureUtcDateTime) {
        this.departureUtcDateTime = departureUtcDateTime;
    }

    public LocalDateTime getArrivalUtcDateTime() {
        return arrivalUtcDateTime;
    }

    public void setArrivalUtcDateTime(LocalDateTime arrivalUtcDateTime) {
        this.arrivalUtcDateTime = arrivalUtcDateTime;
    }

    public LocalDateTime getUtcLocalDateTime() {
        return arrivalUtcDateTime;
    }

    public void setArrivalLocalDateTime(LocalDateTime arrivalUtcDateTime) {
        this.arrivalUtcDateTime = arrivalUtcDateTime;
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public BigDecimal getInitialTicketBasePrice() {
        return initialTicketBasePrice;
    }

    public void setInitialTicketBasePrice(BigDecimal initialTicketBasePrice) {
        this.initialTicketBasePrice = initialTicketBasePrice;
    }

    public BigDecimal getMaxTicketBasePrice() {
        return maxTicketBasePrice;
    }

    public void setMaxTicketBasePrice(BigDecimal maxTicketBasePrice) {
        this.maxTicketBasePrice = maxTicketBasePrice;
    }

    public Boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    // TODO: 5/15/2017 How to add tickets??? refactor to do it by copying??
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Flight flight = (Flight) o;

        if (departureUtcDateTime != null ? !departureUtcDateTime.equals(flight.departureUtcDateTime) : flight.departureUtcDateTime != null)
            return false;
        if (arrivalUtcDateTime != null ? !arrivalUtcDateTime.equals(flight.arrivalUtcDateTime) : flight.arrivalUtcDateTime != null)
            return false;
        if (departureAirport != null ? !departureAirport.equals(flight.departureAirport) : flight.departureAirport != null)
            return false;
        if (arrivalAirport != null ? !arrivalAirport.equals(flight.arrivalAirport) : flight.arrivalAirport != null)
            return false;
        if (aircraft != null ? !aircraft.equals(flight.aircraft) : flight.aircraft != null) return false;
        if (initialTicketBasePrice != null ? !initialTicketBasePrice.equals(flight.initialTicketBasePrice) : flight.initialTicketBasePrice != null)
            return false;
        return maxTicketBasePrice != null ? maxTicketBasePrice.equals(flight.maxTicketBasePrice) : flight.maxTicketBasePrice == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (departureUtcDateTime != null ? departureUtcDateTime.hashCode() : 0);
        result = 31 * result + (arrivalUtcDateTime != null ? arrivalUtcDateTime.hashCode() : 0);
        result = 31 * result + (departureAirport != null ? departureAirport.hashCode() : 0);
        result = 31 * result + (arrivalAirport != null ? arrivalAirport.hashCode() : 0);
        result = 31 * result + (aircraft != null ? aircraft.hashCode() : 0);
        result = 31 * result + (initialTicketBasePrice != null ? initialTicketBasePrice.hashCode() : 0);
        result = 31 * result + (maxTicketBasePrice != null ? maxTicketBasePrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + getId() +
                ", departureUtcDateTime=" + departureUtcDateTime +
                ", arrivalUtcDateTime=" + arrivalUtcDateTime +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                ", aircraft=" + aircraft +
                ", initialTicketBasePrice=" + initialTicketBasePrice +
                ", maxTicketBasePrice=" + maxTicketBasePrice +
                ", canceled=" + canceled +
                '}';
    }
}
