package com.malikov.lowcostairline.model;

/**
 * @author Yurii Malikov
 */
public class Aircraft extends NamedEntity {

    private AircraftModel model;

    public Aircraft(long id, String name, AircraftModel model) {
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
}
