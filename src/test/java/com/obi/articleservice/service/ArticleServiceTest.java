package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
        assertThat(articles).hasSize(3);
        verify(articleRepository, times(1)).findAll();
        verifyNoMoreInteractions(articleRepository);
    }

    @DisplayName("Return individual article")
    @Test
    void findById() {
        // GIVEN article in DB
        String id = UUID.randomUUID().toString();
        Article article = new Article(id, "123", 2.0, 2.0, 2.0);

        // WHEN article is searched by id
        Mockito.when(articleRepository.findById(anyString())).thenReturn(Optional.of(article));

        // THEN return optional of found article
        assertThat(articleService.findById(id)).usingRecursiveComparison().isEqualTo(Optional.of(article));
        verify(articleRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(articleRepository);
    }

    @DisplayName("Return empty optional when searching for non-existing id")
    @Test
    void findByNonExistingId() {
        // GIVEN repository always returns empty optional
        Mockito.when(articleRepository.findById(anyString())).thenReturn(Optional.empty());

        // WHEN THEN searching by id service returns empty optional as well
        assertThat(articleService.findById(anyString())).isEqualTo(Optional.empty());
        verify(articleRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(articleRepository);
    }

    @DisplayName("Return article when saving existing article")
    @Test
    void create() {

        // GIVEN articleRepository successfully saves existing article
        Article article = new Article(null, "123", 2.0, 2.0, 2.0);
        Mockito.when(articleRepository.save(article)).thenReturn(article);

        Article savedArticle = articleService.create(article);

        assertThat(savedArticle).usingRecursiveComparison().isEqualTo(article);
        assertThat(savedArticle.getId()).isEqualTo(savedArticle.getId());

        verify(articleRepository, times(1)).save(any(Article.class));
        verifyNoMoreInteractions(articleRepository);
    }

    @DisplayName("Return article with new id when saving a new article")
    @Test
    void update() {
        // GIVEN article is already created
        Article newArticle = new Article(null, "123", 2.0, 2.0, 2.0);
        Mockito.when(articleRepository.save(newArticle)).thenReturn(newArticle);
        Article createdArticle = articleService.create(newArticle);

        // WHEN updating article
        createdArticle.setHeight(123123.0);
        Mockito.when(articleRepository.save(createdArticle)).thenReturn(createdArticle);
        Article savedArticle = articleService.update(createdArticle);

        // THEN ensure article has been updated
        assertThat(savedArticle.getHeight()).isNotNull();
        assertThat(savedArticle.getHeight()).isEqualTo(123123.0);
    }

    @DisplayName("Return nothing when article is deleted")
    @Test
    void deleteById() {
        String id = "123";
        doNothing().when(articleRepository).deleteById(id);
        Mockito.when(articleRepository.existsById(id)).thenReturn(true);

        articleService.deleteById(id);
        verify(articleRepository, times(1)).deleteById(id);
        verify(articleRepository, times(1)).existsById(id);
        verifyNoMoreInteractions(articleRepository);
    }

    @DisplayName("Throw illegal state exception when non-existing article is deleted")
    @Test
    void deleteNonExistingArticle() {
        String id = "123";
        Mockito.when(articleRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(IllegalStateException.class, () -> articleService.deleteById(id));

        verify(articleRepository, times(1)).existsById(id);
        verifyNoMoreInteractions(articleRepository);
    }
}
