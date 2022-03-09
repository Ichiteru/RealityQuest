package com.chern.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "quest")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"tags"})
public class Quest {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   private String name;
   private String description;
   private String genre;
   private double price;
   private LocalTime duration;
   @Column(name = "creation_date")
   private LocalDate creationDate;
   @Column(name = "modification_date")
   private LocalDate modificationDate;
   @Column(name = "max_people")
   private int maxPeople;
   @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
   @JoinTable(
           name = "quest_tag",
           joinColumns = @JoinColumn(name = "quest_id"),
           inverseJoinColumns = @JoinColumn(name = "tag_id")
   )
   @ToString.Exclude
   @JsonIgnoreProperties("quests")
   private List<Tag> tags;
   @ToString.Exclude
   @OneToMany(mappedBy = "quest")
   private Set<Order> orders;
}
