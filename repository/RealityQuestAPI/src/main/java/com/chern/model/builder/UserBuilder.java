package com.chern.model.builder;

import com.chern.model.Order;
import com.chern.model.User;

import java.util.Set;

public final class UserBuilder {
    private long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private Set<Order> orders;

    private UserBuilder() {
    }

    public static UserBuilder anUser() {
        return new UserBuilder();
    }

    public UserBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withOrders(Set<Order> orders) {
        this.orders = orders;
        return this;
    }

    public User build() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setOrders(orders);
        return user;
    }
}
