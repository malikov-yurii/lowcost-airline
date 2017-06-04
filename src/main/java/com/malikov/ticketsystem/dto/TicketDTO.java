package com.malikov.ticketsystem.dto;

import com.malikov.ticketsystem.model.TicketStatus;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */

// TODO: 5/31/2017 I can change it for tickets table
public class TicketDTO extends BaseDTO {

    private String passengerFirstName;

    private String passengerLastName;

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

    private Boolean withPriorityRegistrationAndBoarding;

    private Integer seatNumber;

    private TicketStatus status;

    public TicketDTO(){}

    public TicketDTO(Long id, String passengerFirstName, String passengerLastName,
                     String departureAirportName, String arrivalAirportName,
                     String departureCityName, String arrivalCityName,
                     LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime,
                     BigDecimal price, Boolean withBaggage, Boolean withPriorityRegistrationAndBoarding,
                     Integer seatNumber,
                     TicketStatus status
    ) {
        super(id);
        // TODO: 5/30/2017 Should i remove unnecessary this.
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.departureAirport = departureAirportName;
        this.arrivalAirport = arrivalAirportName;
        this.departureCity = departureCityName;
        this.arrivalCity = arrivalCityName;
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.price = price;
        this.withBaggage = withBaggage == null ? false : withBaggage;
        this.withPriorityRegistrationAndBoarding = withPriorityRegistrationAndBoarding == null ? false : withPriorityRegistrationAndBoarding;
        this.seatNumber = seatNumber;
        this.status = status;
    }


    public String getPassengerFirstName() {
        return passengerFirstName;
    }

    public void setPassengerFirstName(String passengerFirstName) {
        this.passengerFirstName = passengerFirstName;
    }

    public String getPassengerLastName() {
        return passengerLastName;
    }

    public void setPassengerLastName(String passengerLastName) {
        this.passengerLastName = passengerLastName;
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

    public Boolean getWithPriorityRegistrationAndBoarding() {
        return withPriorityRegistrationAndBoarding;
    }

    public void setWithPriorityRegistrationAndBoarding(Boolean withPriorityRegistrationAndBoarding) {
        this.withPriorityRegistrationAndBoarding = withPriorityRegistrationAndBoarding;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }
}
