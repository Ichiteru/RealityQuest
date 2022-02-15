package com.chern.mapper;

import com.chern.model.Quest;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestRowMapper implements RowMapper<Quest> {
    @Override
    public Quest mapRow(ResultSet rs, int rowNum) throws SQLException {
        Quest quest = new Quest();
        quest.setId(rs.getLong("id"));
        quest.setName(rs.getString("name"));
        quest.setDescription(rs.getString("description"));
        quest.setGenre(rs.getString("genre"));
        quest.setPrice(rs.getDouble("price"));
        quest.setDuration(rs.getTime("duration").toLocalTime());
        quest.setCreationDate(rs.getDate("creation_date").toLocalDate());
        quest.setModificationDate(rs.getDate("modification_date").toLocalDate());
        quest.setMaxPeople(rs.getInt("max_people"));
        quest.setTags(null);
        return quest;
    }
}
