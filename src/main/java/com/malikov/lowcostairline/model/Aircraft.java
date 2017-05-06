package com.malikov.lowcostairline.model;

/**
 * @author Yurii Malikov
 */
public class Aircraft extends NamedEntity {

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
