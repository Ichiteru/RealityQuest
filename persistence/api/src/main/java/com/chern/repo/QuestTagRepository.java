package com.chern.repo;

import com.chern.model.Quest;
import com.chern.model.Tag;

import java.util.List;

public interface QuestTagRepository {

    boolean bindQuestWithTags(Quest quest, List<Tag> tags);
}
