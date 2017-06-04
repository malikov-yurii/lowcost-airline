package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.User;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IUserRepository extends IGenericRepository<User> {

    /**
     * @return User loaded by email or null if not found
     */
    User getByEmail(String email);

    List<String> getByEmailMask(String emailMask);

    List<User> getByLastName(String lastName);

    List<String> getLastNamesBy(String lastNameMask);
}
