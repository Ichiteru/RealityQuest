package com.chern.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
public class FullInfoQuestDto {

    private long id;
    private String name;
    private String description;
    private String genre;
    private double price;
    private LocalTime duration;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private int maxPeople;
    private List<TagDto> tags;
}
