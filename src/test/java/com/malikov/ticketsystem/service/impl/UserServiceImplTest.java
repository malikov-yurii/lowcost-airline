package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.service.AbstractServiceTest;

/**
 * @author Yurii Malikov
 */
public class UserServiceImplTest extends AbstractServiceTest {
/*

    @Autowired
    protected IUserService service;

    @Test
    public void create() throws Exception {
        User newUser = getNewDummyUserWithNullId(null);
        User created = service.create(newUser);
        newUser.setId(created.getId());
        MATCHER.assertCollectionEquals(getTestDataUsersWith(newUser), service.getAll());
    }

    //@Test
    //public void update() throws Exception {
    //    User updated = new User(USER_1);
    //    updated.setName("NewName");
    //    updated.setLastName("NewLastName");
    //    service.update(updated);
    //    MATCHER.assertEquals(updated, service.get(updated.getId()));
    //}

    @Test
    public void get() throws Exception {
        User user = service.get(USER_1.getId());
        MATCHER.assertEquals(USER_1, user);
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(USERS, service.getAll());
    }

    public void delete() throws Exception {
        service.delete(USER_2.getId());
        List<User> all = new ArrayList<>(USERS);
        all.remove(USER_2);
        MATCHER.assertCollectionEquals(all, service.getAll());
    }

    private User getNewDummyUserWithNullId(Long id) {
        return new User(id, "newName", "newLastName", "new@gmail.com", "newPassword", "+380501122334");
    }

    private ArrayList<User> getTestDataUsersWith(User newUser) {
        ArrayList<User> usersWithNewUser = new ArrayList<>(USERS);
        usersWithNewUser.add(newUser);
        return usersWithNewUser;
    }

    @Test
    public void getByEmail() throws Exception {
        User userByEmail = service.getByEmail(USER_7.getEmail());
        MATCHER.assertEquals(USER_7, userByEmail);
    }
*/

}