package com.malikov.ticketsystem.repository;

import com.malikov.ticketsystem.model.User;

/**
 * @author Yurii Malikov
 */
public interface IUserRepository extends IGenericRepository<User> {

    /**
     * @return User loaded by email or null if not found
     */
    User getByEmail(String email);
}
