package com.obi.articleservice.model;

public class ImmutableArticle { // record always immutable
    private final String id;

    public ImmutableArticle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
