package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.repository.IRoleRepository;
import com.malikov.ticketsystem.repository.IUserRepository;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

import static com.malikov.ticketsystem.util.UserUtil.prepareToSave;
import static com.malikov.ticketsystem.util.UserUtil.updateFromTo;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFound;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFoundWithId;

/**
 * @author Yurii Malikov
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public User save(User user) {
        Assert.notNull(user, "user should not be null");
        // TODO: 6/3/2017 get rid of hardcoded + how dto pass reference dto role by name?
        user.setRoles(Collections.singleton(roleRepository.getByName("ROLE_USER")));
        return userRepository.save(prepareToSave(user));
    }

    @Override
    public User update(UserDTO userDTO) {
        User user = updateFromTo(get(userDTO.getId()), userDTO);
        return userRepository.save(prepareToSave(user));
    }

    @Override
    public List<User> getByLastName(String lastName) {
        return userRepository.getByLastName(lastName);
    }

    @Override
    public User get(long id) {
        return checkNotFoundWithId(userRepository.get(id), id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public boolean delete(long id) {
        return userRepository.delete(id);
    }

    @Override
    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(userRepository.getByEmail(email), "email=" + email);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.getByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(u);
    }

    @Override
    public List<String> getEmailsBy(String emailMask) {
        return userRepository.getByEmailMask(emailMask);
    }

    @Override
    public List<String> getLastNamesBy(String lastNameMask) {
        return userRepository.getLastNamesBy(lastNameMask);
    }
}
