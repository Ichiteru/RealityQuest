package com.chern.service;

import com.chern.exception.NoSuchDataException;
import com.chern.model.Quest;
import com.chern.repo.QuestRepository;
import com.chern.repo.TagRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class QuestService {

    private final QuestRepository questRepository;
    private final TagRepository tagRepository;

    public QuestService(QuestRepository questRepository, TagRepository tagRepository) {
        this.questRepository = questRepository;
        this.tagRepository = tagRepository;
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


}
