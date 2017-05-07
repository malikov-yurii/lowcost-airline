package com.malikov.lowcostairline.service.impl;

import com.malikov.lowcostairline.model.User;
import com.malikov.lowcostairline.repository.IUserRepository;
import com.malikov.lowcostairline.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author Yurii Malikov
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository repository;

    @Override
    public User save(User user) {
        Assert.notNull(user, "user should not be null");
        // TODO: 5/5/2017 prepare user to save
        return repository.save(user);
    }

    @Override
    public void update(User user) {
        // TODO: 5/5/2017 get rid of message  duplicating and prepare to save user
        Assert.notNull(user, "user should not be null");
        repository.save(user);

    }

    @Override
    public User get(long id) {
        // TODO: 5/5/2017 check not found with id
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    // TODO: 5/5/2017 Check not found with id
    @Override
    public void delete(long id) {
        repository.delete(id);
    }
}
