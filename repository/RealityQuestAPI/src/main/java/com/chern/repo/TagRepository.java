package com.chern.repo;

import com.chern.model.Tag;

import java.util.List;

public interface TagRepository {

//    List<Tag> getByQuestId(long id);
//    Boolean existsByQuestId(long id);

    List<Tag> save(List<Tag> tags);

    Tag getById(long id);

    List<Tag> getAll();

    boolean existsById(long id);

    long deleteById(long id);

    int delete(List<Long> ids);
}
