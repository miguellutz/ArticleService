package com.obi.articleservice.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

@ToString
@Getter
@RequiredArgsConstructor
public class ArticleDto { // no need for noargsconstructor, possibility to hide properties otherwise exposed in normal article
    private final String id;// final muss und darf nur einmal instantiert werden (best practice)
    private final String internationalArticleNumber;
    private final Double height;
    private final Double width;
    private final Double length;
    // validation.article.height.notnull
    // DE -> validation.article.height.notnull -> Ein Article muss eine Höhe haben
    //private List<CountryArticle> countryArticles;
}