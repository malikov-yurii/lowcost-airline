package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.util.exception.NotFoundException;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IUserService{

    /**
     * @return new saved user with role USER_ROLE
     */
    User create(UserDTO userDTO);

    /**
     * Updates user by userDTO
     * @throws NotFoundException if not found with id
     */
    void update(UserDTO userDTO) throws NotFoundException;

    /**
     * @return user found by userId
     * @throws NotFoundException if not found with id
     */
    User get(long userId) throws NotFoundException;

    /**
     * Remove user by userId
     * @throws NotFoundException if not found with id
     */
    void delete(long userId) throws NotFoundException;

    /**
     * @return user found by email
     * @throws NotFoundException in not found with email
     */
    User getByEmail(String email) throws NotFoundException;

    /**
     * @return user emails found by emailMask
     */
    List<String> getEmailsByMask(String emailMask);

    /**
     * @return list of users found by last name
     */
    List<User> getByLastName(String lastName);

    /**
     * @return list of user last names found by lastNameMask
     */
    List<String> getLastNamesByMask(String lastNameMask);
}
