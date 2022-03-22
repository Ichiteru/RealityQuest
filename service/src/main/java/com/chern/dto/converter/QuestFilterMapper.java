package com.chern.dto.converter;

import com.chern.dto.QuestFilterDto;
import com.chern.exception.FeatureNotRealisedException;
import com.chern.filter.QuestFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class QuestFilterMapper implements Mapper<QuestFilterDto, QuestFilter> {

    @Override
    public QuestFilterDto entityToDto(QuestFilter entity) {
        throw new FeatureNotRealisedException("Feature not realized");
    }

    @Override
    public QuestFilter dtoToEntity(QuestFilterDto dto) {
        QuestFilter questFilter = new QuestFilter();
        BeanUtils.copyProperties(dto,questFilter);
        return questFilter;
    }
}
