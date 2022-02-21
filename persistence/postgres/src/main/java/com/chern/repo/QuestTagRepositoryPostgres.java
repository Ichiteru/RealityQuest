package com.chern.repo;

import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class QuestTagRepositoryPostgres implements QuestTagRepository{

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public QuestTagRepositoryPostgres(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public boolean bindQuestWithTags(Quest quest, List<Tag> tags) {
        String query = "insert into quest_tag (quest_id, tag_id) values (?, ?) " +
                "on conflict do nothing"; // modify not checked on success work on insert
        jdbcTemplate.batchUpdate(query, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Tag tag = tags.get(i);
                ps.setLong(1, quest.getId());
                ps.setLong(2, tag.getId());
            }

            @Override
            public int getBatchSize() {
                return tags.size();
            }
        });
        return true;
    }

    @Override
    public void unbindQuestTags(Quest quest, List<Tag> tags) {
        String query = "delete from quest_tag where quest_id=" + quest.getId() + " and tag_id not in(:ids)";
        List<Long> collect = tags.stream().map(Tag::getId).collect(Collectors.toList());
        Map namedParams = Collections.singletonMap("ids", collect);
//        SqlParameterSource parameters = new MapSqlParameterSource("ids",
//                tags.stream().map(Tag::getId).collect(Collectors.toList()));
        namedParameterJdbcTemplate.update(query, namedParams);
    }

    @Override
    public void unbindAllQuestTags(Quest quest) {
        String query = "delete from quest_tag where quest_id=?";
        jdbcTemplate.update(query, quest.getId());
    }
}
