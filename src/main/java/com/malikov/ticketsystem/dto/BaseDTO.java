package com.malikov.ticketsystem.dto;

import com.malikov.ticketsystem.IHasId;

/**
 * @author Yurii Malikov
 */
public abstract class BaseDTO implements IHasId {

    protected Long id;


    public BaseDTO() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseDTO baseDTO = (BaseDTO) o;

        return id != null ? id.equals(baseDTO.id) : baseDTO.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "id=" + id +
                '}';
    }
}
