package com.chern.model.builder;

import com.chern.model.Tag;

public final class TagBuilder {
    private long id;
    private String name;

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

    public Tag build() {
        Tag tag = new Tag();
        tag.setId(id);
        tag.setName(name);
        return tag;
    }
}
