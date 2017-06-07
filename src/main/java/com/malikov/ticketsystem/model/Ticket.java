package com.malikov.ticketsystem.model;

import com.malikov.ticketsystem.util.converter.OffsetDateTimeConverter;
import com.malikov.ticketsystem.util.converter.ZoneIdConverter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * @author Yurii Malikov
 */
@Entity
// TODO: 6/6/2017 why do i need constraint description here
@Table(name = "tickets", uniqueConstraints = @UniqueConstraint(columnNames = {"flight_id", "seat_number"},
        name = "flight_seat_unique_constraint"))
public class Ticket extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User user;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "purchase_offsetdatetime")
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime purchaseOffsetDateTime;

    @Column(name = "with_baggage")
    // TODO: 5/28/2017 Do I need annotation below
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean withBaggage;

    @Column(name = "with_priority_registration")
    // TODO: 5/28/2017 Do I need annotation below
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean withPriorityRegistration;

    // TODO: 5/8/2017 Maybe I need dto store ticket information in a single column and parseToLocalDateTime if I need dto??? But in a such case I cannot easily add column with additional ticket information
    @Column(name = "passenger_name")
    private String passengerFirstName;

    @Column(name = "passenger_last_name")
    private String passengerLastName;

    @Column(name = "departure_airport_name")
    private String departureAirportName;

    @Column(name = "arrival_airport_name")
    private String arrivalAirportName;

    @Column(name = "departure_city_name")
    private String departureCityName;

    @Column(name = "arrival_city_name")
    private String arrivalCityName;

    @Column(name = "departure_utc_datetime")
    private LocalDateTime departureUtcDateTime;

    @Column(name = "departure_time_zone")
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId departureZoneId;

    @Column(name = "arrival_offset_datetime")
    @Convert(converter = OffsetDateTimeConverter.class)
    private OffsetDateTime arrivalOffsetDateTime;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;


    public Ticket() {
    }

    public Ticket(Long id, Flight flight, User user, String passengerFirstName, String passengerLastName,
                  BigDecimal price, OffsetDateTime purchaseOffsetDateTime, Boolean withBaggage,
                  Boolean withPriorityRegistration, Integer seatNumber, TicketStatus status) {
        super(id);
        this.flight = flight;
        this.user = user;
        this.price = price;
        this.purchaseOffsetDateTime = purchaseOffsetDateTime;
        this.withBaggage = withBaggage;
        this.withPriorityRegistration = withPriorityRegistration;
        this.passengerFirstName = passengerFirstName;
        this.passengerLastName = passengerLastName;
        departureAirportName = flight.getDepartureAirport().getName();
        arrivalAirportName = flight.getArrivalAirport().getName();
        departureUtcDateTime = flight.getDepartureUtcDateTime();
        departureZoneId = flight.getDepartureAirport().getCity().getZoneId();
        departureCityName = flight.getDepartureAirport().getCity().getName();
        arrivalCityName = flight.getArrivalAirport().getCity().getName();
        arrivalOffsetDateTime = OffsetDateTime.ofInstant(flight.getUtcLocalDateTime().toInstant(ZoneOffset.UTC),
                flight.getArrivalAirport().getCity().getZoneId());
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OffsetDateTime getPurchaseOffsetDateTime() {
        return purchaseOffsetDateTime;
    }

    public void setPurchaseOffsetDateTime(OffsetDateTime purchaseOffsetDateTime) {
        this.purchaseOffsetDateTime = purchaseOffsetDateTime;
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

    public String getDepartureAirportName() {
        return departureAirportName;
    }

    public void setDepartureAirportName(String departureAirportName) {
        this.departureAirportName = departureAirportName;
    }

    public String getArrivalAirportName() {
        return arrivalAirportName;
    }

    public void setArrivalAirportName(String arrivalAirportName) {
        this.arrivalAirportName = arrivalAirportName;
    }

    public String getDepartureCityName() {
        return departureCityName;
    }

    public void setDepartureCityName(String departureCityName) {
        this.departureCityName = departureCityName;
    }

    public String getArrivalCityName() {
        return arrivalCityName;
    }

    public void setArrivalCityName(String arrivalCityName) {
        this.arrivalCityName = arrivalCityName;
    }

    public LocalDateTime getDepartureUtcDateTime() {
        return departureUtcDateTime;
    }

    public void setDepartureUtcDateTime(LocalDateTime departureUtcDateTime) {
        this.departureUtcDateTime = departureUtcDateTime;
    }

    public ZoneId getDepartureZoneId() {
        return departureZoneId;
    }

    public void setDepartureZoneId(ZoneId departureZoneId) {
        this.departureZoneId = departureZoneId;
    }

    public OffsetDateTime getArrivalOffsetDateTime() {
        return arrivalOffsetDateTime;
    }

    public void setArrivalOffsetDateTime(OffsetDateTime arrivalOffsetDateTime) {
        this.arrivalOffsetDateTime = arrivalOffsetDateTime;
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
        if (!super.equals(o)) return false;

        Ticket ticket = (Ticket) o;

        if (price != null ? !price.equals(ticket.price) : ticket.price != null) {
            return false;
        }
        if (purchaseOffsetDateTime != null
                ? !purchaseOffsetDateTime.equals(ticket.purchaseOffsetDateTime)
                : ticket.purchaseOffsetDateTime != null) {
            return false;
        }
        if (withBaggage != null
                ? !withBaggage.equals(ticket.withBaggage)
                : ticket.withBaggage != null) {
            return false;
        }
        if (withPriorityRegistration != null
                ? !withPriorityRegistration.equals(ticket.withPriorityRegistration)
                : ticket.withPriorityRegistration != null) {
            return false;
        }
        if (passengerFirstName != null
                ? !passengerFirstName.equals(ticket.passengerFirstName)
                : ticket.passengerFirstName != null) {
            return false;
        }
        if (passengerLastName != null
                ? !passengerLastName.equals(ticket.passengerLastName)
                : ticket.passengerLastName != null) {
            return false;
        }
        if (departureAirportName != null
                ? !departureAirportName.equals(ticket.departureAirportName)
                : ticket.departureAirportName != null) {
            return false;
        }
        if (arrivalAirportName != null
                ? !arrivalAirportName.equals(ticket.arrivalAirportName)
                : ticket.arrivalAirportName != null) {
            return false;
        }
        if (departureCityName != null
                ? !departureCityName.equals(ticket.departureCityName)
                : ticket.departureCityName != null) {
            return false;
        }
        if (arrivalCityName != null
                ? !arrivalCityName.equals(ticket.arrivalCityName)
                : ticket.arrivalCityName != null) {
            return false;
        }
        if (departureUtcDateTime != null
                ? !departureUtcDateTime.equals(ticket.departureUtcDateTime)
                : ticket.departureUtcDateTime != null) {
            return false;
        }
        if (departureZoneId != null
                ? !departureZoneId.equals(ticket.departureZoneId)
                : ticket.departureZoneId != null) {
            return false;
        }
        if (arrivalOffsetDateTime != null
                ? !arrivalOffsetDateTime.equals(ticket.arrivalOffsetDateTime)
                : ticket.arrivalOffsetDateTime != null) {
            return false;
        }
        return seatNumber != null ? seatNumber.equals(ticket.seatNumber) : ticket.seatNumber == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (flight != null ? flight.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (purchaseOffsetDateTime != null ? purchaseOffsetDateTime.hashCode() : 0);
        result = 31 * result + (withBaggage != null ? withBaggage.hashCode() : 0);
        result = 31 * result + (withPriorityRegistration != null ? withPriorityRegistration.hashCode() : 0);
        result = 31 * result + (passengerFirstName != null ? passengerFirstName.hashCode() : 0);
        result = 31 * result + (passengerLastName != null ? passengerLastName.hashCode() : 0);
        result = 31 * result + (departureAirportName != null ? departureAirportName.hashCode() : 0);
        result = 31 * result + (arrivalAirportName != null ? arrivalAirportName.hashCode() : 0);
        result = 31 * result + (departureCityName != null ? departureCityName.hashCode() : 0);
        result = 31 * result + (arrivalCityName != null ? arrivalCityName.hashCode() : 0);
        result = 31 * result + (departureUtcDateTime != null ? departureUtcDateTime.hashCode() : 0);
        result = 31 * result + (departureZoneId != null ? departureZoneId.hashCode() : 0);
        result = 31 * result + (arrivalOffsetDateTime != null ? arrivalOffsetDateTime.hashCode() : 0);
        result = 31 * result + (seatNumber != null ? seatNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "flightId=" + flight.getId() +
                ", userId=" + user.getId() +
                ", price=" + price +
                ", purchaseOffsetDateTime=" + purchaseOffsetDateTime +
                ", withBaggage=" + withBaggage +
                ", withPriorityRegistration=" + withPriorityRegistration +
                ", passengerFirstName='" + passengerFirstName + '\'' +
                ", passengerLastName='" + passengerLastName + '\'' +
                ", departureAirportName='" + departureAirportName + '\'' +
                ", arrivalAirportName='" + arrivalAirportName + '\'' +
                ", departureCityName='" + departureCityName + '\'' +
                ", arrivalCityName='" + arrivalCityName + '\'' +
                ", departureUtcDateTime=" + departureUtcDateTime +
                ", departureZoneId=" + departureZoneId +
                ", arrivalOffsetDateTime=" + arrivalOffsetDateTime +
                ", seatNumber=" + seatNumber +
                '}';
    }
}
