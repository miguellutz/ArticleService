package com.obi.articleservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "article")
// @NoArgsConstructor --> default constructor
@Data      // für standard plain old java objects (pojos) --> getter, setter, noargs... (von Lombok)
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Article {
    @Id
    private String id;// final muss und darf nur einmal instantiert werden (best practice)
    private String internationalArticleNumber;
    private Double height;
    private Double width;
    private Double length;

    // validation.article.height.notnull
    // DE -> validation.article.height.notnull -> Ein Article muss eine Höhe haben
    //private List<CountryArticle> countryArticles;
}