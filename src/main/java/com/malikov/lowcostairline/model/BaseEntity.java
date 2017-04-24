package com.malikov.lowcostairline.model;

/**
 * Created by Malikov on 4/24/2017.
 */
public abstract class BaseEntity {

    private long id;

    protected BaseEntity(long id) {
        this.id = id;
    }

    protected BaseEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
