package com.chern.repo;

import com.chern.model.Quest;
import com.chern.model.builder.QuestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class QuestRepositoryPostgres implements QuestRepository {

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager entityManager;

    @Override
    public Quest save(Quest quest) {
        entityManager.persist(quest);
        return quest;
    }

    @Override
    public Quest getById(long id) throws EmptyResultDataAccessException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
        Root<Quest> quest = query.from(Quest.class);
        Predicate idPredicate = criteriaBuilder.equal(quest.get("id"), id);
        query.where(idPredicate);
        TypedQuery<Quest> questById = entityManager.createQuery(query);
        return questById.getSingleResult();
    }

    @Override
    public List<Quest> getAll(int page, int size) throws EmptyResultDataAccessException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
        Root<Quest> questRoot = query.from(Quest.class);
        CriteriaQuery<Quest> select = query.select(questRoot);
        TypedQuery<Quest> pagination = entityManager.createQuery(select);
        pagination.setFirstResult(page);
        pagination.setMaxResults(size);
        return pagination.getResultList();
    }

    @Override
    public Quest update(Quest quest) {
        return entityManager.merge(quest);
    }

    @Override
    public Boolean existsById(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Quest> query = criteriaBuilder.createQuery(Quest.class);
        Root<Quest> from = query.from(Quest.class);
        Subquery<Quest> subquery = query.subquery(Quest.class);
        Root<Quest> subqueryFrom = subquery.from(Quest.class);

        subquery.select(subqueryFrom)
                .where(criteriaBuilder
                        .equal(subqueryFrom.get("id"), id));

        query.select(from).where(criteriaBuilder.exists(subquery));
        TypedQuery<Quest> result = entityManager.createQuery(query);
        return result.getResultList().isEmpty() ? false : true;
    }

    @Override
    public long deleteById(long id){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Quest> criteriaDelete = criteriaBuilder.createCriteriaDelete(Quest.class);
        Root<Quest> from = criteriaDelete.from(Quest.class);
        criteriaDelete.where(criteriaBuilder.equal(from.get("id"), id));
        int i = entityManager.createQuery(criteriaDelete).executeUpdate();
        return i;
    }

    @Override
    public int delete(List<Long> ids) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaDelete<Quest> criteriaDelete = criteriaBuilder.createCriteriaDelete(Quest.class);
        Root<Quest> from = criteriaDelete.from(Quest.class);
        criteriaDelete.where(from.get("id").in(ids));
        entityManager.createQuery(criteriaDelete).executeUpdate();
        return entityManager.createQuery(criteriaDelete).executeUpdate();
    }

    @Override
    public List<Quest> searchByParams(String query){
        List<Quest> quests = jdbcTemplate.queryForObject(query, new RowMapper<List<Quest>>() {
            @Override
            public List<Quest> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Quest> quests = new ArrayList<>();
                do {
                    quests.add(
                            QuestBuilder.aQuest()
                                    .withId(rs.getLong("id"))
                                    .withName(rs.getString("name"))
                                    .withGenre(rs.getString("genre"))
                                    .withPrice(rs.getDouble("price"))
                                    .withDuration(rs.getTime("duration").toLocalTime())
                                    .withMaxPeople(rs.getInt("max_people")).build());
                } while (rs.next());
                return quests;
            }
        });
        return quests;
    }
}
