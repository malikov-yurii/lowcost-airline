package com.malikov.ticketsystem;

/**
 * @author Yurii Malikov
 */
public interface IHasId {

    Long getId();

    void setId(Long id);

    default boolean isNew(){
        return getId() == null || getId() == 0;
    }

}
