package com.chern.model.builder;

import com.chern.model.Order;
import com.chern.model.Quest;
import com.chern.model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

public final class OrderBuilder {
    private User user;
    private Quest quest;
    private double cost;
    private LocalDateTime purchaseTime;
    private LocalDateTime reserveTime;
    private LocalDateTime endTime;

    private OrderBuilder() {
    }

    public static OrderBuilder anOrder() {
        return new OrderBuilder();
    }

    public OrderBuilder withUser(User user) {
        this.user = user;
        return this;
    }

    public OrderBuilder withQuest(Quest quest) {
        this.quest = quest;
        return this;
    }

    public OrderBuilder withCost(double cost) {
        this.cost = cost;
        return this;
    }

    public OrderBuilder withPurchaseTime(LocalDateTime purchaseTime) {
        this.purchaseTime = purchaseTime;
        return this;
    }

    public OrderBuilder withReserveTime(LocalDateTime reserveTime) {
        this.reserveTime = reserveTime;
        return this;
    }

    public OrderBuilder withEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    public Order build() {
        Order order = new Order();
        order.setUser(user);
        order.setQuest(quest);
        order.setCost(cost);
        order.setPurchaseTime(purchaseTime);
        order.setReserveTime(reserveTime);
        order.setEndTime(endTime);
        return order;
    }
}
