package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import com.obi.articleservice.repository.CountryArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CountryArticleRepository countryArticleRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public void addArticle() {
        Article article = new Article();
        article.setId("1234");
        article.setInternationalArticleNumber("1234");
        article.setHeight(2);
        article.setLength(2);
        article.setWidth(2);
        articleRepository.save(article);
    }

    public Article updateArticle() {
        Optional<Article> article = articleRepository.findById("1234");
        if(article.isPresent()) {
            article.get().setLength(3);
            return articleRepository.save(article.get());
        }
        return null;
    }

    public void deleteArticle() {
        articleRepository.deleteById("1234");
    }


}
