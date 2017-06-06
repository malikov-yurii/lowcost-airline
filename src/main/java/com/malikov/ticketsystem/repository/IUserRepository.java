package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.User;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IUserRepository extends IGenericRepository<User> {

    /**
     * @return user loaded by email or null if not found
     */
    User getByEmail(String email);

    /**
     * @return list of user emails found by emailMask or empty list if not found any
     */
    List<String> getByEmailMask(String emailMask);

    /**
     * @return list of users found by last name or empty list if not found any
     */
    List<User> getByLastName(String lastName);

    /**
     * @return list of user last names found by last name mask
     */
    List<String> getLastNamesBy(String lastNameMask);
}
