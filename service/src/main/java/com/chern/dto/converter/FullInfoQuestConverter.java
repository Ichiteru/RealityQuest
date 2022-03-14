package com.chern.dto.converter;

import com.chern.dto.FullInfoQuestDTO;
import com.chern.dto.TagDTO;
import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FullInfoQuestConverter implements Converter<FullInfoQuestDTO, Quest> {

    @Autowired
    private Converter<TagDTO, Tag> tagConverter;

    @Override
    public FullInfoQuestDTO entityToDtoConverter(Quest entity) {
        return FullInfoQuestDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .creationDate(entity.getCreationDate())
                .duration(entity.getDuration())
                .genre(entity.getGenre())
                .maxPeople(entity.getMaxPeople())
                .modificationDate(entity.getModificationDate())
                .price(entity.getPrice())
                .tags(entity.getTags().stream()
                        .map(tag -> tagConverter.entityToDtoConverter(tag))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Quest dtoToEntityConverter(FullInfoQuestDTO entity) {
        return null;
    }
}
