package com.model.builder;

import com.model.User;

public class UserBuilder {
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String password;

    public static UserBuilder create() {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.setRole("USER");
        return userBuilder;
    }

    public UserBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setRole(String role) {
        this.role = role;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        User user = new User();
        user.setEmail(this.email);
        user.setFirstName(this.firstName);
        user.setLastName(this.lastName);
        user.setRole(this.role);
        user.setPassword(this.password);
        return user;
    }
}
