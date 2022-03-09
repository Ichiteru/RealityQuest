package com.chern.controller;

import com.chern.model.Order;
import com.chern.model.Quest;
import com.chern.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderRESTController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity save(@RequestParam long questId){
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = token.getClaim("preferred_username");
        Order order = orderService.save(username, questId);
        return ResponseEntity.created(null).body(order);
    }
}
