package com.chern.repo;

import com.chern.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCriteria implements OrderRepository{

    private final EntityManager entityManager;

    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }
}
