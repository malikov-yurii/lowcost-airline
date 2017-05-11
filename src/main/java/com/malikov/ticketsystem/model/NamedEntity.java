package com.malikov.ticketsystem.model;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author Yurii Malikov
 */
@MappedSuperclass
public abstract class NamedEntity extends BaseEntity{

    @NotBlank
    @Column(name = "name", nullable = false)
    @SafeHtml
    private String name;

    public NamedEntity() {
    }

    protected NamedEntity(String name) {
        this.name = name;
    }

    protected NamedEntity(Long id, String name) {
        super(id);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
