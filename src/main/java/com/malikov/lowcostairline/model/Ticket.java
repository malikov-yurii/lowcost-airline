package com.malikov.lowcostairline.model;

import java.math.BigDecimal;

/**
 * @author Yurii Malikov
 */
public class Ticket extends BaseEntity {

    private User user;

    private Flight flight;

    private BigDecimal price;

    private boolean hasBaggage;

    private boolean hasPriorityRegistration;




}
