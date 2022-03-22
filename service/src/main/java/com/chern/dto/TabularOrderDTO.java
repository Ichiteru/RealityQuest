package com.chern.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TabularOrderDTO {

    private long id;
    private String customer;
    private String questName;
    private double cost;
    private LocalDateTime purchaseTime;
    private LocalDateTime reserveTime;
    private LocalDateTime endTime;
}
