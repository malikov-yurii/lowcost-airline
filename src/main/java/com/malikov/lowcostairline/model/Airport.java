package com.malikov.lowcostairline.model;

/**
 * @author Yurii Malikov
 */
public class Airport extends NamedEntity {

    private City city;

    public Airport(){}

    public Airport(long id, String name, City city) {
        super(id, name);
        this.city = city;
    }

    public Airport(String name, City city) {
        super(name);
        this.city = city;
    }

    public Airport(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

}
