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

import java.util.List;
import java.util.Optional;
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

        articleService.save(new Article("1", "123", 1.0, 1.0, 1.0));
        articleService.save(new Article("2", "123", 1.0, 1.0, 1.0));


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
        Article savedArticle = articleService.save(new Article(null, "123", 1.0, 1.0, 1.0));
        assertThat(savedArticle.getId()).isNotBlank();
    }

    @DisplayName("Ensure existing article is updated")
    @Test
    void updateArticle() {
        // GIVEN article exists in DB
        String id = UUID.randomUUID().toString();
        Article existingArticle = articleService.save(new Article(id, "123", 5.0, 5.0, 5.0));

        // WHEN article is updated
        existingArticle.setInternationalArticleNumber("202");
        Article updatedArticle = articleService.save(existingArticle);

        // THEN expect changes in DB
        assertThat(updatedArticle.getInternationalArticleNumber()).isEqualTo("202");
    }

    @DisplayName("Existing article is deleted after delete is called")
    @Test
    void deleteArticle() {
        String id = UUID.randomUUID().toString();
        articleService.save(new Article(id, "123", 6.0, 6.0, 6.0));

        articleService.deleteById(id);

        assertThat(articleService.findById(id)).isNotPresent();
    }

    @DisplayName("Ensure that exception is thrown on not existing deletion")
    @Test
    void deleteNotExistingArticle() {
        org.junit.jupiter.api.Assertions.assertThrows(
                EmptyResultDataAccessException.class,
                () -> articleService.deleteById(UUID.randomUUID().toString()));


    }
}