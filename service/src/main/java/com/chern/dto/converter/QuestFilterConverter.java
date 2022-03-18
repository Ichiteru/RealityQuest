package com.chern.dto.converter;

import com.chern.dto.QuestFilterDto;
import com.chern.filter.QuestFilter;
import com.chern.model.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class QuestFilterConverter implements Converter<QuestFilterDto, QuestFilter> {

    @Override
    public QuestFilterDto entityToDtoConverter(QuestFilter entity) {
        return null;
    }

    @Override
    public QuestFilter dtoToEntityConverter(QuestFilterDto entity) {
        QuestFilter questFilter = new QuestFilter();
        BeanUtils.copyProperties(entity,questFilter);
        return questFilter;
    }
}
