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

    public Flight(long id, LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime, Airport departureAirport, Airport arrivalAirport, Aircraft aircraft, BigDecimal startTicketBasePrice, BigDecimal maxTicketBasePrice) {
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

}
