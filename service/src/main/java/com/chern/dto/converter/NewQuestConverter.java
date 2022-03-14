package com.chern.dto.converter;

import com.chern.dto.NewQuestDTO;
import com.chern.dto.TagDTO;
import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class NewQuestConverter implements Converter<NewQuestDTO, Quest> {

    @Autowired
    private Converter<TagDTO, Tag> tagConverter;

    @Override
    public NewQuestDTO entityToDtoConverter(Quest entity) {
        return null;
    }

    @Override
    public Quest dtoToEntityConverter(NewQuestDTO dto) {
        return Quest.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .genre(dto.getGenre())
                .price(dto.getPrice())
                .duration(dto.getDuration())
                .maxPeople(dto.getMaxPeople())
                .tags(dto.getTags().stream()
                        .map(tag -> tagConverter.dtoToEntityConverter(tag))
                        .collect(Collectors.toList()))
                .build();
    }
}
