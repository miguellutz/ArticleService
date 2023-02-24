package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest     // for integration tests (loads entire spring context)
class ArticleServiceIntegrationTest {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository; // --> no need since ArticleRepository is already in context / instantiated within ArticleService


    @Test
    void getAllArticles() {
        // GIVEN
        // persisted more than one article
        List<Article> foundArticles = articleService.findAll();
        Integer numberOfArticles = foundArticles.size();
        if (numberOfArticles == 0) {
            articleService.save("1", "123", 1.0, 1.0, 1.0);
            articleService.save("2", "123", 1.0, 1.0, 1.0);
        } else if (numberOfArticles == 1) {
            articleService.save("2", "123", 1.0, 1.0, 1.0);
        }

        // WHEN
        // get all articles
        foundArticles = articleService.findAll();

        // THEN
        // list of gotten articles has same size as persisted
        Assertions.assertThat(foundArticles.size()).isEqualTo(2);
    }

    @Test
    void addArticle() {
        String id = UUID.randomUUID().toString();
        articleService.save(id, "123", 1.0, 1.0, 1.0);
        Optional<Article> addedArticle = articleService.findById(id);

        assertThat(addedArticle.get().getId()).isEqualTo(id);
    }

    @Test
    void updateArticle() {
        String id = UUID.randomUUID().toString();
        Article article = articleService.save(id, "123", 5.0, 5.0, 5.0);

        Optional<Article> articleToUpdate = articleService.findById(id);
        articleToUpdate.get().setInternationalArticleNumber("202");

        assertThat(articleToUpdate.get().getInternationalArticleNumber()).isEqualTo("202");
    }

    @Test
    void deleteArticle() {
        String id = UUID.randomUUID().toString();
        articleService.save(id, "123", 6.0, 6.0, 6.0);

        Optional<Article> addedArticle = articleService.findById(id);

        articleService.deleteById(id);

        assertThat(articleService.findById(id)).isNotPresent();
    }
}