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
@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @OneToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    @OneToOne(fetch = FetchType.EAGER)
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


    public Flight() {}

    public Flight(Long id, Airport departureAirport, Airport arrivalAirport, Aircraft aircraft,
                  LocalDateTime departureUtcDateTime, LocalDateTime arrivalUtcDateTime,
                  BigDecimal initialTicketBasePrice, BigDecimal maxTicketBasePrice) {
        super(id);
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.departureUtcDateTime = departureUtcDateTime;
        this.arrivalUtcDateTime = arrivalUtcDateTime;
        this.initialTicketBasePrice = initialTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
        canceled = false;
    }

    public Flight(Airport departureAirport, Airport arrivalAirport, Aircraft aircraft,
                  LocalDateTime departureUtcDateTime, LocalDateTime arrivalUtcDateTime,
                  BigDecimal initialTicketBasePrice, BigDecimal maxTicketBasePrice) {
        this(null, departureAirport, arrivalAirport, aircraft, departureUtcDateTime, arrivalUtcDateTime,
                initialTicketBasePrice, maxTicketBasePrice);
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

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Flight flight = (Flight) o;

        if (departureUtcDateTime != null
                ? !departureUtcDateTime.equals(flight.departureUtcDateTime)
                : flight.departureUtcDateTime != null) {
            return false;
        }
        if (arrivalUtcDateTime != null
                ? !arrivalUtcDateTime.equals(flight.arrivalUtcDateTime)
                : flight.arrivalUtcDateTime != null) {
            return false;
        }
        if (departureAirport != null
                ? !departureAirport.equals(flight.departureAirport)
                : flight.departureAirport != null) {
            return false;
        }
        if (arrivalAirport != null
                ? !arrivalAirport.equals(flight.arrivalAirport)
                : flight.arrivalAirport != null) {
            return false;
        }
        if (aircraft != null
                ? !aircraft.equals(flight.aircraft)
                : flight.aircraft != null) {
            return false;
        }
        if (initialTicketBasePrice != null
                ? !initialTicketBasePrice.equals(flight.initialTicketBasePrice)
                : flight.initialTicketBasePrice != null) {
            return false;
        }
        return maxTicketBasePrice != null
                ? maxTicketBasePrice.equals(flight.maxTicketBasePrice)
                : flight.maxTicketBasePrice == null;
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
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                ", arrivalAirport=" + arrivalAirport +
                ", aircraft=" + aircraft +
                ", departureUtcDateTime=" + departureUtcDateTime +
                ", arrivalUtcDateTime=" + arrivalUtcDateTime +
                ", initialTicketBasePrice=" + initialTicketBasePrice +
                ", maxTicketBasePrice=" + maxTicketBasePrice +
                ", canceled=" + canceled +
                '}';
    }
}
