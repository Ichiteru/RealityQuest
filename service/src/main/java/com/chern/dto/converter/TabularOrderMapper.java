package com.chern.dto.converter;

import com.chern.dto.TabularOrderDto;
import com.chern.exception.FeatureNotRealisedException;
import com.chern.model.Order;
import org.springframework.stereotype.Component;

@Component
public class TabularOrderMapper implements Mapper<TabularOrderDto, Order> {
    @Override
    public TabularOrderDto entityToDto(Order entity) {
        return TabularOrderDto.builder()
                .id(entity.getId())
                .cost(entity.getCost())
                .customer(entity.getUser().getUsername())
                .endTime(entity.getEndTime())
                .purchaseTime(entity.getPurchaseTime())
                .questName(entity.getQuest().getName())
                .reserveTime(entity.getReserveTime())
                .build();
    }

    @Override
    public Order dtoToEntity(TabularOrderDto dto) {
        throw new FeatureNotRealisedException("Feature not realized");
    }
}
