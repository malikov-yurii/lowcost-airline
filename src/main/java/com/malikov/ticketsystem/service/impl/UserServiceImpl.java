package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.repository.IUserRepository;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.malikov.ticketsystem.util.UserUtil.prepareToSave;
import static com.malikov.ticketsystem.util.UserUtil.updateFromTo;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFound;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Yurii Malikov
 */
@Service("userService")
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository repository;

    @Override
    public User save(User user) {
        Assert.notNull(user, "user should not be null");
        return repository.save(prepareToSave(user));
    }

    @Override
    public void update(UserTo userTo) {
        User user = updateFromTo(get(userTo.getId()), userTo);
        repository.save(prepareToSave(user));
    }

    @Override
    public User get(long id, String... hintNames) {
        return checkNotFoundWithId(repository.get(id, hintNames), id);
    }

    @Override
    public List<User> getAll() {
        return repository.getAll();
    }

    @Override
    public void delete(long id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = repository.getByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }

}
