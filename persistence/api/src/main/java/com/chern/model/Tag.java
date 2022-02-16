package com.chern.model;

import java.util.List;

public class Tag {
    private long id;
    private String name;
    private List<Quest> quests;

    public Tag() {
    }

    public Tag(long id, String name, List<Quest> quests) {
        this.id = id;
        this.name = name;
        this.quests = quests;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quests=" + quests +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }
}
