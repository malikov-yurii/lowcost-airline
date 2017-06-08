package com.malikov.ticketsystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.malikov.ticketsystem.model.TicketStatus;
import com.malikov.ticketsystem.util.DateTimeUtil;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.ALWAYS;

/**
 * @author Yurii Malikov
 */

@JsonInclude(ALWAYS)
public class TicketWithRemainingDelayDTO extends BaseDTO {

    @NotNull
    private String passengerFirstName;

    @NotNull
    private String passengerLastName;

    @NotNull
    private String departureAirport;

    @NotNull
    private String arrivalAirport;

    @NotNull
    private String departureCity;

    @NotNull
    private String arrivalCity;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime departureLocalDateTime;

    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime arrivalLocalDateTime;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Boolean hasBaggage;

    @NotNull
    private Boolean hasPriorityRegistrationAndBoarding;

    @NotNull
    private Integer seatNumber;

    @NotNull
    private TicketStatus status;

    private Long remainingDelay;


    public TicketWithRemainingDelayDTO() {}

    public TicketWithRemainingDelayDTO(Long id, String passengerFirstName, String passengerLastName,
                                       String departureAirportName, String arrivalAirportName,
                                       String departureCityName, String arrivalCityName,
                                       LocalDateTime departureLocalDateTime, LocalDateTime arrivalLocalDateTime,
                                       BigDecimal price, Boolean hasBaggage, Boolean hasPriorityRegistrationAndBoarding,
                                       Integer seatNumber, TicketStatus status, Long remainingDelay) {
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
        this.remainingDelay = remainingDelay;
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

    public Long getRemainingDelay() {
        return remainingDelay;
    }

    public void setRemainingDelay(Long remainingDelay) {
        this.remainingDelay = remainingDelay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TicketWithRemainingDelayDTO that = (TicketWithRemainingDelayDTO) o;

        if (passengerFirstName != null
                ? !passengerFirstName.equals(that.passengerFirstName)
                : that.passengerFirstName != null)
            return false;
        if (passengerLastName != null
                ? !passengerLastName.equals(that.passengerLastName)
                : that.passengerLastName != null)
            return false;
        if (departureAirport != null
                ? !departureAirport.equals(that.departureAirport)
                : that.departureAirport != null)
            return false;
        if (arrivalAirport != null ? !arrivalAirport.equals(that.arrivalAirport)
                : that.arrivalAirport != null)
            return false;
        if (departureCity != null
                ? !departureCity.equals(that.departureCity) : that.departureCity != null)
            return false;
        if (arrivalCity != null
                ? !arrivalCity.equals(that.arrivalCity)
                : that.arrivalCity != null) return false;
        if (departureLocalDateTime != null ? !departureLocalDateTime.equals(that.departureLocalDateTime)
                : that.departureLocalDateTime != null)
            return false;
        if (arrivalLocalDateTime != null
                ? !arrivalLocalDateTime.equals(that.arrivalLocalDateTime)
                : that.arrivalLocalDateTime != null)
            return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (hasBaggage != null
                ? !hasBaggage.equals(that.hasBaggage) : that.hasBaggage != null) return false;
        if (hasPriorityRegistrationAndBoarding != null
                ? !hasPriorityRegistrationAndBoarding.equals(that.hasPriorityRegistrationAndBoarding)
                : that.hasPriorityRegistrationAndBoarding != null)
            return false;
        if (seatNumber != null
                ? !seatNumber.equals(that.seatNumber) : that.seatNumber != null) return false;
        if (status != that.status) return false;
        return remainingDelay != null ? remainingDelay.equals(that.remainingDelay) : that.remainingDelay == null;
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
        result = 31 * result + (remainingDelay != null ? remainingDelay.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TicketWithRemainingDelayDTO{" +
                "passengerFirstName='" + passengerFirstName + '\'' +
                ", passengerLastName='" + passengerLastName + '\'' +
                ", departureAirport='" + departureAirport + '\'' +
                ", arrivalAirport='" + arrivalAirport + '\'' +
                ", departureCity='" + departureCity + '\'' +
                ", arrivalCity='" + arrivalCity + '\'' +
                ", departureLocalDateTime=" + departureLocalDateTime +
                ", arrivalLocalDateTime=" + arrivalLocalDateTime +
                ", price=" + price +
                ", hasBaggage=" + hasBaggage +
                ", hasPriorityRegistrationAndBoarding=" + hasPriorityRegistrationAndBoarding +
                ", seatNumber=" + seatNumber +
                ", status=" + status +
                ", remainingDelay=" + remainingDelay +
                '}';
    }
}
