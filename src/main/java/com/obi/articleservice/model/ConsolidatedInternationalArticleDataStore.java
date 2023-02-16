package com.obi.articleservice.model;

public class ConsolidatedInternationalArticleDataStore {
    private final String id;
    private final String internationalArticleNumber;
    private final double width;
    private final double length;

    public ConsolidatedInternationalArticleDataStore(String id, String internationalArticleNumber, double width, double length) {
        this.id = id;
        this.internationalArticleNumber = internationalArticleNumber;
        this.width = width;
        this.length = length;
    }

    public String getId() {
        return id;
    }

    public String getInternationalArticleNumber() {
        return internationalArticleNumber;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }
}
