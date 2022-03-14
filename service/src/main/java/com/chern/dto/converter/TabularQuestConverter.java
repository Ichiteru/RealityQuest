package com.chern.dto.converter;

import com.chern.dto.TabularQuestDTO;
import com.chern.model.Quest;
import org.springframework.stereotype.Component;

@Component
public class TabularQuestConverter implements Converter<TabularQuestDTO, Quest> {

    @Override
    public TabularQuestDTO entityToDtoConverter(Quest entity) {
        return TabularQuestDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .duration(entity.getDuration())
                .genre(entity.getGenre())
                .maxPeople(entity.getMaxPeople())
                .price(entity.getPrice())
                .build();
    }

    @Override
    public Quest dtoToEntityConverter(TabularQuestDTO entity) {
        return null;
    }
}
