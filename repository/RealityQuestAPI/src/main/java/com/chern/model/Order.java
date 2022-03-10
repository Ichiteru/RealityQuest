package com.chern.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quest_user")
@EqualsAndHashCode(exclude = {"quest", "user", "tags"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")

    @JsonIgnoreProperties("orders")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "quest_id")
    @JsonIgnoreProperties("orders")
    private Quest quest;

    @Column(name = "order_cost")
    private double cost;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @Column(name = "reserve_time")
    private LocalDateTime reserveTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;
}
