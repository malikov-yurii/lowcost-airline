package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.model.Role;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.to.UserTo;

/**
 * @author Yurii Malikov
 */
public class UserUtil {

    public static User createNewFromTo(UserTo newUser) {

        // TODO: 5/14/2017 Get rid of new Role(USER) !!!!!!!!!!
        return new User(null, newUser.getFirstName(), newUser.getLastName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), newUser.getPhoneNumber(), new Role("USER"));
    }

    // TODO: 5/14/2017 Is it ok to pass "" as password ??????????????
    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getLastName(), user.getEmail(), "", user.getPhoneNumber());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getFirstName());
        user.setLastName(userTo.getLastName());
        user.setEmail(userTo.getEmail());
        user.setPassword(userTo.getPassword());
        user.setPhoneNumber(userTo.getPhoneNumber());
        return user;

    }

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

}
