package com.chern.config;

import com.chern.model.User;
import com.chern.service.UserService;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null){
            final String token = header.split(" ")[1].trim();
            AccessToken keyCloakToken = new AccessToken();
            try {
                keyCloakToken = TokenVerifier.create(token, AccessToken.class).getToken();
            } catch (VerificationException e) {
                e.printStackTrace();
            }

            if (!userService.existsByUsername(keyCloakToken.getPreferredUsername())){
                User user = User.builder()
                        .name(keyCloakToken.getGivenName())
                        .surname(keyCloakToken.getFamilyName())
                        .email(keyCloakToken.getEmail())
                        .username(keyCloakToken.getPreferredUsername()).build();
                userService.save(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}
