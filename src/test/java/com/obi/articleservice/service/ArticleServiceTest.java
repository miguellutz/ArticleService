package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import com.obi.articleservice.util.TestDataUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class ArticleServiceTest {

    private ArticleService articleService;
    private ArticleRepository mockedArticleRepository;

    @BeforeEach
    void setupMocks(){
        this.mockedArticleRepository = Mockito.mock(ArticleRepository.class);
        this.articleService = new ArticleService(mockedArticleRepository);
    }

    @DisplayName("Return list of persisted articles")
    @Test
    void findAll() {

        // GIVEN multiple articles in repository
        List<Article> articleList = new ArrayList<>();
        articleList.add(TestDataUtil.createArticle());
        articleList.add(TestDataUtil.createArticle());
        articleList.add(TestDataUtil.createArticle());

        Mockito.when(mockedArticleRepository.findAll()).thenReturn(articleList);

        // WHEN findAll articles
        List<Article> articles = articleService.findAll();

        // THEN return list with all articles
        assertThat(articles).hasSize(3);
        verify(mockedArticleRepository, times(1)).findAll();
        verifyNoMoreInteractions(mockedArticleRepository);
    }

    @DisplayName("Return individual article")
    @Test
    void findById() {
        // GIVEN article in DB
        String id = UUID.randomUUID().toString();
        Article article = new Article(id, "123", 2.0, 2.0, 2.0, new ArrayList<>());

        // WHEN article is searched by id
        Mockito.when(mockedArticleRepository.findById(anyString())).thenReturn(Optional.of(article));

        // THEN return optional of found article
        assertThat(articleService.findById(id)).usingRecursiveComparison().isEqualTo(Optional.of(article));
        verify(mockedArticleRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(mockedArticleRepository);
    }

    @DisplayName("Return empty optional when searching for non-existing id")
    @Test
    void findByNonExistingId() {
        // GIVEN repository always returns empty optional
        Mockito.when(mockedArticleRepository.findById(anyString())).thenReturn(Optional.empty());

        // WHEN THEN searching by id service returns empty optional as well
        assertThat(articleService.findById(anyString())).isEqualTo(Optional.empty());
        verify(mockedArticleRepository, times(1)).findById(anyString());
        verifyNoMoreInteractions(mockedArticleRepository);
    }

    @DisplayName("Return article when saving existing article")
    @Test
    void create() {

        // GIVEN articleRepository successfully saves existing article
        Article article = new Article(null, "123", 2.0, 2.0, 2.0, new ArrayList<>()); // on creation id is not null
        Mockito.when(mockedArticleRepository.save(article)).thenReturn(article);

        Article savedArticle = articleService.create(article);      // --> should articleService / articleRepository return bad request when article id is null?

        assertThat(savedArticle).usingRecursiveComparison().isEqualTo(article);
        assertThat(savedArticle.getId()).isEqualTo(article.getId());

        verify(mockedArticleRepository, times(1)).save(any(Article.class));
        verifyNoMoreInteractions(mockedArticleRepository);
    }

    @DisplayName("Return article with new height when updating height")
    @Test
    void update() {
        // GIVEN article is already created
        Article newArticle = new Article(null, "123", 2.0, 2.0, 2.0, new ArrayList<>()); // id when updating also not null
        Mockito.when(mockedArticleRepository.save(newArticle)).thenReturn(newArticle);
        Article createdArticle = articleService.create(newArticle);

        // WHEN updating article
        createdArticle.setHeight(123123.0);
        Mockito.when(mockedArticleRepository.save(createdArticle)).thenReturn(createdArticle);
        Article updatedArticle = articleService.update(createdArticle);

        // THEN ensure article has been updated
        assertThat(updatedArticle.getHeight()).isNotNull();
        assertThat(updatedArticle.getHeight()).isEqualTo(123123.0);
    }

    @DisplayName("Return nothing when article is deleted")
    @Test
    void deleteById() {
        String id = "123";
        doNothing().when(mockedArticleRepository).deleteById(id);
        Mockito.when(mockedArticleRepository.existsById(id)).thenReturn(true);

        articleService.deleteById(id);
        verify(mockedArticleRepository, times(1)).deleteById(id);
        verify(mockedArticleRepository, times(1)).existsById(id);
        verifyNoMoreInteractions(mockedArticleRepository);
    }

    @DisplayName("Throw illegal state exception when non-existing article is deleted")
    @Test
    void deleteNonExistingArticle() {
        String id = "123";
        Mockito.when(mockedArticleRepository.existsById(id)).thenReturn(false);

        Assertions.assertThrows(IllegalStateException.class, () -> articleService.deleteById(id));

        verify(mockedArticleRepository, times(1)).existsById(id);
        verifyNoMoreInteractions(mockedArticleRepository);
    }
}
