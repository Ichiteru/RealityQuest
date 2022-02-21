package com.chern.repo;

import com.chern.model.Tag;
import com.chern.model.builder.TagBuilder;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TagRepositoryPostgres implements TagRepository{

    private final JdbcTemplate jdbcTemplate;

    public TagRepositoryPostgres(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

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
    public List<Tag> save(List<Tag> tags) {
        String query = "insert into tag (id, name) values (?, ?)";
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Tag tag = tags.get(i);
                long id = (long) (Math.random() * 100000);
                tags.get(i).setId(id);
                ps.setLong(1, id);
                ps.setString(2, tag.getName());
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
        return tags;
    }
}
