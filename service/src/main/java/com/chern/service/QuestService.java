package com.chern.service;

import com.chern.exception.NoSuchDataException;
import com.chern.model.Quest;
import com.chern.model.Tag;
import com.chern.repo.QuestRepository;
import com.chern.repo.TagRepository;
import com.chern.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuestService {

    @Autowired
    private QuestRepository questRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private Validator<Quest> questValidator;
    @Autowired
    private Validator<Tag> tagValidator;

    public Quest getById(long id) {
        try {
            Quest quest = questRepository.getById(id);
            return quest;
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("This quest doesn't exist");
        }
    }

    public List<Quest> getAll(int page, int size) {
        try {
            List<Quest> quests = questRepository.getAll(page, size);
            return quests;
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public Quest save(Quest quest) {
        questValidator.validate(quest);
        quest.setCreationDate(LocalDate.now());
        quest.setModificationDate(LocalDate.now());
        List<Tag> tags = quest.getTags();
        if (tags != null) {
            Map<Boolean, List<Tag>> derivedTags = tags.stream()
                    .collect(Collectors.partitioningBy(tag -> tag.getId() == 0));
            List<Tag> newTags = derivedTags.get(true);
            newTags.forEach(tag -> tagValidator.validate(tag));
            tagRepository.save(newTags);
        }
        quest = questRepository.save(quest);
        return quest;
    }

    @Transactional
    public Quest update(Quest quest) {
        if (!questRepository.existsById(quest.getId())) {
            throw new NoSuchDataException("There is no quest with this id(" + quest.getId() + ")");
        }
        questValidator.validate(quest);
        quest.setModificationDate(LocalDate.now());
        List<Tag> tags = quest.getTags();
        questRepository.update(quest);
        tags.forEach(tag -> tagValidator.validate(tag));
        return quest;
    }

    @Transactional
    public long deleteById(long id) {
        if (!questRepository.existsById(id).booleanValue()) {
            throw new NoSuchDataException("Quest with id = " + id + " doesn't exists");
        }
        return questRepository.deleteById(id);
    }

    @Transactional
    public int delete(List<Long> ids) {
        if (ids.size() == 0) {
            return 0;
        }
        return questRepository.delete(ids);
    }

//    public List<Quest> searchBy(String tagName, String namePart,
//                                String descriptionPart, String sortBy, String sortType) {
//        String query = SearchQueryBuilder.buildSearchQuery(tagName, namePart, descriptionPart, sortBy, sortType);
//        return questRepository.searchByParams(query);
//    }
}
