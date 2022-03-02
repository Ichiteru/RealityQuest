package com.chern.config;

import com.chern.model.User;
import com.chern.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByUsername = userRepository.findUserByUsername(username);

        if (userByUsername.isEmpty()){
            throw new UsernameNotFoundException(String.format("User %s not found", username));
        }

        User userFromDB = userByUsername.get();

        return new org.springframework.security.core.userdetails.User(
                userFromDB.getUsername(),
                null,
                userFromDB.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList()));
    }
}
