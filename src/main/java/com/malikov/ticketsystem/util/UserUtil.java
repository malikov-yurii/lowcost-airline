package com.malikov.ticketsystem.util;

import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.User;

/**
 * @author Yurii Malikov
 */
public class UserUtil {

    public static User createNewFromDTO(UserDTO newUser) {

        // TODO: 5/14/2017 Get rid of new Role(USER) !!!!!!!!!!
        return new User(null, newUser.getFirstName(), newUser.getLastName(), newUser.getEmail().toLowerCase(), newUser.getPassword(), newUser.getPhoneNumber());
    }

    // TODO: 5/14/2017 Is it ok dto pass "" as password ??????????????
    public static UserDTO asTo(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getEmail(), "", user.getPhoneNumber());
    }

    public static User updateFromTo(User user, UserDTO userDTO) {
        user.setName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getPassword() != null) {
            user.setPassword(userDTO.getPassword());
        }
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;

    }

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }

}
