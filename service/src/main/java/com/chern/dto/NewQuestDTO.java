package com.chern.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
public class NewQuestDTO {

    private String name;
    private String description;
    private String genre;
    private double price;
    private LocalTime duration;
    private int maxPeople;
    private List<TagDTO> tags;

}
