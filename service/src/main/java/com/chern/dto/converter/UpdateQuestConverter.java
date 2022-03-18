package com.chern.dto.converter;

import com.chern.dto.TagDTO;
import com.chern.dto.UpdateQuestDto;
import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class UpdateQuestConverter implements Converter<UpdateQuestDto, Quest> {

    @Autowired
    private Converter<TagDTO, Tag> tagConverter;

    @Override
    public UpdateQuestDto entityToDtoConverter(Quest entity) {
        return null;
    }

    @Override
    public Quest dtoToEntityConverter(UpdateQuestDto dto) {
        return Quest.builder()
                .tags(dto.getTags().stream()
                        .map(t -> tagConverter.dtoToEntityConverter(t))
                        .collect(Collectors.toList()))
                .maxPeople(dto.getMaxPeople())
                .duration(dto.getDuration())
                .price(dto.getPrice())
                .genre(dto.getGenre())
                .description(dto.getDescription())
                .name(dto.getName())
                .modificationDate(LocalDate.now())
                .id(dto.getId())
                .build();
    }
}
