package com.chern.config;

import com.chern.model.User;
import com.chern.model.builder.UserBuilder;
import com.chern.repo.UserRepository;
import com.chern.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.keycloak.TokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserService userService;
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

            // need to save user in db if new
            if (!userService.existsByUsername(keyCloakToken.getPreferredUsername())){
                User user = UserBuilder.anUser()
                        .withName(keyCloakToken.getGivenName())
                        .withSurname(keyCloakToken.getFamilyName())
                        .withEmail(keyCloakToken.getEmail())
                        .withUsername(keyCloakToken.getPreferredUsername()).build();
                userService.save(user);
            }
        }

        filterChain.doFilter(request, response);
    }
}
