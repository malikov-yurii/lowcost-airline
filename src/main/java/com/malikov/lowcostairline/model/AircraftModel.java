package com.malikov.lowcostairline.model;

/**
 * @author Yurii Malikov
 */
public class AircraftModel extends NamedEntity {

    private int passengersSeatsQuantity;

    public AircraftModel(long id, String name, int passengersSeatsQuantity) {
        super(id, name);
        this.passengersSeatsQuantity = passengersSeatsQuantity;
    }

    public AircraftModel(String name, int passengersSeatsQuantity) {
        super(name);
        this.passengersSeatsQuantity = passengersSeatsQuantity;
    }

    public AircraftModel(int passengersSeatsQuantity) {
        this.passengersSeatsQuantity = passengersSeatsQuantity;
    }

    public int getPassengersSeatsQuantity() {
        return passengersSeatsQuantity;
    }

    public void setPassengersSeatsQuantity(int passengersSeatsQuantity) {
        this.passengersSeatsQuantity = passengersSeatsQuantity;
    }
}
