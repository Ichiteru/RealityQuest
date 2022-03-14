package com.chern.dto.converter;

import com.chern.dto.TagDTO;
import com.chern.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagConverter implements Converter<TagDTO, Tag> {

    @Override
    public TagDTO entityToDtoConverter(Tag entity) {
        return TagDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public Tag dtoToEntityConverter(TagDTO dto) {
        return Tag.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
