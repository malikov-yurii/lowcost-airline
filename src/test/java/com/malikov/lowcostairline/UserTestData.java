package com.malikov.lowcostairline;

import com.malikov.lowcostairline.matchers.ModelMatcher;
import com.malikov.lowcostairline.model.Role;
import com.malikov.lowcostairline.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;


/**
 * @author Yurii Malikov
 */
public class UserTestData {

    private static final Logger LOG = LoggerFactory.getLogger(UserTestData.class);

    public static final Role ROLE_USER = new Role(1L, "USER");
    public static final Role ROLE_ADMIN = new Role(2L, "ADMIN");

    public static final User USER_1_EDUARDOV = new User(1L,"Eduard", "Eduardov", "eduardov@gmail.com", "1111", "+380671234567", ROLE_USER);
    public static final User USER_2_IVANOV = new User(2L,"Ivan", "Ivanov", "ivanov@gmail.com", "1111", "+380661234567", ROLE_USER);
    public static final User USER_3_PETROV = new User(3L,"Petr", "Petrov", "petrov@gmail.com", "1111", "+380911234567", ROLE_USER);
    public static final User USER_4_IBRAGIM = new User(4L, "Ibragim", "Ibragimov", "ibragimov@gmail.com", "1111", "+380921234567", ROLE_USER);
    public static final User USER_5_VICTOROV = new User(5L, "Victor", "Victorov", "victorov@gmail.com", "1111", "+380931234567", ROLE_USER);
    public static final User USER_6_ROBERTOV = new User(6L, "Robert", "Black", "black@gmail.com", "1111", "+380941234567", ROLE_USER);
    public static final User ADMIN_1_WANG = new User(7L, "Hong", "Wang", "wang@gmail.com", "1111", "+380951234567", ROLE_USER, ROLE_ADMIN);
    public static final User ADMIN_2_KUMAR = new User(8L,"Abu", "Kumar", "kumar@gmail.com", "1111", "+380961234567", ROLE_USER, ROLE_ADMIN);

    public static final ArrayList<User> USERS = new ArrayList<>(Arrays.asList(USER_1_EDUARDOV, USER_2_IVANOV, USER_3_PETROV, USER_4_IBRAGIM,
            USER_5_VICTOROV, USER_6_ROBERTOV, ADMIN_1_WANG, ADMIN_2_KUMAR));

    public static final ModelMatcher<User> MATCHER = ModelMatcher.of(User.class,
            (expected, actual) -> expected == actual ||
                    (
                            Objects.equals(expected.getId(), actual.getId())
                            && Objects.equals(expected.getName(), actual.getName())
                            && Objects.equals(expected.getLastName(), actual.getLastName())
                            && Objects.equals(expected.getEmail(), actual.getEmail())
                                    // TODO: 5/5/2017 change password
                            && Objects.equals(expected.getPassword(), actual.getPassword())
                            && Objects.equals(expected.getPhoneNumber(), actual.getPhoneNumber())
                            && Objects.equals(expected.getRoles(), actual.getRoles())
                    )
    );

    // TODO: 5/5/2017 implement comparePassword

}
