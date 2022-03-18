package com.chern.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class QuestFilterDto {
    private List<String> tags;
    private List<String> names;
    private List<String> descriptions;

    public QuestFilterDto() {
        tags = new ArrayList<>();
        names = new ArrayList<>();
        descriptions = new ArrayList<>();
    }
}
