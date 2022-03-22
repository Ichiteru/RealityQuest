package com.chern.dto;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateQuestDto {

    private long id;
    private String name;
    private String description;
    private String genre;
    private double price;
    private LocalTime duration;
    private int maxPeople;
    private List<TagDto> tags;
}
