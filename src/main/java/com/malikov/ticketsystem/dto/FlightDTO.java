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

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureLocalDateTime;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalLocalDateTime;

    private BigDecimal ticketPrice;


    public FlightDTO() {}

    public FlightDTO(Long id, String departureAirport, String arrivalAirport, LocalDateTime departureLocalDateTime,
                     LocalDateTime arrivalLocalDateTime, BigDecimal ticketPrice) {
        super(id);
        this.departureAirport = departureAirport != null ? departureAirport : "";
        this.arrivalAirport = arrivalAirport != null ? arrivalAirport : "";
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.ticketPrice = ticketPrice != null ? ticketPrice : new BigDecimal(0);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightDTO flightDTO = (FlightDTO) o;

        if (departureAirport != null
                ? !departureAirport.equals(flightDTO.departureAirport)
                : flightDTO.departureAirport != null)
            return false;

        if (arrivalAirport != null
                ? !arrivalAirport.equals(flightDTO.arrivalAirport)
                : flightDTO.arrivalAirport != null)
            return false;

        if (departureLocalDateTime != null
                ? !departureLocalDateTime.equals(flightDTO.departureLocalDateTime)
                : flightDTO.departureLocalDateTime != null)
            return false;

        if (arrivalLocalDateTime != null
                ? !arrivalLocalDateTime.equals(flightDTO.arrivalLocalDateTime)
                : flightDTO.arrivalLocalDateTime != null)
            return false;

        return ticketPrice != null
                ? ticketPrice.equals(flightDTO.ticketPrice)
                : flightDTO.ticketPrice == null;
    }

    @Override
    public int hashCode() {
        int result = departureAirport != null ? departureAirport.hashCode() : 0;
        result = 31 * result + (arrivalAirport != null ? arrivalAirport.hashCode() : 0);
        result = 31 * result + (departureLocalDateTime != null ? departureLocalDateTime.hashCode() : 0);
        result = 31 * result + (arrivalLocalDateTime != null ? arrivalLocalDateTime.hashCode() : 0);
        result = 31 * result + (ticketPrice != null ? ticketPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FlightDTO{" +
                "departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", departureLocalDateTime=" + departureLocalDateTime +
                ", arrivalLocalDateTime=" + arrivalLocalDateTime +
                ", ticketPrice=" + ticketPrice +
                '}';
    }
}
