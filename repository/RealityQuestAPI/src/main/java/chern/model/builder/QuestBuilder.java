package chern.model.builder;


import chern.model.Quest;
import chern.model.Tag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public final class QuestBuilder {
    private long id;
    private String name;
    private String description;
    private String genre;
    private double price;
    private LocalTime duration;
    private LocalDate creationDate;
    private LocalDate modificationDate;
    private int maxPeople;
    private List<Tag> tags;

    private QuestBuilder() {
    }

    public static QuestBuilder aQuest() {
        return new QuestBuilder();
    }

    public QuestBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public QuestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public QuestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public QuestBuilder withGenre(String genre) {
        this.genre = genre;
        return this;
    }

    public QuestBuilder withPrice(double price) {
        this.price = price;
        return this;
    }

    public QuestBuilder withDuration(LocalTime duration) {
        this.duration = duration;
        return this;
    }

    public QuestBuilder withCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public QuestBuilder withModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
        return this;
    }

    public QuestBuilder withMaxPeople(int maxPeople) {
        this.maxPeople = maxPeople;
        return this;
    }

    public QuestBuilder withTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Quest build() {
        Quest quest = new Quest();
        quest.setId(id);
        quest.setName(name);
        quest.setDescription(description);
        quest.setGenre(genre);
        quest.setPrice(price);
        quest.setDuration(duration);
        quest.setCreationDate(creationDate);
        quest.setModificationDate(modificationDate);
        quest.setMaxPeople(maxPeople);
        quest.setTags(tags);
        return quest;
    }
}
