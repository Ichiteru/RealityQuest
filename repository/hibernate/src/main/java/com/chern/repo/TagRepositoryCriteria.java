package com.chern.repo;


import com.chern.model.Order;
import com.chern.model.Tag;
import com.chern.model.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TagRepositoryCriteria implements TagRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Tag> save(List<Tag> tags) throws DuplicateKeyException{
        try {
        tags.forEach(tag -> entityManager.persist(tag));
        return tags;
        } catch (ConstraintViolationException exception){
            throw new DuplicateKeyException("Tag with this name already exists");
        }
    }

    @Override
    public Tag getById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        CriteriaQuery<Tag> byId = query.select(from)
                .where(criteriaBuilder.equal(from.get("id"), id));
        TypedQuery<Tag> result = entityManager.createQuery(byId);
        return result.getSingleResult();
    }

    @Override
    public List<Tag> getAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        CriteriaQuery<Tag> selectAll = query.select(from);
        TypedQuery<Tag> result = entityManager.createQuery(selectAll);
        result.setFirstResult(page);
        result.setMaxResults(size);
        return result.getResultList();
    }

    @Override
    public boolean existsById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        Subquery<Tag> subquery = query.subquery(Tag.class);
        Root<Tag> subqueryFrom = subquery.from(Tag.class);
        subquery.select(subqueryFrom)
                .where(criteriaBuilder.equal(subqueryFrom.get("id"), id));
        CriteriaQuery<Tag> existById = query.select(from).where(criteriaBuilder.exists(subquery));
        TypedQuery<Tag> result = entityManager.createQuery(existById);
        return result.getResultList().isEmpty() ? false : true;
    }

    @Override
    public long deleteById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> criteriaDelete = criteriaBuilder.createCriteriaDelete(Tag.class);
        Root<Tag> from = criteriaDelete.from(Tag.class);
        criteriaDelete.where(criteriaBuilder.equal(from.get("id"), id));
        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public int delete(List<Long> ids) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Tag> criteriaDelete = criteriaBuilder.createCriteriaDelete(Tag.class);
        Root<Tag> from = criteriaDelete.from(Tag.class);
        criteriaDelete.where(from.get("id").in(ids));
        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public Tag findMostUsedOfTopUser() {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Tag> tagQuery = builder.createQuery(Tag.class);
        Root<com.chern.model.Order> tagRoot = tagQuery.from(com.chern.model.Order.class);
        Join<com.chern.model.Order, Tag> orderTagName = tagRoot.join("quest")
                .join("tags");

        Subquery<User> userQuery = tagQuery.subquery(User.class);
        Root<com.chern.model.Order> userRoot = userQuery.from(com.chern.model.Order.class);

        Subquery<BigDecimal> sumQuery = userQuery.subquery(BigDecimal.class);
        Root<com.chern.model.Order> sumRoot = sumQuery.from(com.chern.model.Order.class);
        sumQuery.select(builder.sum(sumRoot.get("cost")))
                .groupBy(sumRoot.get("user"));

        userQuery.select(userRoot.get("user"))
                .groupBy(userRoot.get("user"))
                .having(builder.ge(builder.sum(userRoot.get("cost")), builder.all(sumQuery)));

        Subquery<Long> countQuery = tagQuery.subquery(Long.class);
        Root<com.chern.model.Order> countRoot = countQuery.from(com.chern.model.Order.class);
        Join<Order, Tag> countTagJoin = countRoot.join("quest")
                .join("tags");

        countQuery.select(builder.count(countTagJoin))
                .where(builder.in(countRoot.get("user")).value(userQuery))
                .groupBy(countTagJoin);

        tagQuery.select(orderTagName)
                .where(builder.in(tagRoot.get("user")).value(userQuery))
                .groupBy(orderTagName)
                .having(builder.ge(builder.count(orderTagName), builder.all(countQuery)));
        TypedQuery<Tag> result = entityManager.createQuery(tagQuery);
        List<Tag> resultList = result.getResultList();
        return resultList.stream()
                .findFirst()
                .orElse(null);

    }

    @Override
    public List<Tag> getByNames(List<String> names) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        CriteriaQuery<Tag> result = query.select(from).where(from.get("name").in(names));
        return entityManager.createQuery(query).getResultList();
    }
}
