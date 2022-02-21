package com.chern.service;

import com.chern.exception.NoSuchDataException;
import com.chern.model.Quest;
import com.chern.model.Tag;
import com.chern.repo.QuestRepository;
import com.chern.repo.QuestTagRepository;
import com.chern.repo.TagRepository;
import com.chern.validation.Validator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestService {

    private final QuestTagRepository questTagRepository;
    private final QuestRepository questRepository;
    private final TagRepository tagRepository;
    private final Validator<Quest> questValidator;
    private final Validator<Tag> tagValidator;

    public QuestService(QuestTagRepository questTagRepository, QuestRepository questRepository,
                        TagRepository tagRepository, Validator questValidator, Validator<Tag> tagValidator) {
        this.questTagRepository = questTagRepository;
        this.questRepository = questRepository;
        this.tagRepository = tagRepository;
        this.questValidator = questValidator;
        this.tagValidator = tagValidator;
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

    @Transactional
    public Quest save(Quest quest){
        questValidator.validate(quest);
        quest.setCreationDate(LocalDate.now());
        quest.setModificationDate(LocalDate.now());
        quest = questRepository.save(quest);
        if (quest.getTags() != null){
            quest.getTags().forEach(tag -> tagValidator.validate(tag));
            Map<Boolean, List<Tag>> derivedTags = quest.getTags()
                    .stream()
                    .collect(Collectors.partitioningBy(tag -> tag.getId() == 0));
            List<Tag> oldTags = derivedTags.get(false);
            List<Tag> newTags = derivedTags.get(true);
            if (newTags != null){
                newTags = tagRepository.save(newTags);
                oldTags.addAll(newTags);
            }
            questTagRepository.bindQuestWithTags(quest, oldTags);
            quest.setTags(oldTags);
        }
        return quest;
    }
}
