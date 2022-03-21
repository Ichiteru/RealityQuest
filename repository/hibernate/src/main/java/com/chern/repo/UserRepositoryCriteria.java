package com.chern.repo;

import com.chern.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryCriteria implements UserRepository{

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<User> findUserByUsername(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);

        Root<User> userRoot = query.from(User.class);
        Predicate idPredicate = criteriaBuilder.equal(userRoot.get("username"), username);
        query.where(idPredicate);
        TypedQuery<User> userByUsername = entityManager.createQuery(query);
        return Optional.of(userByUsername.getSingleResult());
    }

    @Override
    public boolean existsByName(String preferredUsername) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Subquery<User> subquery = query.subquery(User.class);
        Root<User> userRoot = subquery.from(User.class);

        subquery.select(userRoot)
                .where(criteriaBuilder
                        .equal(userRoot.get("username"), preferredUsername));

        query.select(root)
                .where(criteriaBuilder.exists(subquery));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList().isEmpty() ? false : true;
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }
}
