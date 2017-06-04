package com.malikov.ticketsystem.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;

/**
 * @author Yurii Malikov
 */
@Entity
@Table(name = "aircraft_models")
@AttributeOverride(name = "name", column = @Column(name = "model_name"))
public class AircraftModel extends NamedEntity {

    @Column(name = "passenger_seats_quantity")
    @Range(min = 0, max = 450)
    private int passengersSeatsQuantity;

    public AircraftModel(){}

    public AircraftModel(Long id, String name, Integer passengersSeatsQuantity) {
        super(id, name);
        this.passengersSeatsQuantity = passengersSeatsQuantity;
    }

    //public AircraftModel(String name, Integer passengersSeatsQuantity) {
    //    super(name);
    //    this.passengersSeatsQuantity = passengersSeatsQuantity;
    //}
    //
    //public AircraftModel(int passengersSeatsQuantity) {
    //    this.passengersSeatsQuantity = passengersSeatsQuantity;
    //}

    public AircraftModel(AircraftModel aircraftModel) {
        super(aircraftModel.getId(), aircraftModel.getName());
        this.passengersSeatsQuantity = aircraftModel.getPassengersSeatsQuantity();
    }

    public int getPassengersSeatsQuantity() {
        return passengersSeatsQuantity;
    }

    public void setPassengersSeatsQuantity(int passengersSeatsQuantity) {
        this.passengersSeatsQuantity = passengersSeatsQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AircraftModel that = (AircraftModel) o;

        return passengersSeatsQuantity == that.passengersSeatsQuantity;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + passengersSeatsQuantity;
        return result;
    }

    @Override
    public String toString() {
        return "AircraftModel{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", passengersSeatsQuantity=" + passengersSeatsQuantity +
                '}';
    }
}
