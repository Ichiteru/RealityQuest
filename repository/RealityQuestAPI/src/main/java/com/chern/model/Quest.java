package com.chern.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Quest {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   private String name;
   private String description;
   private String genre;
   private BigDecimal price;
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
   private List<Tag> tags;
   @OneToMany(mappedBy = "quest")
   private Set<Order> orders;
}
