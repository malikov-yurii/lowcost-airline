package com.malikov.ticketsystem.service;

import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.dto.UserTo;

import java.util.List;

/**
 * @author Yurii Malikov
 */
public interface IUserService{

    User save(User user);

    void update(UserTo userTo);

    User get(long id, String... hintNames);

    List<User> getAll();

    void delete(long id);
    // TODO: 5/14/2017  throws NotFoundException?
    User getByEmail(String email);

    List<String> getEmailsByEmailMask(String emailMask);
}
