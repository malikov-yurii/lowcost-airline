package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.repository.IRoleRepository;
import com.malikov.ticketsystem.repository.IUserRepository;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.UserUtil;
import com.malikov.ticketsystem.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.malikov.ticketsystem.util.UserUtil.prepareToSave;
import static com.malikov.ticketsystem.util.UserUtil.updateFromTo;
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
    public User get(long userId) {
        return checkNotFoundWithId(userRepository.get(userId), userId);
    }

    @Override
    public User create(UserDTO userDTO) {
        ValidationUtil.checkNew(userDTO);
        User user = UserUtil.createNewFromDTO(userDTO);
        user.setRoles(Collections.singleton(roleRepository.getByName("ROLE_USER")));
        return userRepository.save(prepareToSave(user));
    }

    @Override
    public void update(UserDTO userDTO) {
        ValidationUtil.checkNotNew(userDTO);
        User user = updateFromTo(get(userDTO.getId()), userDTO);
        userRepository.save(prepareToSave(user));
    }

    @Override
    public List<User> getByLastName(String lastName) {
        return userRepository.getByLastName(lastName);
    }

    @Override
    public void delete(long userId) {
        ValidationUtil.checkSuccess(userRepository.delete(userId), "not found user with id" + userId);
    }

    @Override
    public User getByEmail(String email) {
        return ValidationUtil.checkSuccess(userRepository.getByEmail(email), "email=" + email);
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
    public List<String> getEmailsByMask(String emailMask) {
        return userRepository.getByEmailMask(emailMask);
    }

    @Override
    public List<String> getLastNamesByMask(String lastNameMask) {
        return userRepository.getLastNamesBy(lastNameMask);
    }
}
