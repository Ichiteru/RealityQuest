package com.chern.repo;

import com.chern.model.Quest;

import java.util.List;

public interface QuestRepository {

    Number save(Quest quest);
    Quest getById(long id);
    List<Quest> getAll();
    void deleteById(long id);
    Quest update(Quest quest);
}
