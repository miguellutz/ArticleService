package com.obi.articleservice.model;

public class MutableArticle {
    private String id;

    public MutableArticle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
