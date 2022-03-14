package com.chern.service;

import com.chern.model.User;
import com.chern.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean existsByUsername(String username){
        return userRepository.existsByName(username);
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
}
