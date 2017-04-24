package com.malikov.lowcostairline.model;

import java.util.TimeZone;

/**
 * Created by Malikov on 4/24/2017.
 */
public class City extends NamedEntity {

    private TimeZone timeZone;

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
}
