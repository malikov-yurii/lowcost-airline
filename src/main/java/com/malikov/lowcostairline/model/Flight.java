package com.malikov.lowcostairline.model;

import com.malikov.lowcostairline.util.DateTimeUtil;
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
        @NamedQuery(name = Flight.DELETE, query = "DELETE FROM Flight f WHERE f.id=:id"),
        @NamedQuery(name = Flight.ALL_SORTED, query = "SELECT f FROM Flight f ORDER BY f.id ASC")
})
@Entity
@Table(name = "flights")
public class Flight extends BaseEntity {

    public static final String DELETE = "Flight.delete";

    public static final String ALL_SORTED = "Flight.allSorted";

    @OneToOne
    @JoinColumn(name = "departure_airport_id")
    private Airport departureAirport;

    @OneToOne
    @JoinColumn(name = "arrival_airport_id")
    private Airport arrivalAirport;

    @OneToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;

    @Column(name = "departure_localdatetime")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureLocalDateTime;

    @Column(name = "arrival_localdatetime")
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalLocalDateTime;

    @Column(name = "start_ticket_base_price")
    private BigDecimal startTicketBasePrice;

    @Column(name = "max_ticket_base_price")
    private BigDecimal maxTicketBasePrice;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "flight")
    private List<Ticket> tickets;

    public Flight() {
    }

    public Flight(Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime, BigDecimal startTicketBasePrice, BigDecimal maxTicketBasePrice) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.startTicketBasePrice = startTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
    }

    public Flight(Long id, Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime, BigDecimal startTicketBasePrice, BigDecimal maxTicketBasePrice) {
        super(id);
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.startTicketBasePrice = startTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
    }

    public Flight(Flight flight) {
        super(flight.getId());
        departureAirport = flight.getDepartureAirport();
        arrivalAirport = flight.getArrivalAirport();
        aircraft = flight.getAircraft();
        departureLocalDateTime = flight.getDepartureLocalDateTime();
        arrivalLocalDateTime = flight.getArrivalLocalDateTime();
        startTicketBasePrice = flight.getStartTicketBasePrice();
        maxTicketBasePrice = flight.getMaxTicketBasePrice();
    }

    public LocalDateTime getDepartureLocalDateTime() {
        return departureLocalDateTime;
    }

    public void setDepartureLocalDateTime(LocalDateTime departureLocalDateTime) {
        this.departureLocalDateTime = departureLocalDateTime;
    }

    public LocalDateTime getArrivalLocalDateTime() {
        return arrivalLocalDateTime;
    }

    public void setArrivalLocalDateTime(LocalDateTime arrivalLocalDateTime) {
        this.arrivalLocalDateTime = arrivalLocalDateTime;
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

    public BigDecimal getStartTicketBasePrice() {
        return startTicketBasePrice;
    }

    public void setStartTicketBasePrice(BigDecimal startTicketBasePrice) {
        this.startTicketBasePrice = startTicketBasePrice;
    }

    public BigDecimal getMaxTicketBasePrice() {
        return maxTicketBasePrice;
    }

    public void setMaxTicketBasePrice(BigDecimal maxTicketBasePrice) {
        this.maxTicketBasePrice = maxTicketBasePrice;
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

        if (departureLocalDateTime != null ? !departureLocalDateTime.equals(flight.departureLocalDateTime) : flight.departureLocalDateTime != null)
            return false;
        if (arrivalLocalDateTime != null ? !arrivalLocalDateTime.equals(flight.arrivalLocalDateTime) : flight.arrivalLocalDateTime != null)
            return false;
        if (departureAirport != null ? !departureAirport.equals(flight.departureAirport) : flight.departureAirport != null)
            return false;
        if (arrivalAirport != null ? !arrivalAirport.equals(flight.arrivalAirport) : flight.arrivalAirport != null)
            return false;
        if (aircraft != null ? !aircraft.equals(flight.aircraft) : flight.aircraft != null) return false;
        if (startTicketBasePrice != null ? !startTicketBasePrice.equals(flight.startTicketBasePrice) : flight.startTicketBasePrice != null)
            return false;
        return maxTicketBasePrice != null ? maxTicketBasePrice.equals(flight.maxTicketBasePrice) : flight.maxTicketBasePrice == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (departureLocalDateTime != null ? departureLocalDateTime.hashCode() : 0);
        result = 31 * result + (arrivalLocalDateTime != null ? arrivalLocalDateTime.hashCode() : 0);
        result = 31 * result + (departureAirport != null ? departureAirport.hashCode() : 0);
        result = 31 * result + (arrivalAirport != null ? arrivalAirport.hashCode() : 0);
        result = 31 * result + (aircraft != null ? aircraft.hashCode() : 0);
        result = 31 * result + (startTicketBasePrice != null ? startTicketBasePrice.hashCode() : 0);
        result = 31 * result + (maxTicketBasePrice != null ? maxTicketBasePrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + getId() +
                ", departureLocalDateTime=" + departureLocalDateTime +
                ", arrivalLocalDateTime=" + arrivalLocalDateTime +
                ", departureAirport=" + departureAirport +
                ", arrivalAirport=" + arrivalAirport +
                ", aircraft=" + aircraft +
                ", startTicketBasePrice=" + startTicketBasePrice +
                ", maxTicketBasePrice=" + maxTicketBasePrice +
                '}';
    }
}
