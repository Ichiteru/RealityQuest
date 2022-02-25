package com.chern.mapper;


import chern.model.Tag;
import chern.model.builder.TagBuilder;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TagRowMapper implements RowMapper<List<Tag>> {
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
}
