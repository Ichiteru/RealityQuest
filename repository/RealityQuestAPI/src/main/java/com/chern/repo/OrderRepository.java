package com.chern.repo;

import com.chern.model.Order;

public interface OrderRepository {

    Order save(Order order);
}
