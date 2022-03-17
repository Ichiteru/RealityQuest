package com.chern.repo;

import com.chern.model.Quest;
import com.chern.model.Tag;

import java.util.List;

public interface QuestRepository {
    Quest save(Quest quest);
    Quest getById(long id);

    List<Quest> getAll(int page, int size);

    Quest update(Quest quest);
    Boolean existsById(long id);
    long deleteById(long id);
    int delete(List<Long> ids);

    List<Quest> searchBySeveralTags(List<Long> tagIds);
//    List<Quest> searchByParams(String query);
}
