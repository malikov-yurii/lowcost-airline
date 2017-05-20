package com.malikov.ticketsystem.to;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class FlightTo extends BaseTo {

    private String departureAirport;

    private String arrivalAirport;

    private String departureLocalDateTime;

    private String arrivalLocalDateTime;

    private String aircraftName;

    private BigDecimal initialBaseTicketPrice;

    private BigDecimal maxBaseTicketPrice;

    public FlightTo(
            Long id
            , String departureAirport
            , String arrivalAirport
            , String departureLocalDateTime
            , String arrivalLocalDateTime
            , String aircraftName
            , BigDecimal initialBaseTicketPrice
            , BigDecimal maxBaseTicketPrice
    ) {
        super(id);
        this.departureAirport = departureAirport != null ? departureAirport : "";
        this.arrivalAirport = arrivalAirport != null ? arrivalAirport : "";
        this.departureLocalDateTime = departureLocalDateTime != null ? departureLocalDateTime : "";
        this.arrivalLocalDateTime = arrivalLocalDateTime != null ? arrivalLocalDateTime : "";
        this.aircraftName = aircraftName != null ? aircraftName : "";
        this.initialBaseTicketPrice = initialBaseTicketPrice != null ? initialBaseTicketPrice : new BigDecimal(0);
        this.maxBaseTicketPrice = maxBaseTicketPrice != null ? maxBaseTicketPrice : new BigDecimal(0);
    }

    public FlightTo(){}

    public String getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(String departureAirport) {
        this.departureAirport = departureAirport;
    }

    public String getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(String arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getDepartureLocalDateTime() {
        return departureLocalDateTime;
    }

    public void setDepartureLocalDateTime(String departureLocalDateTime) {
        this.departureLocalDateTime = departureLocalDateTime;
    }

    public String getArrivalLocalDateTime() {
        return arrivalLocalDateTime;
    }

    public void setArrivalLocalDateTime(String arrivalLocalDateTime) {
        this.arrivalLocalDateTime = arrivalLocalDateTime;
    }

    public String getAircraftName() {
        return aircraftName;
    }

    public void setAircraftName(String aircraftName) {
        this.aircraftName = aircraftName;
    }

    public BigDecimal getInitialBaseTicketPrice() {
        return initialBaseTicketPrice;
    }

    public void setInitialBaseTicketPrice(BigDecimal initialBaseTicketPrice) {
        this.initialBaseTicketPrice = initialBaseTicketPrice;
    }

    public BigDecimal getMaxBaseTicketPrice() {
        return maxBaseTicketPrice;
    }

    public void setMaxBaseTicketPrice(BigDecimal maxBaseTicketPrice) {
        this.maxBaseTicketPrice = maxBaseTicketPrice;
    }
}
