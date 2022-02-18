package com.chern.repo;

import com.chern.model.Tag;

import java.util.List;

public interface TagRepository {

    List<Tag> getByQuestId(long id);
    Boolean existsByQuestId(long id);
}
