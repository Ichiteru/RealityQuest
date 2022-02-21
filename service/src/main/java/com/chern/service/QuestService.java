package com.chern.service;

import com.chern.exception.NoSuchDataException;
import com.chern.model.Quest;
import com.chern.model.Tag;
import com.chern.repo.QuestRepository;
import com.chern.repo.QuestTagRepository;
import com.chern.repo.TagRepository;
import com.chern.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestService {

    private final QuestTagRepository questTagRepository;
    private final QuestRepository questRepository;
    private final TagRepository tagRepository;
    private final Validator questValidator;

    public QuestService(QuestTagRepository questTagRepository, QuestRepository questRepository, TagRepository tagRepository, Validator questValidator) {
        this.questTagRepository = questTagRepository;
        this.questRepository = questRepository;
        this.tagRepository = tagRepository;
        this.questValidator = questValidator;
    }

    public Quest getById(long id) {
        try {
            Quest quest = questRepository.getById(id);
            if (tagRepository.existsByQuestId(id).booleanValue()){
                quest.setTags(tagRepository.getByQuestId(id));
            }
            return quest;
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("This quest doesn't exist");
        }
    }

    public List<Quest> getAll(){
        try {
            List<Quest> quests = questRepository.getAll();
            return quests;
        } catch (EmptyResultDataAccessException ex){
            throw new NoSuchDataException("There are no quests in storage");
        }
    }

    public Quest save(Quest quest){
        questValidator.validate(quest);
        Number questId = questRepository.save(quest);
        quest.setId(questId.longValue());
        if (quest.getTags() != null){
            Map<Boolean, List<Tag>> derivedTags = quest.getTags()
                    .stream()
                    .collect(Collectors.partitioningBy(tag -> tag.getId() == 0));
            List<Tag> newTags = derivedTags.get(true);
            List<Tag> oldTags = derivedTags.get(false);
            // insert old tags into db and get their id's
            newTags = tagRepository.save(newTags);
            newTags.addAll(oldTags);
            questTagRepository.bindQuestWithTags(quest, newTags);
        }
        return null;
    }
}
