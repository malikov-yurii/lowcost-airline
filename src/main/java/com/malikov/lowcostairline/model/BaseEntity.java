package com.malikov.lowcostairline.model;

/**
 * @author Yurii Malikov
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
