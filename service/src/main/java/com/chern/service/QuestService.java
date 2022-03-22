package com.chern.service;

import com.chern.dto.*;
import com.chern.dto.converter.Mapper;
import com.chern.exception.DuplicateFieldException;
import com.chern.exception.NoSuchDataException;
import com.chern.filter.QuestFilter;
import com.chern.model.Quest;
import com.chern.model.Tag;
import com.chern.repo.QuestRepository;
import com.chern.repo.TagRepository;
import com.chern.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestService {

    private final QuestRepository questRepository;
    private final TagRepository tagRepository;
    private final Validator<Quest> questValidator;
    private final Validator<Tag> tagValidator;
    private final Mapper<TabularQuestDto, Quest> tabularQuestMapper;
    private final Mapper<FullInfoQuestDto, Quest> infoQuestMapper;
    private final Mapper<QuestFilterDto, QuestFilter> filterMapper;
    private final Mapper<UpdateQuestDto, Quest> updateQuestMapper;

    public FullInfoQuestDto getById(long id) {
        try {
            Quest quest = questRepository.getById(id);
            FullInfoQuestDto fullInfoQuestDTO = infoQuestMapper.entityToDto(quest);
            return fullInfoQuestDTO;
        } catch (EmptyResultDataAccessException ex) {
            throw new NoSuchDataException("This quest doesn't exist");
        }
    }

    public List<TabularQuestDto> getAll(int page, int size) {
        try {
            List<Quest> quests = questRepository.getAll(page*10, size);
            List<TabularQuestDto> tabularQuests =
                    quests.stream()
                            .map(quest -> tabularQuestMapper.entityToDto(quest))
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
    public Quest saveGeneratedData(Quest quest)  throws DuplicateFieldException{
        List<Tag> tags = quest.getTags();
        tagRepository.save(tags);
        quest = questRepository.save(quest);
        return quest;
    }

    @Transactional
    public Quest update(UpdateQuestDto questDto) throws DuplicateFieldException{
        if (!questRepository.existsById(questDto.getId())) {
            throw new NoSuchDataException("There is no quest with this id(" + questDto.getId() + ")");
        }
        Quest quest = updateQuestMapper.dtoToEntity(questDto);
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

    public List<TabularQuestDto> searchBySeveralTags(List<Long> tagIds) {
        List<Quest> quests = questRepository.searchBySeveralTags(tagIds);
        List<TabularQuestDto> dtos = quests.stream()
                .map(q -> tabularQuestMapper.entityToDto(q))
                .collect(Collectors.toList());
        return dtos;
    }

    public List<TabularQuestDto> searchBy(QuestFilterDto questFilterDto, int page, int size) {
        QuestFilter questFilter = filterMapper.dtoToEntity(questFilterDto);
        List<Tag> tags = tagRepository.getByNames(questFilterDto.getTags());
        questFilter.setTags(tags);
        List<Quest> quests = questRepository
                .findByFilter(questFilter, page*10, size);
        return quests.stream().map(q -> tabularQuestMapper.entityToDto(q))
                .collect(Collectors.toList());
    }

}
