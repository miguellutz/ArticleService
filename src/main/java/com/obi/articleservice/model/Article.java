package com.obi.articleservice.model;

public class Article {

    private String id;
    private String internationalArticleNumber;
    private double height;
    private double width;
    private double length;

    public Article() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInternationalArticleNumber() {
        return internationalArticleNumber;
    }

    public void setInternationalArticleNumber(String internationalArticleNumber) {
        this.internationalArticleNumber = internationalArticleNumber;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}