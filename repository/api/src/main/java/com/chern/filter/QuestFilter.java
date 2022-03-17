package com.chern.filter;

import com.chern.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestFilter {
    private List<Tag> tags = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private List<String> descriptions = new ArrayList<>();
}
