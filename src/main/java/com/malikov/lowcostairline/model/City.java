package com.malikov.lowcostairline.model;

import java.time.ZoneId;

/**
  * @author Yurii Malikov
 */
public class City extends NamedEntity {

    private ZoneId zoneId;

    public City(long id, String name, ZoneId zoneId) {
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

    public ZoneId getZoneId() {
        return zoneId;
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

}
