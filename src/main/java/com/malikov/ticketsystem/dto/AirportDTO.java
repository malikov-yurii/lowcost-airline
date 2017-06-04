package com.malikov.ticketsystem.dto;

/**
 * @author Yurii Malikov
 */
public class AirportDTO extends BaseDTO{

    // TODO: 5/21/2017 extend it (I don't need it for TO)

    private String name;

    private String cityName;

    public AirportDTO() {}

    public AirportDTO(Long id, String name, String cityName) {
        super(id);
        this.name = name;
        this.cityName = cityName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
