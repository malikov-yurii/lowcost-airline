package com.malikov.ticketsystem.dto;

import com.malikov.ticketsystem.IHasId;

/**
 * @author Yurii Malikov
 */
public abstract class BaseTo implements IHasId{

    protected Long id;

    public BaseTo() {
    }

    public BaseTo(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
