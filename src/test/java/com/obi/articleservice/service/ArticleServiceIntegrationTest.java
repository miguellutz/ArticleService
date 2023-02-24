package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest     // for integration tests (loads entire spring context)
class ArticleServiceIntegrationTest {
    @Autowired
    private ArticleService articleService;


    @Test
    void getAllArticles() {
        // GIVEN
        // persisted more than one article

        // WHEN
        // get all articles

        // THEN
        // list of gotten articles has same size as persisted

    }

    @Test
    void addArticle() {
    }

    @Test
    void updateArticle() {
    }

    @Test
    void deleteArticle() {
    }
}