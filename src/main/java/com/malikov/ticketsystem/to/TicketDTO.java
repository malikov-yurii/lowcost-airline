package com.malikov.ticketsystem.to;

import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */

// TODO: 5/31/2017 I can change it for tickets table
public class TicketDTO extends BaseTo{

    private String departureAirport;

    private String arrivalAirport;

    private String departureCity;

    private String arrivalCity;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureLocalDateTime;

    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalLocalDateTime;

    private BigDecimal price;

    private Boolean withBaggage;

    private Boolean withPriorityRegistration;

    private Integer seatNumber;

    public TicketDTO(Long id, String departureAirportName, String arrivalAirportName, String departureCityName, String arrivalCityName, LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime, BigDecimal price, Boolean withBaggage, Boolean withPriorityRegistration, Integer seatNumber) {
        super(id);
        // TODO: 5/30/2017 Should i remove unnecessary this.
        this.departureAirport = departureAirportName;
        this.arrivalAirport = arrivalAirportName;
        this.departureCity = departureCityName;
        this.arrivalCity = arrivalCityName;
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.price = price;
        this.withBaggage = withBaggage == null ? false : withBaggage;
        this.withPriorityRegistration = withPriorityRegistration == null ? false : withPriorityRegistration;
        this.seatNumber = seatNumber;
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

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean getWithBaggage() {
        return withBaggage;
    }

    public void setWithBaggage(Boolean withBaggage) {
        this.withBaggage = withBaggage;
    }

    public Boolean getWithPriorityRegistration() {
        return withPriorityRegistration;
    }

    public void setWithPriorityRegistration(Boolean withPriorityRegistration) {
        this.withPriorityRegistration = withPriorityRegistration;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

}
