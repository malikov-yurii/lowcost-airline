package com.malikov.lowcostairline.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */
public class Flight extends BaseEntity{

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Airport departureAirport;

    private Airport arrivalAirport;

    private BigDecimal startBaseTicketPrice;

    private BigDecimal maxBaseTicketPrice;

    private int totalSeatQuantity;

    public Flight(long id, LocalDateTime departureTime, LocalDateTime arrivalTime, Airport departureAirport, Airport arrivalAirport, BigDecimal startBaseTicketPrice, BigDecimal maxBaseTicketPrice, int totalSeatQuantity) {
        super(id);
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.startBaseTicketPrice = startBaseTicketPrice;
        this.maxBaseTicketPrice = maxBaseTicketPrice;
        this.totalSeatQuantity = totalSeatQuantity;
    }

    public Flight(LocalDateTime departureTime, LocalDateTime arrivalTime, Airport departureAirport, Airport arrivalAirport, BigDecimal startBaseTicketPrice, BigDecimal maxBaseTicketPrice, int totalSeatQuantity) {
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.startBaseTicketPrice = startBaseTicketPrice;
        this.maxBaseTicketPrice = maxBaseTicketPrice;
        this.totalSeatQuantity = totalSeatQuantity;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public BigDecimal getStartBaseTicketPrice() {
        return startBaseTicketPrice;
    }

    public void setStartBaseTicketPrice(BigDecimal startBaseTicketPrice) {
        this.startBaseTicketPrice = startBaseTicketPrice;
    }

    public BigDecimal getMaxBaseTicketPrice() {
        return maxBaseTicketPrice;
    }

    public void setMaxBaseTicketPrice(BigDecimal maxBaseTicketPrice) {
        this.maxBaseTicketPrice = maxBaseTicketPrice;
    }

    public int getTotalSeatQuantity() {
        return totalSeatQuantity;
    }

    public void setTotalSeatQuantity(int totalSeatQuantity) {
        this.totalSeatQuantity = totalSeatQuantity;
    }

}
