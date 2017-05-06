package com.malikov.lowcostairline.service;

import com.malikov.lowcostairline.model.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;

import static com.malikov.lowcostairline.UserTestData.*;

/**
 * @author Yurii Malikov
 */
public class UserServiceImplTest extends AbstractServiceTest {

    @Autowired
    protected IUserService service;

    @Test
    public void save() throws Exception {
        User newUser = getNewDummyUserWithNullId(null);
        User created = service.save(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(getTestDataUsersWith(newUser), service.getAll());
    }

    @Test
    public void update() throws Exception {
        User updated = new User(USER_1_EDUARDOV);
        updated.setName("NewName");
        updated.setLastName("NewLastName");
        service.update(updated);
        MATCHER.assertEquals(updated, service.get(updated.getId()));
    }

    @Test
    public void get() throws Exception {
        User user = service.get(USER_1_EDUARDOV.getId());
        MATCHER.assertEquals(USER_1_EDUARDOV, user);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(USERS, service.getAll());
    }

    // TODO: 5/6/2017 How to test delete despite constraints? At first I sould delete tickets perhaps
    @Test(expected = PersistenceException.class)
    public void delete() throws Exception {
        service.delete(USER_2_IVANOV.getId());
        List<User> all = new ArrayList<>(USERS);
        all.remove(USER_2_IVANOV);
        MATCHER.assertCollectionEquals(all, service.getAll());
    }

    private User getNewDummyUserWithNullId(Long id) {
        return new User(id, "newName", "newLastName", "new@gmail.com:", "newPassword", "+380501122334");
    }

    private ArrayList<User> getTestDataUsersWith(User newUser) {
        ArrayList<User> usersWithNewUser = new ArrayList<>(USERS);
        usersWithNewUser.add(newUser);
        return usersWithNewUser;
    }

}