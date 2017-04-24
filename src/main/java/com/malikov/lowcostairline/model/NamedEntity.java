package com.malikov.lowcostairline.model;

/**
 * Created by Malikov on 4/24/2017.
 */
public abstract class NamedEntity extends BaseEntity{

    private String name;

    protected NamedEntity(long id, String name) {
        super(id);
        this.name = name;
    }

    protected NamedEntity(String name) {
        this.name = name;
    }

    protected NamedEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
