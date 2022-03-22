package com.chern.dto.converter;

import com.chern.dto.FullInfoQuestDto;
import com.chern.dto.TagDto;
import com.chern.exception.FeatureNotRealisedException;
import com.chern.model.Quest;
import com.chern.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class FullInfoQuestMapper implements Mapper<FullInfoQuestDto, Quest> {

    @Autowired
    private Mapper<TagDto, Tag> tagMapper;

    @Override
    public FullInfoQuestDto entityToDto(Quest entity) {
        return FullInfoQuestDto.builder()
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
                        .map(tag -> tagMapper.entityToDto(tag))
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public Quest dtoToEntity(FullInfoQuestDto dto) {
        throw new FeatureNotRealisedException("Feature not realized");
    }
}
