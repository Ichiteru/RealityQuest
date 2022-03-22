package com.chern.dto.converter;

import com.chern.dto.NewQuestDto;
import com.chern.dto.TagDto;
import com.chern.exception.FeatureNotRealisedException;
import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NewQuestMapper implements Mapper<NewQuestDto, Quest> {

    @Autowired
    private Mapper<TagDto, Tag> tagMapper;

    @Override
    public NewQuestDto entityToDto(Quest entity) {
        throw new FeatureNotRealisedException("Feature not realized");
    }

    @Override
    public Quest dtoToEntity(NewQuestDto dto) {
        return Quest.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .genre(dto.getGenre())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .maxPeople(dto.getMaxPeople())
                .tags(dto.getTags().stream()
                        .map(tag -> tagMapper.dtoToEntity(tag))
                        .collect(Collectors.toList()))
                .build();
    }
}
