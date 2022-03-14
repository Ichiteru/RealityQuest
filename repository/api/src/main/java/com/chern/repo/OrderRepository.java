package com.chern.repo;

import com.chern.model.Order;
import com.chern.model.Tag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    List<Order> getAll(int page, int size);
    Optional<Order> getById(long id);
    boolean isReservedAtThisTime(LocalDateTime time);
}
