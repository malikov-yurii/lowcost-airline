package com.malikov.ticketsystem.dto;

import java.util.Arrays;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FreeSeatsDTO that = (FreeSeatsDTO) o;

        if (totalSeats != null ? !totalSeats.equals(that.totalSeats) : that.totalSeats != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(freeSeats, that.freeSeats);
    }

    @Override
    public int hashCode() {
        int result = totalSeats != null ? totalSeats.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(freeSeats);
        return result;
    }

    @Override
    public String toString() {
        return "FreeSeatsDTO{" +
                "totalSeats=" + totalSeats +
                ", freeSeats=" + Arrays.toString(freeSeats) +
                '}';
    }
}
