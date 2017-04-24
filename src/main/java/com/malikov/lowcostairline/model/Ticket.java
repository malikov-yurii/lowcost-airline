package com.malikov.lowcostairline.model;

import java.math.BigDecimal;

/**
 * Created by Malikov on 4/24/2017.
 */
public class Ticket extends BaseEntity {

    private User user;

    private Flight flight;

    private BigDecimal price;

    private boolean hasBaggage;

    private boolean hasPriorityRegistration;




}
