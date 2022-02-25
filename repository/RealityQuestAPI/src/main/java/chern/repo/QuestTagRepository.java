package chern.repo;

import chern.model.Quest;
import chern.model.Tag;

import java.util.List;

public interface QuestTagRepository {

    boolean bindQuestWithTags(Quest quest, List<Tag> tags);

    void unbindQuestTags(Quest quest, List<Tag> tags);

    void unbindAllQuestTags(Quest quest);
}
