package com.obi.articleservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "article")
public class Article {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "international_article_number")
    private String internationalArticleNumber;
    @Column(name = "height")
    private double height;
    @Column(name = "width")
    private double width;
    @Column(name = "length")
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