package com.chern.dto.converter;

import com.chern.dto.TabularOrderDTO;
import com.chern.model.Order;
import org.springframework.stereotype.Component;

@Component
public class TabularOrderConverter implements Converter<TabularOrderDTO, Order> {
    @Override
    public TabularOrderDTO entityToDtoConverter(Order entity) {
        return TabularOrderDTO.builder()
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
    public Order dtoToEntityConverter(TabularOrderDTO entity) {
        return null;
    }
}
