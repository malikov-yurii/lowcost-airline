package com.malikov.ticketsystem.service.impl;

import com.malikov.ticketsystem.AuthorizedUser;
import com.malikov.ticketsystem.dto.UserDTO;
import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.repository.IRoleRepository;
import com.malikov.ticketsystem.repository.IUserRepository;
import com.malikov.ticketsystem.service.IUserService;
import com.malikov.ticketsystem.util.ValidationUtil;
import com.malikov.ticketsystem.util.dtoconverter.UserDTOConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.malikov.ticketsystem.util.MessageUtil.getMessage;
import static com.malikov.ticketsystem.util.ValidationUtil.checkNotFound;
import static com.malikov.ticketsystem.util.dtoconverter.UserDTOConverter.prepareToSave;
import static com.malikov.ticketsystem.util.dtoconverter.UserDTOConverter.updateFromTo;

/**
 * @author Yurii Malikov
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService, UserDetailsService, MessageSourceAware {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private MessageSource messageSource;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public User get(long userId) {
        return checkNotFound(userRepository.get(userId),
                getMessage(messageSource, "exception.notFoundById") + userId);
    }

    @Override
    public User create(UserDTO userDTO) {
        ValidationUtil.checkNew(userDTO, getMessage(messageSource, "exception.mustBeNew"));
        User user = UserDTOConverter.createNewFromDTO(userDTO);
        user.setRoles(Collections.singleton(roleRepository.getByName("ROLE_USER")));
        return userRepository.save(prepareToSave(user));
    }

    @Override
    public void update(UserDTO userDTO) {
        ValidationUtil.checkNotNew(userDTO, getMessage(messageSource, "exception.mustBeNotNew"));
        User user = updateFromTo(get(userDTO.getId()), userDTO);
        userRepository.save(prepareToSave(user));
        LOG.info("{} updated.", userDTO);
    }

    @Override
    public List<User> getByLastName(String lastName) {
        return userRepository.getByLastName(lastName);
    }

    @Override
    public void delete(long userId) {
        checkNotFound(userRepository.delete(userId),
                getMessage(messageSource, "exception.notFoundById") + userId);
        LOG.info("User={} deleted.", userId);
    }

    @Override
    public User getByEmail(String email) {
        return checkNotFound(userRepository.getByEmail(email),
                getMessage(messageSource, "exception.notFoundByEmail") + email);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User u = userRepository.getByEmail(email.toLowerCase());
        if (u == null) {
            throw new UsernameNotFoundException(getMessage(messageSource,
                    "exception.notFoundByEmail") + email);
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

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
