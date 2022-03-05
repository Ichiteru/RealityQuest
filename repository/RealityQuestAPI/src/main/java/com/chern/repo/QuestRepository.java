package com.chern.repo;

import com.chern.model.Quest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

public interface QuestRepository {
    Quest save(Quest quest);
    Quest getById(long id);

    List<Quest> getAll(int page, int size) throws EmptyResultDataAccessException;

    Quest update(Quest quest);
    Boolean existsById(long id);
    long deleteById(long id);
    int delete(List<Long> ids);
    List<Quest> searchByParams(String query);
}
