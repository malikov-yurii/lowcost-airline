package com.malikov.ticketsystem.dto;

import com.malikov.ticketsystem.model.TicketStatus;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * @author Yurii Malikov
 */

public class TicketDTO extends BaseDTO {

    @NotNull
    @SafeHtml
    @Size(min = 2, max = 50)
    private String passengerFirstName;

    @NotNull
    @SafeHtml
    @Size(min = 2, max = 50)
    private String passengerLastName;

    @NotNull
    @SafeHtml
    @Size(min = 2, max = 255)
    private String departureAirport;

    @NotNull
    @SafeHtml
    @Size(min = 2, max = 255)
    private String arrivalAirport;

    @NotNull
    @SafeHtml
    @Size(min = 2, max = 70)
    private String departureCity;

    @NotNull
    @SafeHtml
    @Size(min = 2, max = 70)
    private String arrivalCity;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureLocalDateTime;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalLocalDateTime;

    @NotNull
    private BigDecimal price;

    private Boolean hasBaggage;

    private Boolean hasPriorityRegistrationAndBoarding;

    private Integer seatNumber;

    private TicketStatus status;

    public TicketDTO() {}

    public TicketDTO(Long id, String passengerFirstName, String passengerLastName, String departureAirportName,
                     String arrivalAirportName, String departureCityName, String arrivalCityName,
                     LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime, BigDecimal price,
                     Boolean hasBaggage, Boolean hasPriorityRegistrationAndBoarding, Integer seatNumber,
                     TicketStatus status) {
        super(id);
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        this.departureAirport = departureAirportName;
        this.arrivalAirport = arrivalAirportName;
        this.departureCity = departureCityName;
        this.arrivalCity = arrivalCityName;
        this.departureLocalDateTime = departureLocalDateTime;
        this.arrivalLocalDateTime = arrivalLocalDateTime;
        this.price = price;
        this.hasBaggage = hasBaggage == null ? false : hasBaggage;
        this.hasPriorityRegistrationAndBoarding = hasPriorityRegistrationAndBoarding == null
                ? false
                : hasPriorityRegistrationAndBoarding;
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
        return price.setScale(6, RoundingMode.HALF_UP);
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Boolean isHasBaggage() {
        return hasBaggage;
    }

    public void setHasBaggage(Boolean hasBaggage) {
        this.hasBaggage = hasBaggage;
    }

    public Boolean isHasPriorityRegistrationAndBoarding() {
        return hasPriorityRegistrationAndBoarding;
    }

    public void setHasPriorityRegistrationAndBoarding(Boolean hasPriorityRegistrationAndBoarding) {
        this.hasPriorityRegistrationAndBoarding = hasPriorityRegistrationAndBoarding;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketDTO ticketDTO = (TicketDTO) o;

        if (passengerFirstName != null
                ? !passengerFirstName.equals(ticketDTO.passengerFirstName)
                : ticketDTO.passengerFirstName != null)
            return false;
        if (passengerLastName != null
                ? !passengerLastName.equals(ticketDTO.passengerLastName)
                : ticketDTO.passengerLastName != null)
            return false;
        if (departureAirport != null
                ? !departureAirport.equals(ticketDTO.departureAirport) : ticketDTO.departureAirport != null)
            return false;
        if (arrivalAirport != null
                ? !arrivalAirport.equals(ticketDTO.arrivalAirport)
                : ticketDTO.arrivalAirport != null)
            return false;
        if (departureCity != null
                ? !departureCity.equals(ticketDTO.departureCity)
                : ticketDTO.departureCity != null)
            return false;
        if (arrivalCity != null
                ? !arrivalCity.equals(ticketDTO.arrivalCity)
                : ticketDTO.arrivalCity != null)
            return false;
        if (departureLocalDateTime != null
                ? !departureLocalDateTime.equals(ticketDTO.departureLocalDateTime)
                : ticketDTO.departureLocalDateTime != null)
            return false;
        if (arrivalLocalDateTime != null
                ? !arrivalLocalDateTime.equals(ticketDTO.arrivalLocalDateTime)
                : ticketDTO.arrivalLocalDateTime != null)
            return false;
        if (price != null ? !price.equals(ticketDTO.price) : ticketDTO.price != null) return false;
        if (hasBaggage != null ? !hasBaggage.equals(ticketDTO.hasBaggage) : ticketDTO.hasBaggage != null)
            return false;
        if (hasPriorityRegistrationAndBoarding != null
                ? !hasPriorityRegistrationAndBoarding.equals(ticketDTO.hasPriorityRegistrationAndBoarding)
                : ticketDTO.hasPriorityRegistrationAndBoarding != null)
            return false;
        if (seatNumber != null
                ? !seatNumber.equals(ticketDTO.seatNumber)
                : ticketDTO.seatNumber != null) return false;
        return status == ticketDTO.status;
    }

    @Override
    public int hashCode() {
        int result = passengerFirstName != null ? passengerFirstName.hashCode() : 0;
        result = 31 * result + (passengerLastName != null ? passengerLastName.hashCode() : 0);
        result = 31 * result + (departureAirport != null ? departureAirport.hashCode() : 0);
        result = 31 * result + (arrivalAirport != null ? arrivalAirport.hashCode() : 0);
        result = 31 * result + (departureCity != null ? departureCity.hashCode() : 0);
        result = 31 * result + (arrivalCity != null ? arrivalCity.hashCode() : 0);
        result = 31 * result + (departureLocalDateTime != null ? departureLocalDateTime.hashCode() : 0);
        result = 31 * result + (arrivalLocalDateTime != null ? arrivalLocalDateTime.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (hasBaggage != null ? hasBaggage.hashCode() : 0);
        result = 31 * result + (hasPriorityRegistrationAndBoarding != null
                ? hasPriorityRegistrationAndBoarding.hashCode() : 0);
        result = 31 * result + (seatNumber != null ? seatNumber.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
