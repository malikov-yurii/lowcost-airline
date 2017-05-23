package com.malikov.ticketsystem.model;

import javax.persistence.*;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Aircraft.DELETE, query = "DELETE FROM Aircraft a WHERE a.id=:id"),
        @NamedQuery(name = Aircraft.ALL_SORTED, query = "SELECT a FROM Aircraft a ORDER BY a.id ASC"),
        @NamedQuery(name = Aircraft.BY_NAME_MASK, query = "SELECT a FROM Aircraft a WHERE lower(a.name) LIKE lower(:nameMask) ORDER BY a.id ASC"),
        @NamedQuery(name = Aircraft.BY_NAME, query = "SELECT a FROM Aircraft a WHERE lower(a.name) = lower(:name) ORDER BY a.id ASC")
})
@Entity
@Table(name = "aircraft")
public class Aircraft extends NamedEntity {

    public static final String DELETE = "Aircraft.delete";
    public static final String ALL_SORTED = "Aircraft.allSorted";

    public static final String BY_NAME_MASK = "Aircraft.byNameMask";

    public static final String BY_NAME = "Aircraft.byName";

    @ManyToOne
    @JoinColumn(name = "model_id")
    private AircraftModel model;

    public Aircraft(){}

    public Aircraft(Long id, String name, AircraftModel model) {
        super(id, name);
        this.model = model;
    }

    public Aircraft(String name, AircraftModel model) {
        super(name);
        this.model = model;
    }

    public Aircraft(AircraftModel model) {
        this.model = model;
    }

    public Aircraft(Aircraft aircraft) {
        super(aircraft.getId(), aircraft.getName());
        model = aircraft.getModel();
    }

    public AircraftModel getModel() {
        return model;
    }

    public void setModel(AircraftModel model) {
        this.model = model;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Aircraft aircraft = (Aircraft) o;

        return model != null ? model.equals(aircraft.model) : aircraft.model == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Aircraft{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", model=" + model +
                '}';
    }
}
