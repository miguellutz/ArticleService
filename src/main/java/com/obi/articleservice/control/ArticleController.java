package com.obi.articleservice.control;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    @Autowired                      // replaces constructor and automatically injects dependencies from ArticleService
    ArticleService articleService;

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PostMapping
    public void addNewArticle() {
        articleService.addArticle();
    }

    @DeleteMapping
    public void deleteArticle() {
        articleService.deleteArticle();
    }

    @PutMapping
    public void updateArticle() {
        articleService.updateArticle();
    }
}
