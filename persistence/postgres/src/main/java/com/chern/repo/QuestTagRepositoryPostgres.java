package com.chern.repo;

import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QuestTagRepositoryPostgres implements QuestTagRepository{

    private final JdbcTemplate jdbcTemplate;

    public QuestTagRepositoryPostgres(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean bindQuestWithTags(Quest quest, List<Tag> tags) {
        String query = "insert into quest_tag (quest_id, tag_id) values (?, ?)";
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
}
