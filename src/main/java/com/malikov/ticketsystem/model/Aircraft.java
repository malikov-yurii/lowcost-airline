package com.malikov.ticketsystem.model;

import javax.persistence.*;

/**
 * @author Yurii Malikov
 */
@Entity
@Table(name = "aircraft")
public class Aircraft extends NamedEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id")
    private AircraftModel model;


    public Aircraft() {}

    public Aircraft(Long id, String name, AircraftModel model) {
        super(id, name);
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
                "model=" + model +
                '}';
    }
}
