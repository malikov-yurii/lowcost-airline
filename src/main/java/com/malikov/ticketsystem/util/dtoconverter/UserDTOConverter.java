package com.malikov.ticketsystem.util.dtoconverter;

import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.util.PasswordUtil;

/**
 * @author Yurii Malikov
 */
public class UserDTOConverter {

    public static User createNewFromDTO(UserDTO newUser) {
        return new User(null, newUser.getFirstName(), newUser.getLastName(), newUser.getEmail().toLowerCase(),
                newUser.getPassword(), newUser.getPhoneNumber());
    }

    public static UserDTO asTo(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getLastName(), user.getEmail(), "",
                user.getPhoneNumber());
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
