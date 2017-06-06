package com.malikov.ticketsystem.dto;

import com.malikov.ticketsystem.util.DateTimeUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */
public class FlightDTO extends BaseDTO {

    @SafeHtml
    @NotBlank
    @Size(min = 2)
    private String departureAirport;

    @SafeHtml
    @NotBlank
    @Size(min = 2)
    private String arrivalAirport;

    // TODO: 5/20/2017 How can I replace it with spring formatter??????
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureLocalDateTime;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalLocalDateTime;

    private BigDecimal ticketPrice;

    public FlightDTO(
            Long id
            , String departureAirport
            , String arrivalAirport
            , LocalDateTime departureLocalDateTime
            , LocalDateTime arrivalLocalDateTime
            , BigDecimal ticketPrice
    ) {
        super(id);
        this.departureAirport = departureAirport != null ? departureAirport : "";
        this.arrivalAirport = arrivalAirport != null ? arrivalAirport : "";
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.ticketPrice = ticketPrice != null ? ticketPrice : new BigDecimal(0);
    }

    public FlightDTO() {
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

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

}
