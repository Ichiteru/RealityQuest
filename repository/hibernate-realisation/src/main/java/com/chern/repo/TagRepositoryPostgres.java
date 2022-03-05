package com.chern.repo;


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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class TagRepositoryPostgres implements TagRepository {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final EntityManager entityManager;

    @Override
    public List<Tag> getByQuestId(long id) {
        String query = "select id, name  from quest_tag " +
                "inner join tag t on t.id = quest_tag.tag_id " +
                "where quest_id=?";
        List<Tag> tags = jdbcTemplate.queryForObject(query, new RowMapper<List<Tag>>() {
            @Override
            public List<Tag> mapRow(ResultSet rs, int rowNum) throws SQLException {
                    List<Tag> tags = new ArrayList<>();
                    do {
                        Tag tag = TagBuilder.aTag()
                                .withId(rs.getLong("id"))
                                .withName(rs.getString("name")).build();
                        tags.add(tag);
                    } while (rs.next());
                    return tags;
            }
        }, id);
        return tags;
    }

    @Override
    public Boolean existsByQuestId(long id) {
        String query = "select exists (select 1 from quest_tag where quest_id=?)";
        Boolean aBoolean = jdbcTemplate.queryForObject(query, Boolean.class, id);
        return aBoolean;
    }

    @Override
    public List<Tag> save(List<Tag> tags) throws DuplicateKeyException {
        tags.forEach(tag -> entityManager.persist(tag));
        return tags;
    }

    @Override
    public Tag getById(long id) {
        String query = "select id, name from tag where id =?";
        Tag tag = jdbcTemplate.queryForObject(query, new RowMapper<Tag>() {

            @Override
            public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
                return TagBuilder.aTag()
                        .withId(rs.getLong("id"))
                        .withName(rs.getString("name")).build();
            }
        }, id);
        return tag;
    }

    @Override
    public List<Tag> getAll() {
        String query = "select id,name from tag";
        List<Tag> tags = jdbcTemplate.queryForObject(query, new RowMapper<List<Tag>>() {
            @Override
            public List<Tag> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Tag> tags = new ArrayList<>();
                do {
                    Tag tag = TagBuilder.aTag()
                            .withId(rs.getLong("id"))
                            .withName(rs.getString("name")).build();
                    tags.add(tag);
                } while (rs.next());
                return tags;
            }
        });
        return tags;
    }

    @Override
    public boolean existsById(long id) {
        String query = "select exists (select 1 from tag where id=?)";
        Boolean isExists = jdbcTemplate.queryForObject(query, Boolean.class, id);
        return isExists;
    }

    @Override
    public long deleteById(long id) {
        String query = "delete from tag where id=?";
        jdbcTemplate.update(query, id);
        return id;
    }

    @Override
    public int delete(List<Long> ids) {
        String query = "delete from tag where id in(:ids)";
        Map namedParams = Collections.singletonMap("ids", ids);
        return namedParameterJdbcTemplate.update(query, namedParams);
    }

}
