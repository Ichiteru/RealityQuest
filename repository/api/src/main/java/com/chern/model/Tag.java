package com.chern.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"quests", "orders"})
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "quest_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "quest_id")
    )
    @JsonIgnoreProperties("tags")
    private List<Quest> quests;

    public Tag(String name) {
        this.name = name;
    }
}
