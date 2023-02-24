package com.obi.articleservice.control;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    @Autowired                      // replaces constructor and automatically injects dependencies from ArticleService
    ArticleService articleService;

    @GetMapping
    public List<Article> findAll() {
        return articleService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Article> findById(@PathVariable(value = "id") String id) { return articleService.findById(id); }

    @PostMapping
    public Article save(String id,
                     String internationalArticleNumber,
                     double height,
                     double width,
                     double length) {
        Article article = articleService.save(id, internationalArticleNumber, height, width, length);
        return article;
    }

    // wie nutze ich simultan params und erlaube gleichzeitig erstellung neuer?
    @PutMapping //("/{id}")?
    public void update(String id,
                       String internationalArticleNumber,
                       double height,
                       double width,
                       double length) {
        articleService.save(id, internationalArticleNumber, height, width, length);
    }

    // delete über params oder gepasster id?
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(value = "id") String id) {
        articleService.deleteById(id);
    }

    //@DeleteMapping
    //public void delete(Article article) { articleService.delete(article); }
}
