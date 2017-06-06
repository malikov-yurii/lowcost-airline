package com.malikov.ticketsystem.model;

import com.malikov.ticketsystem.util.validator.PhoneNumber;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Yurii Malikov
 */
@SuppressWarnings("JpaQlInspection")
@Entity
// TODO: 6/6/2017 why do i need constraint description here
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = "email",
                                                              name = "users_unique_email_idx"))
public class User extends NamedEntity {

    @NotBlank
    @Column(name = "last_name", nullable = false)
    @SafeHtml
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Please provide a valid email address")
    @NotBlank
    @SafeHtml
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Length(min = 4)
    @SafeHtml
    private String password;

    @NotBlank
    @PhoneNumber
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Ticket> tickets;


    public User() {}

    public User(Long id, String name, String lastName, String email, String password, String phoneNumber) {
        super(id, name);
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    //public User(String lastName, String email, String password, String phoneNumber, Set<Role> roles) {
    //    this.lastName = lastName;
    //    this.email = email;
    //    this.password = password;
    //    this.phoneNumber = phoneNumber;
    //    this.roles = roles;
    //}

    public User(String name, String lastName, String email, String password, String phoneNumber, Set<Role> roles) {
        super(name);
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = roles;
    }

    public User(Long id, String name, String lastName, String email, String password, String phoneNumber, Role... roles) {
        super(id, name);
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.roles = new HashSet<>(Arrays.asList(roles));
    }

    //public User(Long id, String name, String lastName, String email, String password, String phoneNumber, Set<Role> roles) {
    //    super(id, name);
    //    this.lastName = lastName;
    //    this.email = email;
    //    this.password = password;
    //    this.phoneNumber = phoneNumber;
    //    this.roles = roles;
    //}

    //public User(User user) {
    //    super(user.getId(), user.getName());
    //    this.lastName = user.getLastName();
    //    this.email = user.getEmail();
    //    this.password = user.getPassword();
    //    this.phoneNumber = user.getPhoneNumber();
    //    this.roles = user.getRoles();
    //}

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(user.phoneNumber) : user.phoneNumber != null) return false;
        return roles != null ? roles.equals(user.roles) : user.roles == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", name='" + getName() + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                '}';
    }
}
