package com.chern.service;

import com.chern.model.User;
import com.chern.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean existsByUsername(String username){
        return userRepository.existsByName(username);
    }

    @Transactional
    public void save(User user){
        userRepository.save(user);
    }
}
