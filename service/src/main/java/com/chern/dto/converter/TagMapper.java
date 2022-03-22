package com.chern.dto.converter;

import com.chern.dto.TagDto;
import com.chern.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper implements Mapper<TagDto, Tag> {

    @Override
    public TagDto entityToDto(Tag entity) {
        return TagDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

    @Override
    public Tag dtoToEntity(TagDto dto) {
        return Tag.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }
}
