package com.malikov.lowcostairline.model;

import com.malikov.lowcostairline.util.converters.ZoneIdConverter;

import javax.persistence.*;
import java.time.ZoneId;

/**
  * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@NamedQueries({
        @NamedQuery(name = City.DELETE, query = "DELETE FROM City c WHERE c.id=:id"),
        @NamedQuery(name = City.ALL_SORTED, query = "SELECT c FROM City c ORDER BY c.id ASC")
})
@Entity
@Table(name = "cities")
public class City extends NamedEntity {

    public static final String DELETE = "City.delete";
    public static final String ALL_SORTED = "City.allSorted";


    @Column(name = "time_zone")
    @Convert(converter = ZoneIdConverter.class)
    private ZoneId zoneId;

    public City(){}

    public City(Long id, String name, ZoneId zoneId) {
        super(id, name);
        this.zoneId = zoneId;
    }

    public City(String name, ZoneId zoneId) {
        super(name);
        this.zoneId = zoneId;
    }

    public City(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public City(City city) {
        super(city.getId(), city.getName());
        zoneId = city.getZoneId();
    }

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        City city = (City) o;

        return zoneId != null ? zoneId.equals(city.zoneId) : city.zoneId == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (zoneId != null ? zoneId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "City{" +
                " id=" + getId() +
                ", name=" + getName() +
                ", zoneId=" + zoneId +
                '}';
    }
}
