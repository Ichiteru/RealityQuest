package com.chern.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalTime;

@Data
@Builder
public class TabularQuestDto {

    private long id;
    private String name;
    private String genre;
    private double price;
    private LocalTime duration;
    private int maxPeople;

}
