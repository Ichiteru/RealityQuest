package com.chern.repo;

import com.chern.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findUserByUsername(String username);

    boolean existsByName(String preferredUsername);

    void save(User user);
}
