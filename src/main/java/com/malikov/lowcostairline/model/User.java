package com.malikov.lowcostairline.model;

/**
 * @author Yurii Malikov
 */
public class User extends NamedEntity{

    private String lastName;

    private String email;

    private String password;

    private String phoneNumber;

    public User(long id, String name, String lastName, String email, String password, String phoneNumber) {
        super(id, name);
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(String name, String lastName, String email, String password, String phoneNumber) {
        super(name);
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(String lastName, String email, String password, String phoneNumber) {
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public User(long id, String name, String lastName, String email, String phoneNumber) {
        super(id, name);
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

}
