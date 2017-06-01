package com.malikov.ticketsystem;

import com.malikov.ticketsystem.matchers.ModelMatcher;
import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.malikov.ticketsystem.FlightTestData.*;
import static com.malikov.ticketsystem.UserTestData.*;

/**
 * @author Yurii Malikov
 */
public class TicketTestData {

    private static final Logger LOG = LoggerFactory.getLogger(TicketTestData.class);

    public static final Ticket TICKET_1 = new Ticket(1L, FLIGHT_1, USER_1, USER_1.getName(), USER_1.getLastName(), new BigDecimal("30.00"),
            OffsetDateTime.parse("2017-05-30T07:30+03:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket TICKET_2_BELONGS_USER_2 = new Ticket(2L, FLIGHT_2, USER_2, USER_2.getName(), USER_2.getLastName(), new BigDecimal("30.00"),
            OffsetDateTime.parse("2017-05-23T08:30+03:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket TICKET_3_BELONGS_USER_3 = new Ticket(3L, FLIGHT_2, USER_3, USER_3.getName(), USER_3.getLastName(), new BigDecimal("83.00"),
            OffsetDateTime.parse("2017-05-23T09:30+03:00"), true, false, 2, TicketStatus.PAID);

    public static final Ticket TICKET_4 = new Ticket(4L, FLIGHT_2, USER_4, USER_4.getName(), USER_4.getLastName(), new BigDecimal("39.00"),
            OffsetDateTime.parse("2017-05-23T10:30+03:00"), false, true, 3, TicketStatus.PAID);

    public static final Ticket TICKET_5 = new Ticket(5L, FLIGHT_2, USER_5, USER_5.getName(), USER_5.getLastName(), new BigDecimal("92.00"),
            OffsetDateTime.parse("2017-05-23T11:30+03:00"), true, true, 4, TicketStatus.PAID);

    public static final Ticket TICKET_6_BELONGS_USER_2 = new Ticket(6L, FLIGHT_3, USER_2, USER_2.getName(), USER_2.getLastName(), new BigDecimal("30.00"),
            OffsetDateTime.parse("2017-05-23T08:30+01:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket TICKET_7 = new Ticket(7L, FLIGHT_4, USER_6, USER_6.getName(), USER_6.getLastName(), new BigDecimal("40.00"),
            OffsetDateTime.parse("2017-05-22T08:30+03:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket TICKET_8 = new Ticket(8L, FLIGHT_4, USER_7, USER_7.getName(), USER_7.getLastName(), new BigDecimal("103.00"),
            OffsetDateTime.parse("2017-05-22T09:30+03:00"), true, false, 2, TicketStatus.PAID);

    public static final Ticket TICKET_9 = new Ticket(9L, FLIGHT_5, USER_6, USER_6.getName(), USER_6.getLastName(), new BigDecimal("40.00"),
            OffsetDateTime.parse("2017-05-22T08:30+01:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket TICKET_10 = new Ticket(10L, FLIGHT_6, ADMIN, ADMIN.getName(), ADMIN.getLastName(), new BigDecimal("40.00"),
            OffsetDateTime.parse("2017-05-20T08:30+02:00"), false, false, 1, TicketStatus.PAID);

    public static final List<Ticket> TICKETS = Arrays.asList(TICKET_1, TICKET_2_BELONGS_USER_2, TICKET_3_BELONGS_USER_3, TICKET_4,
            TICKET_5, TICKET_6_BELONGS_USER_2, TICKET_7, TICKET_8, TICKET_9, TICKET_10);

    public static final ModelMatcher<Ticket> MATCHER = ModelMatcher.of(Ticket.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getPrice(), actual.getPrice())
                            && Objects.equals(expected.getPurchaseOffsetDateTime(), actual.getPurchaseOffsetDateTime())
                            && Objects.equals(expected.getWithBaggage(), actual.getWithBaggage())
                            && Objects.equals(expected.getWithPriorityRegistration(), actual.getWithPriorityRegistration())
                            && Objects.equals(expected.getPassengerFirstName(), actual.getPassengerFirstName())
                            && Objects.equals(expected.getPassengerLastName(), actual.getPassengerLastName())
                            && Objects.equals(expected.getDepartureAirportName(), actual.getDepartureAirportName())
                            && Objects.equals(expected.getArrivalAirportName(), actual.getArrivalAirportName())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getArrivalOffsetDateTime(), actual.getArrivalOffsetDateTime())));

    public static final ModelMatcher<Ticket> MATCHER_WITH_USER = ModelMatcher.of(Ticket.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getUser(), actual.getUser())
                            && Objects.equals(expected.getPrice(), actual.getPrice())
                            && Objects.equals(expected.getPurchaseOffsetDateTime(), actual.getPurchaseOffsetDateTime())
                            && Objects.equals(expected.getWithBaggage(), actual.getWithBaggage())
                            && Objects.equals(expected.getWithPriorityRegistration(), actual.getWithPriorityRegistration())
                            && Objects.equals(expected.getPassengerFirstName(), actual.getPassengerFirstName())
                            && Objects.equals(expected.getPassengerLastName(), actual.getPassengerLastName())
                            && Objects.equals(expected.getDepartureAirportName(), actual.getDepartureAirportName())
                            && Objects.equals(expected.getArrivalAirportName(), actual.getArrivalAirportName())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getArrivalOffsetDateTime(), actual.getArrivalOffsetDateTime())));

    public static final ModelMatcher<Ticket> MATCHER_WITH_FLIGHT = ModelMatcher.of(Ticket.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getFlight(), actual.getFlight())
                            && Objects.equals(expected.getPrice(), actual.getPrice())
                            && Objects.equals(expected.getPurchaseOffsetDateTime(), actual.getPurchaseOffsetDateTime())
                            && Objects.equals(expected.getWithBaggage(), actual.getWithBaggage())
                            && Objects.equals(expected.getWithPriorityRegistration(), actual.getWithPriorityRegistration())
                            && Objects.equals(expected.getPassengerFirstName(), actual.getPassengerFirstName())
                            && Objects.equals(expected.getPassengerLastName(), actual.getPassengerLastName())
                            && Objects.equals(expected.getDepartureAirportName(), actual.getDepartureAirportName())
                            && Objects.equals(expected.getArrivalAirportName(), actual.getArrivalAirportName())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getArrivalOffsetDateTime(), actual.getArrivalOffsetDateTime())));

    public static final ModelMatcher<Ticket> MATCHER_WITH_USER_AND_FLIGHT = ModelMatcher.of(Ticket.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getFlight(), actual.getFlight())
                            && Objects.equals(expected.getUser(), actual.getUser())
                            && Objects.equals(expected.getPrice(), actual.getPrice())
                            && Objects.equals(expected.getPurchaseOffsetDateTime(), actual.getPurchaseOffsetDateTime())
                            && Objects.equals(expected.getWithBaggage(), actual.getWithBaggage())
                            && Objects.equals(expected.getWithPriorityRegistration(), actual.getWithPriorityRegistration())
                            && Objects.equals(expected.getPassengerFirstName(), actual.getPassengerFirstName())
                            && Objects.equals(expected.getPassengerLastName(), actual.getPassengerLastName())
                            && Objects.equals(expected.getDepartureAirportName(), actual.getDepartureAirportName())
                            && Objects.equals(expected.getArrivalAirportName(), actual.getArrivalAirportName())
                            && Objects.equals(expected.getDepartureUtcDateTime(), actual.getDepartureUtcDateTime())
                            && Objects.equals(expected.getArrivalOffsetDateTime(), actual.getArrivalOffsetDateTime())));

}
