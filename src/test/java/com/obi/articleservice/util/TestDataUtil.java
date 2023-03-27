package com.obi.articleservice.util;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.model.CountryArticle;
import com.obi.articleservice.model.CountryArticleId;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestDataUtil {
    public static Article createArticle() {
        Article article = new Article();

        // create country
        List<CountryArticle> countryArticles = new ArrayList<>();
        countryArticles.add(new CountryArticle(new CountryArticleId(null,"DE"), "Kovalex", false, article));

        // set article properties
        article.setId(UUID.randomUUID().toString());
        article.setInternationalArticleNumber(UUID.randomUUID().toString());
        article.setLength(2.1);
        article.setHeight(2.2);
        article.setWidth(2.3);
        article.setCountryArticles(countryArticles);
        return article;
    }
}
