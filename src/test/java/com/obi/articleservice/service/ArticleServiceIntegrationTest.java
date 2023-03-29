package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest     // for integration tests (loads entire spring context)
class ArticleServiceIntegrationTest {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleRepository articleRepository; // --> no need since ArticleRepository is already in context / instantiated within ArticleService


    @BeforeEach
    void cleanDB(){ // to wipe DB for each test in case data left from another test
        articleRepository.deleteAll();
    }

    @DisplayName("Expect all persisted articles to be found")
    @Test
    void getAllArticles() {
        // GIVEN
        // persisted more than one article
        List<Article> initialFoundArticles = articleService.findAll();
        Assertions.assertThat(initialFoundArticles.size()).isEqualTo(0);

        articleService.create(new Article("123", "123", 1.0, 1.0, 1.0, new ArrayList<>())); // --> id's need to be assigned manually?
        articleService.create(new Article("1234", "123", 1.0, 1.0, 1.0, new ArrayList<>()));


        // WHEN
        // get all articles
        List<Article> foundArticles = articleService.findAll();

        // THEN
        // list of gotten articles has same size as persisted
        Assertions.assertThat(foundArticles.size()).isEqualTo(2);
    }

    @DisplayName("Ensure new article got new id")
    @Test
    void saveNewArticle() {
        Article savedArticle = articleService.create(new Article("123", "123", 1.0, 1.0, 1.0, new ArrayList<>()));
        assertThat(savedArticle.getId()).isNotBlank();
    }

    @DisplayName("Ensure existing article is updated")
    @Test
    void updateArticle() {
        // GIVEN article exists in DB
        Article existingArticle = articleService.create(new Article("123", "123", 5.0, 5.0, 5.0, new ArrayList<>()));

        // WHEN article is updated
        existingArticle.setInternationalArticleNumber("202");
        Article updatedArticle = articleService.update(existingArticle);

        // THEN expect changes in DB
        assertThat(updatedArticle.getInternationalArticleNumber()).isEqualTo("202");
    }

    @DisplayName("Existing article is deleted after delete is called")
    @Test
    void deleteArticle() {
        Article savedArticle = articleService.create(new Article("123", "123", 6.0, 6.0, 6.0, new ArrayList<>()));

        articleService.deleteById(savedArticle.getId());

        assertThat(articleService.findById(savedArticle.getId())).isNotPresent();
    }

    @DisplayName("Ensure that exception is thrown on not existing deletion")
    @Test
    void deleteNotExistingArticle() {
        assertThat(articleRepository.count()).isEqualTo(0);
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> articleService.deleteById(UUID.randomUUID().toString()));
    }
}