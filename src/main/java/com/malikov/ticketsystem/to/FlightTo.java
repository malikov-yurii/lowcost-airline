package com.malikov.ticketsystem.to;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.malikov.ticketsystem.util.serializers.LocalDateTimeDeserializer;
import com.malikov.ticketsystem.util.serializers.LocalDateTimeSerializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */
public class FlightTo extends BaseTo {

    private String departureAirport;

    private String arrivalAirport;

    // TODO: 5/20/2017 How can I replace it with spring formatter or make serializer/deserializer work globaly without declaring them explicitly??????
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime departureLocalDateTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime arrivalLocalDateTime;

    private String aircraftName;

    private BigDecimal initialBaseTicketPrice;

    private BigDecimal maxBaseTicketPrice;

    public FlightTo(
            Long id
            , String departureAirport
            , String arrivalAirport
            , LocalDateTime departureLocalDateTime
            , LocalDateTime arrivalLocalDateTime
            , String aircraftName
            , BigDecimal initialBaseTicketPrice
            , BigDecimal maxBaseTicketPrice
    ) {
        super(id);
        this.departureAirport = departureAirport != null ? departureAirport : "";
        this.arrivalAirport = arrivalAirport != null ? arrivalAirport : "";
        //this.departureLocalDateTime = departureLocalDateTime != null ? departureLocalDateTime : "";
        this.departureLocalDateTime = departureLocalDateTime;
        //this.arrivalLocalDateTime = arrivalLocalDateTime != null ? arrivalLocalDateTime : "";
        this.arrivalLocalDateTime = arrivalLocalDateTime;
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
