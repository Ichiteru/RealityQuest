package com.chern.repo;

import com.chern.model.Tag;

import java.util.List;

public interface TagRepository {

//    List<Tag> getByQuestId(long id);
//    Boolean existsByQuestId(long id);

    List<Tag> save(List<Tag> tags);

    Tag getById(long id);

    List<Tag> getAll(int page, int size);

    boolean existsById(long id);

    long deleteById(long id);

    int delete(List<Long> ids);

    Tag findMostUsedOfTopUser();

    List<Tag> getByNames(List<String> tags);
}
