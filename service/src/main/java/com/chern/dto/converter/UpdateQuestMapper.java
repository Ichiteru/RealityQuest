package com.chern.dto.converter;

import com.chern.dto.TagDto;
import com.chern.dto.UpdateQuestDto;
import com.chern.exception.FeatureNotRealisedException;
import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class UpdateQuestMapper implements Mapper<UpdateQuestDto, Quest> {

    @Autowired
    private Mapper<TagDto, Tag> tagMapper;

    @Override
    public UpdateQuestDto entityToDto(Quest entity) {
        throw new FeatureNotRealisedException("Feature not realized");
    }

    @Override
    public Quest dtoToEntity(UpdateQuestDto dto) {
        return Quest.builder()
                .tags(dto.getTags().stream()
                        .map(t -> tagMapper.dtoToEntity(t))
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
