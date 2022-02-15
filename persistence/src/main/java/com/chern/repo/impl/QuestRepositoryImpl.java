package com.chern.repo.impl;

import com.chern.mapper.QuestRowMapper;
import com.chern.model.Quest;
import com.chern.repo.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class QuestRepositoryImpl implements QuestRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public QuestRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Quest save(Quest quest) {
        return null;
    }

    @Override
    public Quest getById(long id) {
        return jdbcTemplate.queryForObject("select * from quest where id=?", new Object[]{id}, new QuestRowMapper());
    }

    @Override
    public List<Quest> getAll() {
        return null;
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public Quest update(Quest quest) {
        return null;
    }
}
