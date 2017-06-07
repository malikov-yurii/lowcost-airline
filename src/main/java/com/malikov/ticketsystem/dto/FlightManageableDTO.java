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
            , Boolean canceled
    ) {
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
}
