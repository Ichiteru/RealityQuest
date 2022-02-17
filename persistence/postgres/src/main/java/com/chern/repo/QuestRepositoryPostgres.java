package com.chern.repo;

import com.chern.mapper.QuestRowMapper;
import com.chern.model.Quest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class QuestRepositoryPostgres implements QuestRepository {

    private final JdbcTemplate jdbcTemplate;

    public QuestRepositoryPostgres(DataSource dataSource) { // here need to put custom datSource with custom connection pool
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Quest save(Quest quest) {
        return null;
    }

    @Override
    public Quest getById(long id) throws EmptyResultDataAccessException {
        String query = "select * from quest where id=?";
        Quest quest = jdbcTemplate.queryForObject(query, new QuestRowMapper(), id);
        return quest;
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
