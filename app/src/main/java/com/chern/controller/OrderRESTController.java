package com.chern.controller;

import com.chern.dto.NewOrderDto;
import com.chern.dto.TabularOrderDto;
import com.chern.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderRESTController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @PostMapping("/orders")
    public ResponseEntity save(@RequestBody NewOrderDto info,
                               @AuthenticationPrincipal Jwt token){
        String username = token.getClaim("preferred_username");
        TabularOrderDto order = orderService.save(username, info.getQuestId(),info.getReservationTime());
        return ResponseEntity.created(null).body(order);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders")
    public ResponseEntity getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size){
        List<TabularOrderDto> orders = orderService.getAll(page, size);
        return ResponseEntity.ok().body(orders);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders/{id}")
    public ResponseEntity getById(@PathVariable long id){
        return ResponseEntity.ok(orderService.getById(id));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders/user/{userId}")
    public ResponseEntity getUserReservations(@PathVariable long userId){
        List<TabularOrderDto> reservations = orderService.getUserReservations(userId);
        return ResponseEntity.ok(reservations);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER')")
    @DeleteMapping("/orders/{id}")
    public ResponseEntity deleteById(@PathVariable long id){
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
