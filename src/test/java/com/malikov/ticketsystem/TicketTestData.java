package com.malikov.ticketsystem;

import com.malikov.ticketsystem.model.Ticket;
import com.malikov.ticketsystem.model.TicketStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import static com.malikov.ticketsystem.FlightTestData.*;
import static com.malikov.ticketsystem.UserTestData.*;
import static java.math.RoundingMode.HALF_UP;

/**
 * @author Yurii Malikov
 */
public class TicketTestData {

    public static final Ticket TICKET_1 = new Ticket(1L, FLIGHT_1, USER_1, USER_1.getName(), USER_1.getLastName(), new BigDecimal(30).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-30T07:30+03:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket USER_2_FIRST_TICKET = new Ticket(2L, FLIGHT_2, USER_2, USER_2.getName(), USER_2.getLastName(), new BigDecimal(30).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-23T08:30+03:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket TICKET_3_BELONGS_USER_3 = new Ticket(3L, FLIGHT_2, USER_3, USER_3.getName(), USER_3.getLastName(), new BigDecimal(83).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-23T09:30+03:00"), true, false, 2, TicketStatus.PAID);

    public static final Ticket TICKET_4 = new Ticket(4L, FLIGHT_2, USER_4, USER_4.getName(), USER_4.getLastName(), new BigDecimal(39).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-23T10:30+03:00"), false, true, 3, TicketStatus.PAID);

    public static final Ticket TICKET_5 = new Ticket(5L, FLIGHT_2, USER_5, USER_5.getName(), USER_5.getLastName(), new BigDecimal(92).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-23T11:30+03:00"), true, true, 4, TicketStatus.PAID);

    public static final Ticket USER_2_LAST_TICKET = new Ticket(6L, FLIGHT_3, USER_2, USER_2.getName(), USER_2.getLastName(), new BigDecimal(30).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-23T08:30+01:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket FLIGHT_4_USER_6_TICKET = new Ticket(7L, FLIGHT_4, USER_6, USER_6.getName(), USER_6.getLastName(), new BigDecimal(40).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-22T08:30+03:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket FLIGHT_4_USER_7_TICKET = new Ticket(8L, FLIGHT_4, USER_7, USER_7.getName(), USER_7.getLastName(), new BigDecimal(3).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-22T09:30+03:00"), true, false, 2, TicketStatus.PAID);

    public static final Ticket SECOND_TICKET_OF_USER_6 = new Ticket(9L, FLIGHT_5, USER_6, USER_6.getName(), USER_6.getLastName(), new BigDecimal(40).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-22T08:30+01:00"), false, false, 1, TicketStatus.PAID);

    public static final Ticket TICKET_10 = new Ticket(10L, FLIGHT_6, ADMIN, ADMIN.getName(), ADMIN.getLastName(), new BigDecimal(40).setScale(6, HALF_UP),
            OffsetDateTime.parse("2017-05-20T08:30+02:00"), false, false, 1, TicketStatus.PAID);

    public static final int FLIGHT_4_TICKET_QUANTITY = 2;

    // TODO: 6/7/2017 codestyle? SHOULD I LEAVE IT HERE?

    public static final LocalDateTime BEFORE_USER_2_LAST_TICKET_DEPARTURE_DATE_TIME =
            USER_2_LAST_TICKET.getDepartureUtcDateTime().minusMinutes(1);

    public static final LocalDateTime AFTER_USER_2_FIRST_TICKET_DEPARTURE_DATE_TIME =
            USER_2_FIRST_TICKET.getDepartureUtcDateTime().plusMinutes(1);


}
