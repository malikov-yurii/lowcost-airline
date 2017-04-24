package com.malikov.lowcostairline.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by Malikov on 4/24/2017.
 */
public class Flight extends BaseEntity{

    private LocalDateTime departureTime;

    private LocalDateTime arrivalTime;

    private Airport departureAirport;

    private Airport arrivalAirport;

    private BigDecimal startTicketPrice;

    private BigDecimal endTicketPrice;

    private int totalSeatQuantity;

    private int freeSeatQuantity;



}
