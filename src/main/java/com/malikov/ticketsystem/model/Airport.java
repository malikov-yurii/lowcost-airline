package com.malikov.ticketsystem.model;

import javax.persistence.*;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = Airport.DELETE, query = "DELETE FROM Airport a WHERE a.id=:id"),
        @NamedQuery(name = Airport.ALL_SORTED, query = "SELECT a FROM Airport a ORDER BY a.id ASC"),
        @NamedQuery(name = Airport.BY_NAME_MASK, query = "SELECT a FROM Airport a WHERE lower(a.name) LIKE lower(:nameMask) ORDER BY a.id ASC")
})
@Entity
@Table(name = "airports")
public class Airport extends NamedEntity {

    public static final String DELETE = "Airport.delete";
    public static final String ALL_SORTED = "Airport.allSorted";

    public static final String BY_NAME_MASK = "Airport.byNameMask";

    @OneToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Airport(){}

    public Airport(Long id, String name, City city) {
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

    public Airport(Airport airport) {
        super(airport.getId(), airport.getName());
        city = airport.getCity();
    }

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
