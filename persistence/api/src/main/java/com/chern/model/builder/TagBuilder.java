package com.chern.model.builder;

import com.chern.model.Quest;
import com.chern.model.Tag;

import java.util.List;

public final class TagBuilder {
    private long id;
    private String name;
    private List<Quest> quests;

    private TagBuilder() {
    }

    public static TagBuilder aTag() {
        return new TagBuilder();
    }

    public TagBuilder withId(long id) {
        this.id = id;
        return this;
    }

    public TagBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TagBuilder withQuests(List<Quest> quests) {
        this.quests = quests;
        return this;
    }

    public Tag build() {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        tag.setQuests(quests);
        return tag;
    }
}
