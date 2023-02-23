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
public class Article {

    @Id
    private String id;       // final muss und darf nur einmal instantiert werden (best practice)
    private String internationalArticleNumber;
    private double height;
    private double width;
    private double length;

    //private List<CountryArticle> countryArticles;
}