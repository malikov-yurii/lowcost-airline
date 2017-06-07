package com.malikov.ticketsystem.model;

import com.malikov.ticketsystem.IHasId;
import org.hibernate.Hibernate;

import javax.persistence.*;

/**
 * @author Yurii Malikov
 */
@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class BaseEntity implements IHasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Access(value = AccessType.PROPERTY)
    private Long id;


    public BaseEntity() {
    }

    protected BaseEntity(Long id) {
        this.id = id;
    }


    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || !getClass().equals(Hibernate.getClass(o))) {
            return false;
        }

        BaseEntity that = (BaseEntity) o;

        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return (getId() == null) ? 0 : getId().intValue();
    }

    @Override
    public String toString() {
        return String.format("Entity of type %s with id: %s", getClass().getName(), getId());
    }
}
