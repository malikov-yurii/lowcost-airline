package com.malikov.ticketsystem.dto;

import com.malikov.ticketsystem.IHasId;

/**
 * @author Yurii Malikov
 */
public abstract class BaseDTO implements IHasId{

    protected Long id;

    public BaseDTO() {
    }

    public BaseDTO(Long id) {
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
