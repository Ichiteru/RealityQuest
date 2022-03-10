package com.chern.controller;

import com.chern.model.Order;
import com.chern.model.Quest;
import com.chern.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class OrderRESTController {

    private final OrderService orderService;

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @PostMapping("/orders")
    public ResponseEntity save(@RequestParam long questId,
                               @RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime reservationTime){
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = token.getClaim("preferred_username");
        Order order = orderService.save(username, questId,reservationTime);
        return ResponseEntity.created(null).body(order);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders")
    public ResponseEntity getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size){
        List<Order> orders = orderService.getAll(page, size);
        return ResponseEntity.ok().body(orders);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders/{id}")
    public ResponseEntity getById(@PathVariable long id){
        Optional<Order> order = orderService.getById(id);
        return ResponseEntity.ok(order);
    }
}
