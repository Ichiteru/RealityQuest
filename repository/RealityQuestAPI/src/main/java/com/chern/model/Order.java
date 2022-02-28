package com.chern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quest_user")
public class Order {

    @EmbeddedId
    private UserQuestKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("questId")
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @Column(name = "order_cost")
    private double cost;

    @Column(name = "purchase_time")
    private LocalDateTime purchaseTime;

    @Column(name = "reserve_time")
    private LocalTime reserveTime;
}
