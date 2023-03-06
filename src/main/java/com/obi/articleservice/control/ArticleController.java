package com.obi.articleservice.control;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    // GET /article/uuid-12-udasdasd
    @GetMapping("/{id}")
    public ResponseEntity<Article> findById(@PathVariable(value = "id") String id) { // Optional Article might not be found
        Optional<Article> foundArticle = articleService.findById(id);
        if(foundArticle.isPresent()){
            Article article = foundArticle.get();
            return ResponseEntity.ok(article);
        }else{
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PostMapping
    public ResponseEntity<Article> save(Article article) {
        boolean articleIsInvalid = article == null || article.getId() == null || article.getId().isBlank(); // shift + F6 to rename all instances
        if (articleIsInvalid) { // blank considers spaces "   "
            return ResponseEntity.badRequest().build();
        }
        Article newArticle = articleService.save(article);
        return ResponseEntity.ok(newArticle);
    }

    // PUT /article/uuid-12-udasdasd
    // wie nutze ich simultan params und erlaube gleichzeitig erstellung neuer?
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") String id, Article article) {
        if(!id.equals(article.getId())){
            return ResponseEntity.badRequest().build();
        }
        if (!articleService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article with id "+ id +" not found");
        }
        Article savedArticle = articleService.save(article);
        return ResponseEntity.ok(savedArticle);
    }

    // delete über params oder gepasster id?
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") String id) {
        if(!articleService.existsById(id)){
            return ResponseEntity.badRequest().build();
        }
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //@DeleteMapping
    //public void delete(Article article) { articleService.delete(article); }
}
