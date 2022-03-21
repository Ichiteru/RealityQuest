package com.chern.controller;

import com.chern.dto.NewOrderDTO;
import com.chern.dto.TabularOrderDTO;
import com.chern.dto.converter.Converter;
import com.chern.model.Order;
import com.chern.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class OrderRESTController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @PostMapping("/orders")
    public ResponseEntity save(@RequestBody NewOrderDTO info,
                               @AuthenticationPrincipal Jwt token){
        String username = token.getClaim("preferred_username");
        TabularOrderDTO order = orderService.save(username, info.getQuestId(),info.getReservationTime());
        return ResponseEntity.created(null).body(order);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders")
    public ResponseEntity getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size){
        List<TabularOrderDTO> orders = orderService.getAll(page, size);
        return ResponseEntity.ok().body(orders);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_OWNER', 'ROLE_USER')")
    @GetMapping("/orders/{id}")
    public ResponseEntity getById(@PathVariable long id){
        return ResponseEntity.ok(orderService.getById(id));
    }
}
