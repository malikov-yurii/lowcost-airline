package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.User;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IUserService{

    User save(User user);

    User update(UserDTO userDTO);

    User get(long id);

    List<User> getAll();

    boolean delete(long id);
    // TODO: 5/14/2017  throws NotFoundException?
    User getByEmail(String email);

    List<String> getEmailsBy(String emailMask);

    List<User> getByLastName(String lastName);

    List<String> getLastNamesBy(String lastNameMask);
}
