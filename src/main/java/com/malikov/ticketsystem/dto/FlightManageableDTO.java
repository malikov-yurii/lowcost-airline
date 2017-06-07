package com.malikov.ticketsystem.dto;

import com.malikov.ticketsystem.util.DateTimeUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */
public class FlightManageableDTO extends BaseDTO {

    @SafeHtml
    @NotBlank
    @Size(min = 2)
    private String departureAirport;

    @SafeHtml
    @NotBlank
    @Size(min = 2)
    private String arrivalAirport;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureLocalDateTime;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalLocalDateTime;

    @SafeHtml
    @NotBlank
    @Size(min = 2)
    private String aircraftName;

    @NotNull
    private BigDecimal initialBaseTicketPrice;

    @NotNull
    private BigDecimal maxBaseTicketPrice;

    private Boolean canceled;


    public FlightManageableDTO(
            Long id
            , String departureAirport
            , String arrivalAirport
            , LocalDateTime departureLocalDateTime
            , LocalDateTime arrivalLocalDateTime
            , String aircraftName
            , BigDecimal initialBaseTicketPrice
            , BigDecimal maxBaseTicketPrice
            , Boolean canceled) {
        super(id);
        this.departureAirport = departureAirport != null ? departureAirport : "";
        this.arrivalAirport = arrivalAirport != null ? arrivalAirport : "";
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.aircraftName = aircraftName != null ? aircraftName : "";
        this.initialBaseTicketPrice = initialBaseTicketPrice != null ? initialBaseTicketPrice : new BigDecimal(0);
        this.maxBaseTicketPrice = maxBaseTicketPrice != null ? maxBaseTicketPrice : new BigDecimal(0);
        this.canceled = canceled;
    }

    public FlightManageableDTO() {
    }


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

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightManageableDTO that = (FlightManageableDTO) o;

        if (departureAirport != null
                ? !departureAirport.equals(that.departureAirport)
                : that.departureAirport != null)
            return false;
        if (arrivalAirport != null
                ? !arrivalAirport.equals(that.arrivalAirport)
                : that.arrivalAirport != null)
            return false;
        if (departureLocalDateTime != null
                ? !departureLocalDateTime.equals(that.departureLocalDateTime)
                : that.departureLocalDateTime != null)
            return false;
        if (arrivalLocalDateTime != null
                ? !arrivalLocalDateTime.equals(that.arrivalLocalDateTime)
                : that.arrivalLocalDateTime != null)
            return false;
        if (aircraftName != null
                ? !aircraftName.equals(that.aircraftName)
                : that.aircraftName != null) return false;
        if (initialBaseTicketPrice != null
                ? !initialBaseTicketPrice.equals(that.initialBaseTicketPrice)
                : that.initialBaseTicketPrice != null)
            return false;
        if (maxBaseTicketPrice != null
                ? !maxBaseTicketPrice.equals(that.maxBaseTicketPrice)
                : that.maxBaseTicketPrice != null)
            return false;
        return canceled != null ? canceled.equals(that.canceled) : that.canceled == null;
    }

    @Override
    public int hashCode() {
        int result = departureAirport != null ? departureAirport.hashCode() : 0;
        result = 31 * result + (arrivalAirport != null ? arrivalAirport.hashCode() : 0);
        result = 31 * result + (departureLocalDateTime != null ? departureLocalDateTime.hashCode() : 0);
        result = 31 * result + (arrivalLocalDateTime != null ? arrivalLocalDateTime.hashCode() : 0);
        result = 31 * result + (aircraftName != null ? aircraftName.hashCode() : 0);
        result = 31 * result + (initialBaseTicketPrice != null ? initialBaseTicketPrice.hashCode() : 0);
        result = 31 * result + (maxBaseTicketPrice != null ? maxBaseTicketPrice.hashCode() : 0);
        result = 31 * result + (canceled != null ? canceled.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlightManageableDTO{" +
                "departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", departureLocalDateTime=" + departureLocalDateTime +
                ", arrivalLocalDateTime=" + arrivalLocalDateTime +
                ", aircraftName='" + aircraftName + '\'' +
                ", initialBaseTicketPrice=" + initialBaseTicketPrice +
                ", maxBaseTicketPrice=" + maxBaseTicketPrice +
                ", canceled=" + canceled +
                '}';
    }
}
