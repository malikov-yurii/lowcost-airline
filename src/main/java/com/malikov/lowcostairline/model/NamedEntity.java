package com.malikov.lowcostairline.model;

/**
 * @author Yurii Malikov
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
