package com.chern.dto.converter;

import com.chern.dto.TabularQuestDto;
import com.chern.exception.FeatureNotRealisedException;
import com.chern.model.Quest;
import org.springframework.stereotype.Component;

@Component
public class TabularQuestMapper implements Mapper<TabularQuestDto, Quest> {

    @Override
    public TabularQuestDto entityToDto(Quest entity) {
        return TabularQuestDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .duration(entity.getDuration())
                .genre(entity.getGenre())
                .maxPeople(entity.getMaxPeople())
                .price(entity.getPrice())
                .build();
    }

    @Override
    public Quest dtoToEntity(TabularQuestDto dto) {
        throw new FeatureNotRealisedException("Feature not realized");
    }
}
