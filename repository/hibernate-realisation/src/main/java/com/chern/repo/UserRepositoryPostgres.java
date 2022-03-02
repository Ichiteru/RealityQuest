package com.chern.repo;

import com.chern.model.Quest;
import com.chern.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryPostgres implements UserRepository{

    private final EntityManager entityManager;

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
}
