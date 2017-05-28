package com.malikov.ticketsystem;

import com.malikov.ticketsystem.matchers.ModelMatcher;
import com.malikov.ticketsystem.model.Role;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.util.PasswordUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


/**
 * @author Yurii Malikov
 */
public class UserTestData {

    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final Role ROLE_USER = new Role(1L, "ROLE_USER");

    public static final Role ROLE_ADMIN = new Role(2L, "ROLE_ADMIN");

    public static final User USER_1 = new User(1L, "Eduard", "Eduardov",
            "user@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380671234567", ROLE_USER);

    public static final User USER_2 = new User(2L, "Ivan", "Ivanov",
            "ivanov@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380661234567", ROLE_USER);

    public static final User USER_3 = new User(3L, "Petr", "Petrov",
            "petrov@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380911234567", ROLE_USER);

    public static final User USER_4 = new User(4L, "Ibragim", "Ibragimov",
            "ibragimov@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380921234567", ROLE_USER);

    public static final User USER_5 = new User(5L, "Victor", "Victorov",
            "victorov@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380931234567", ROLE_USER);

    public static final User USER_6 = new User(6L, "Robert", "Black",
            "black@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380941234567", ROLE_USER);

    public static final User USER_7 = new User(7L, "Hong", "Wang",
            "hong@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380951234567", ROLE_USER);

    public static final User ADMIN = new User(8L, "Vsevolod", "Vsevlastov",
            "admin@gmail.com", "$2a$11$bRQR2FxnBrKnr/PS0eaDUeEQzO2ZtYJllGPIkdekZ0q6rJVJrCmXm", "+380961234567", ROLE_USER, ROLE_ADMIN);

    public static final List USERS = Arrays.asList(USER_1, USER_2, USER_3, USER_4,
            USER_5, USER_6, USER_7, ADMIN);


    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual || (
                    Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getLastName(), actual.getLastName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                            && comparePassword(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getPhoneNumber(), actual.getPhoneNumber())
                            && Objects.equals(expected.getRoles(), actual.getRoles())));

    private static boolean comparePassword(String rawOrEncodedPassword, String password) {
        if (PasswordUtil.isEncoded(rawOrEncodedPassword)) {
            return rawOrEncodedPassword.equals(password);
        } else if (!PasswordUtil.isMatch(rawOrEncodedPassword, password)) {
            LOG.error("Password " + password + " doesn't match encoded " + password);
            return false;
        }
        return true;
    }

}
