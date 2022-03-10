package com.chern.repo;

import com.chern.model.Order;
import com.chern.model.Tag;
import com.chern.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryCriteria implements OrderRepository {

    private final EntityManager entityManager;

    @Override
    public Order save(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public List<Order> getAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);
        CriteriaQuery<Order> select = query.select(orderRoot);
        TypedQuery<Order> pagination = entityManager.createQuery(select);
        pagination.setFirstResult(page);
        pagination.setMaxResults(size);
        return pagination.getResultList();
    }

    @Override
    public Optional<Order> getById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> order = query.from(Order.class);
        Predicate idPredicate = criteriaBuilder.equal(order.get("id"), id);
        query.where(idPredicate);
        TypedQuery<Order> orderById = entityManager.createQuery(query);
        return Optional.of(orderById.getSingleResult());
    }

    @Override
    public boolean isReservedAtThisTime(LocalDateTime time) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> mainSelect = criteriaBuilder.createQuery(Order.class);
        Root<Order> mainFrom = mainSelect.from(Order.class);
        Subquery<Order> subQuery = mainSelect.subquery(Order.class);
        Root<Order> fromSubQuery = subQuery.from(Order.class);
        Predicate less = criteriaBuilder.lessThanOrEqualTo(fromSubQuery.get("reserveTime"), time);
        Predicate more = criteriaBuilder.greaterThanOrEqualTo(fromSubQuery.get("endTime"), time);
        subQuery.select(fromSubQuery).where(criteriaBuilder.and(less, more));
        mainSelect.select(mainFrom).where(criteriaBuilder.exists(subQuery));
        TypedQuery<Order> result = entityManager.createQuery(mainSelect);
        List<Order> resultList = result.getResultList();
        return resultList.isEmpty() ? false : true;
    }

}
