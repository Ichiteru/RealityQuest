package com.chern.service;

import com.chern.dto.*;
import com.chern.dto.converter.Converter;
import com.chern.dto.converter.TabularQuestConverter;
import com.chern.exception.DuplicateFieldException;
import com.chern.exception.NoSuchDataException;
import com.chern.filter.QuestFilter;
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
    @Autowired
    private Converter<TabularQuestDTO, Quest> tabularQuestConverter;
    @Autowired
    private Converter<FullInfoQuestDTO, Quest> infoQuestConverter;
    @Autowired
    private Converter<QuestFilterDto, QuestFilter> filterConverter;
    @Autowired
    private Converter<UpdateQuestDto, Quest> updateQuestConverter;

    public FullInfoQuestDTO getById(long id) {
        try {
            Quest quest = questRepository.getById(id);
            FullInfoQuestDTO fullInfoQuestDTO = infoQuestConverter.entityToDtoConverter(quest);
            return fullInfoQuestDTO;
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("This quest doesn't exist");
        }
    }

    public List<TabularQuestDTO> getAll(int page, int size) {
        try {
            List<Quest> quests = questRepository.getAll(page, size);
            List<TabularQuestDTO> tabularQuests =
                    quests.stream()
                            .map(quest -> tabularQuestConverter.entityToDtoConverter(quest))
                            .collect(Collectors.toList());
            return tabularQuests;
        } catch (EmptyResultDataAccessException ex) {
            return new ArrayList<>();
        }
    }

    @Transactional
    public Quest save(Quest quest)  throws DuplicateFieldException{
        questValidator.validate(quest);
        if(questRepository.existsByName(quest.getName())){
            throw new DuplicateFieldException("Quest with name '" + quest.getName() + "' already exists");
        }
        quest.setCreationDate(LocalDate.now());
        quest.setModificationDate(LocalDate.now());
        List<Tag> tags = quest.getTags();
        if (tags != null) {
            quest.setTags(saveNewTags(tags));
        }
        quest = questRepository.save(quest);
        return quest;
    }

    @Transactional
    public Quest update(UpdateQuestDto questDto) throws DuplicateFieldException{
        if (!questRepository.existsById(questDto.getId())) {
            throw new NoSuchDataException("There is no quest with this id(" + questDto.getId() + ")");
        }
        Quest quest = updateQuestConverter.dtoToEntityConverter(questDto);
        questValidator.validate(quest);
        if(questRepository.existsByName(quest.getName())){
            throw new DuplicateFieldException("Quest with name '" + quest.getName() + "' already exists");
        }
        List<Tag> tags = quest.getTags();
        quest.setTags(saveNewTags(tags));
        questRepository.update(quest);
        return quest;
    }

    private List<Tag> saveNewTags(List<Tag> tags) {
        Map<Boolean, List<Tag>> derivedTags = tags.stream()
                .collect(Collectors.partitioningBy(tag -> tag.getId() == 0));
        List<Tag> newTags = derivedTags.get(true);
        newTags.forEach(tag -> tagValidator.validate(tag));
        if (!tagRepository.getByNames(newTags.stream().map(Tag::getName).collect(Collectors.toList())).isEmpty()){
            throw new DuplicateFieldException("Tag with some of this names already exists");
        }
        tagRepository.save(newTags);
        return tags;
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

    public List<TabularQuestDTO> searchBySeveralTags(List<Long> tagIds) {
        List<Quest> quests = questRepository.searchBySeveralTags(tagIds);
        List<TabularQuestDTO> dtos = quests.stream()
                .map(q -> tabularQuestConverter.entityToDtoConverter(q))
                .collect(Collectors.toList());
        return dtos;
    }

    public List<TabularQuestDTO> searchBy(QuestFilterDto questFilterDto, int page, int size) {
        QuestFilter questFilter = filterConverter.dtoToEntityConverter(questFilterDto);
        List<Tag> tags = tagRepository.getByNames(questFilterDto.getTags());
        questFilter.setTags(tags);
        List<Quest> quests = questRepository
                .findByFilter(questFilter, page, size);
        return quests.stream().map(q -> tabularQuestConverter.entityToDtoConverter(q))
                .collect(Collectors.toList());
    }

}
