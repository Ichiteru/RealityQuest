package com.chern.dto;

import com.chern.model.Order;
import com.chern.model.Tag;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

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
    private List<TagDTO> tags;
}
