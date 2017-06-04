package com.malikov.ticketsystem.model;

import javax.persistence.*;

/**
 * @author Yurii Malikov
 */
@Entity
@Table(name = "airports")
public class Airport extends NamedEntity {

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Airport(){}

    public Airport(Long id, String name, City city) {
        super(id, name);
        this.city = city;
    }

    //public Airport(String name, City city) {
    //    super(name);
    //    this.city = city;
    //}

    //public Airport(City city) {
    //    this.city = city;
    //}

    //public Airport(Airport airport) {
    //    super(airport.getId(), airport.getName());
    //    city = airport.getCity();
    //}

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Airport airport = (Airport) o;

        return city != null ? city.equals(airport.city) : airport.city == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Airport{" +
                " id=" + getId() +
                ", name=" + getName() +
                ", city=" + city +
                '}';
    }

}
