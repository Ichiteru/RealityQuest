package com.chern.config;

import com.chern.repo.UserRepository;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token = header.split(" ")[1].trim();
        AccessToken token1 = new AccessToken();
        try {
            token1 = TokenVerifier.create(token, AccessToken.class).getToken();
        } catch (VerificationException e) {
            e.printStackTrace();
        }
        Set<String> roles = token1.getResourceAccess("postman-client-password-grant")
                .getRoles();
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                token1.getPreferredUsername(),
                "password",
                roles
                        .stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList()));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()
        );

        authenticationToken.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
