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
    private Integer passengerSeatsQuantity;


    public AircraftModel() {}

    public AircraftModel(Long id, String name, Integer passengerSeatsQuantity) {
        super(id, name);
        this.passengerSeatsQuantity = passengerSeatsQuantity;
    }

    public AircraftModel(AircraftModel aircraftModel) {
        super(aircraftModel.getId(), aircraftModel.getName());
        this.passengerSeatsQuantity = aircraftModel.getPassengerSeatsQuantity();
    }


    public Integer getPassengerSeatsQuantity() {
        return passengerSeatsQuantity;
    }

    public void setPassengerSeatsQuantity(Integer passengerSeatsQuantity) {
        this.passengerSeatsQuantity = passengerSeatsQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AircraftModel that = (AircraftModel) o;

        return passengerSeatsQuantity == that.passengerSeatsQuantity;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + passengerSeatsQuantity;
        return result;
    }

    @Override
    public String toString() {
        return "AircraftModel{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", passengerSeatsQuantity=" + passengerSeatsQuantity +
                '}';
    }
}
