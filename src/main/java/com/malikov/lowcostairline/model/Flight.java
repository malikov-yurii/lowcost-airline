package com.malikov.lowcostairline.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */
public class Flight extends BaseEntity{

    private LocalDateTime departureLocalDateTime;

    private LocalDateTime arrivalLocalDateTime;

    private Airport departureAirport;

    private Airport arrivalAirport;

    private Aircraft aircraft;

    private BigDecimal startTicketBasePrice;

    private BigDecimal maxTicketBasePrice;

    public Flight(){}

    public Flight(Long id, LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime, Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, BigDecimal startTicketBasePrice, BigDecimal maxTicketBasePrice) {
        super(id);
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.startTicketBasePrice = startTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
    }

    public Flight(LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime, Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, BigDecimal startTicketBasePrice, BigDecimal maxTicketBasePrice) {
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.aircraft = aircraft;
        this.startTicketBasePrice = startTicketBasePrice;
        this.maxTicketBasePrice = maxTicketBasePrice;
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
