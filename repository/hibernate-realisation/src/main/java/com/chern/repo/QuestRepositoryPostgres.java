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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final EntityManager entityManager;

    @Override
    public Quest save(Quest quest) {
        entityManager.persist(quest);
//        String query = "insert into quest (name, genre, price, description, duration, creation_date," +
//                " modification_date, max_people) " +
//                "values (?, ?, ?, ?, ?, ?, ?, ?) returning id";
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(con -> {
//            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            ps.setString(1, quest.getName());
//            ps.setString(2, quest.getGenre());
//            ps.setBigDecimal(3, BigDecimal.valueOf(quest.getPrice()));
//            ps.setString(4, quest.getDescription());
//            ps.setTime(5, Time.valueOf(quest.getDuration()));
//            ps.setDate(6, Date.valueOf(quest.getCreationDate()));
//            ps.setDate(7, Date.valueOf(quest.getModificationDate()));
//            ps.setInt(8, quest.getMaxPeople());
//            return ps;
//        }, keyHolder);
//        quest.setId(keyHolder.getKey().longValue());
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
//        String query = "select * from quest where id=?";
//        Quest quest = jdbcTemplate.queryForObject(query, new QuestRowMapper(), id);
//        return quest;
    }

    @Override
    public List<Quest> getAll() throws EmptyResultDataAccessException {
        String query = "select id, name, genre, price, duration, max_people from quest";
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

    @Override
    public Quest update(Quest quest) {
        String query = "update quest set name=?, genre=?, price=?, description=?, duration=?, modification_date=?, max_people=? " +
                "where id=?";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, quest.getName());
                ps.setString(2, quest.getGenre());
                ps.setBigDecimal(3, quest.getPrice());
                ps.setString(4, quest.getDescription());
                ps.setTime(5, Time.valueOf(quest.getDuration()));
                ps.setDate(6, Date.valueOf(quest.getModificationDate()));
                ps.setInt(7, quest.getMaxPeople());
                ps.setLong(8, quest.getId());
                return ps;
            }
        });
        return quest;
    }

    @Override
    public Boolean existsById(long id) {
        String query = "select exists (select 1 from quest where id=?)";
        Boolean aBoolean = jdbcTemplate.queryForObject(query, Boolean.class, id);
        return aBoolean;
    }

    @Override
    public long deleteById(long id){
        String query = "delete from quest where id=?";
        jdbcTemplate.update(query, id);
        return id;
    }

    @Override
    public int delete(List<Long> ids) {
        String query = "delete from quest where id in(:ids)";
        Map namedParams = Collections.singletonMap("ids", ids);
        return namedParameterJdbcTemplate.update(query, namedParams);
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
