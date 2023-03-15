package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ArticleServiceTest {

    @InjectMocks
    ArticleService articleService;
    @Mock
    private ArticleRepository articleRepository;

    @DisplayName("Return list of persisted articles")
    @Test
    void findAll() {
        // GIVEN multiple articles in repository
        List<Article> articleList = new ArrayList<>();
        articleList.add(new Article(UUID.randomUUID().toString(), "1", 20.0, 2.0, 2.0));
        articleList.add(new Article(UUID.randomUUID().toString(), "2", 20.0, 2.0, 2.0));
        articleList.add(new Article(UUID.randomUUID().toString(), "3", 20.0, 2.0, 2.0));

        Mockito.when(articleRepository.findAll()).thenReturn(articleList);

        // WHEN findAll articles
        List<Article> articles = articleService.findAll();

        // THEN return list with all articles
        assertThat(articleService.findAll()).hasSize(3);
    }

    @DisplayName("Return individual article")
    @Test
    void findById() {
        // GIVEN article in DB
        String id = UUID.randomUUID().toString();
        Article article = new Article(id, "123", 2.0, 2.0, 2.0);

        // WHEN article is searched by id
        Mockito.when(articleRepository.findById(id)).thenReturn(Optional.of(article));

        // THEN return optional of found article
        assertThat(articleService.findById(id)).isEqualTo(Optional.of(article));
    }

    @DisplayName("Return empty optional when searching for non-existing id")
    @Test
    void findByNonExistingId() {
        Mockito.when(articleRepository.findById("123")).thenReturn(Optional.empty());

        assertThat(articleService.findById("123")).isEqualTo(Optional.empty());
    }




}
