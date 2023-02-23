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
    public List<Article> findAll() {
        return articleService.findAll();
    }

    @PostMapping
    public Article save(String id,
                     String internationalArticleNumber,
                     double height,
                     double width,
                     double length) {
        Article article = articleService.save(id, internationalArticleNumber, height, width, length);
        return article;
    }

    @PutMapping
    public void update(String id,
                       String internationalArticleNumber,
                       double height,
                       double width,
                       double length) {
        articleService.save(id, internationalArticleNumber, height, width, length);
    }

    @DeleteMapping
    public void deleteById(String id) {
        articleService.deleteById(id);
    }

    //@DeleteMapping
    //public void delete(Article article) { articleService.delete(article); }
}
