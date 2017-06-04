package com.malikov.ticketsystem;

import com.malikov.ticketsystem.model.User;
import com.malikov.ticketsystem.dto.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private String fullName;

    // TODO: 6/1/2017 do i really need field id?
    private Long id;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), true, true, true, true, user.getRoles());
        this.id = user.getId();
        this.fullName = user.getName() + ' ' + user.getLastName();
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        // TODO: 6/3/2017 Why principal is instanceof AuthorizedUser???
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "Not authorized user found");
        return user;
    }

    public static long id() {
        return get().id;
    }

    public void update(UserDTO newTo) {
        fullName = newTo.getFirstName() + ' ' + newTo.getLastName();
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "AuthorizedUser{" +
                "id=" + id +
                ", email=" + getUsername() +
                ", fullName=" + getFullName() +
                '}';
    }
}
