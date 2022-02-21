package com.chern.repo;

import com.chern.model.Quest;

import java.util.List;

public interface QuestRepository {

    Quest save(Quest quest);
    Quest getById(long id);
    List<Quest> getAll();
    Quest update(Quest quest);

    Boolean existsById(long id);

    long deleteById(long id);

    int delete(List<Long> ids);
}
