package com.chern.repo;


import com.chern.model.Quest;
import com.chern.model.Tag;
import com.chern.model.builder.TagBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class TagRepositoryCriteria implements TagRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final EntityManager entityManager;

//    @Override
//    public List<Tag> getByQuestId(long id) {
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
//        Root<Quest> questRoot = query.from(Quest.class);
//        CriteriaQuery<Quest> questById = query.select(questRoot)
//                .where(criteriaBuilder.equal(questRoot.get("id"), id));
//        TypedQuery<Quest> result = entityManager.createQuery(questById);
//        return result.getSingleResult().getTags();
//    }
//
//    @Override
//    public Boolean existsByQuestId(long id) {
//        String query = "select exists (select 1 from quest_tag where quest_id=?)";
//        Boolean aBoolean = jdbcTemplate.queryForObject(query, Boolean.class, id);
//        return aBoolean;
//    }

    @Override
    public List<Tag> save(List<Tag> tags) throws DuplicateKeyException {
        tags.forEach(tag -> entityManager.persist(tag));
        return tags;
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
    public List<Tag> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tag> query = criteriaBuilder.createQuery(Tag.class);
        Root<Tag> from = query.from(Tag.class);
        CriteriaQuery<Tag> selectAll = query.select(from);
        TypedQuery<Tag> result = entityManager.createQuery(selectAll);
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

}
