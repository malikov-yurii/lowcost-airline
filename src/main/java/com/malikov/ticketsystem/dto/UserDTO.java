package com.malikov.ticketsystem.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import javax.validation.constraints.Size;

/**
 * @author Yurii Malikov
 */
public class UserDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @SafeHtml
    private String firstName;

    @NotBlank
    @SafeHtml
    private String lastName;

    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Size(min = 4, max = 64, message = " password must be between 4 and 64 characters")
    @SafeHtml
    private String password;

    @NotBlank
    private String phoneNumber;


    public UserDTO() {
    }

    public UserDTO(Long id, String firstName, String lastName, String email, String password, String phoneNumber) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

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


    // TODO: 5/14/2017 get set password special actions for security?
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
