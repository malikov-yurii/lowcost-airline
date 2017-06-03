package com.malikov.ticketsystem.dto;

/**
 * @author Yurii Malikov
 */
public class FreeSeatsDTO {

    private Integer totalSeats;

    private Integer[] freeSeats;

    public FreeSeatsDTO(Integer totalSeats, Integer[] freeSeats) {
        this.totalSeats = totalSeats;
        this.freeSeats = freeSeats;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer[] getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer[] freeSeats) {
        this.freeSeats = freeSeats;
    }
}
