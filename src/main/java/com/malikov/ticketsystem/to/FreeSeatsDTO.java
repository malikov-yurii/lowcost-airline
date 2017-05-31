package com.malikov.ticketsystem.to;

/**
 * @author Yurii Malikov
 */
public class FreeSeatsDTO {

    private Integer totalSeatsQuantity;

    private Integer[] seats;

    public FreeSeatsDTO(Integer totalSeatsQuantity, Integer[] seats) {
        this.totalSeatsQuantity = totalSeatsQuantity;
        this.seats = seats;
    }

    public Integer getTotalSeatsQuantity() {
        return totalSeatsQuantity;
    }

    public void setTotalSeatsQuantity(Integer totalSeatsQuantity) {
        this.totalSeatsQuantity = totalSeatsQuantity;
    }

    public Integer[] getSeats() {
        return seats;
    }

    public void setSeats(Integer[] seats) {
        this.seats = seats;
    }
}
