package com.obi.articleservice.control;

import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/article", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    @Autowired                      // replaces constructor and automatically injects dependencies from ArticleService
    ArticleService articleService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Map<String, String> defaultExceptionHandler(Exception exception) {
        Map<String, String> errors = new HashMap<>();
       errors.put("message",exception.getMessage());
        return errors;
    }

    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public Map<String, String> runTimeException(RuntimeException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message","Runtime Exception");
        return errors;
    }*/

    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(IllegalStateException.class)
    public Map<String, String> ise(IllegalStateException exception) {
        Map<String, String> errors = new HashMap<>();
        errors.put("message","ILLEGAL STATE Exception");
        return errors;
    }*/


    @GetMapping
    public List<Article> findAll() {
        return articleService.findAll();
    }

    // GET /article/uuid-12-udasdasd
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> findById(@PathVariable(value = "id") String id) { // Optional Article might not be found
        Optional<Article> foundArticle = articleService.findById(id);
        if (foundArticle.isPresent()) {
            Article article = foundArticle.get();
            return ResponseEntity.ok(map(article));
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }


    @PostMapping
    public ResponseEntity<ArticleDto> save(@Valid @RequestBody ArticleDto articleDto) { // if not passed validation --> constraint violation exception --> bad request
        /* boolean articleIsValid = isArticleValid(articleDto); // shift + F6 to rename all instances
        if (!articleIsValid) { // blank considers spaces "   "
            return ResponseEntity.badRequest().build();
        } */ // --> no need for this since passed ArticleDto will always be valid?
        // throw new IllegalStateException("Oh Oh, unexpected error");
        Article newArticle = articleService.save(map(articleDto));
        return ResponseEntity.ok(map(newArticle));
    }

    /* private static boolean isArticleValid(ArticleDto articleDto) { // in methode auslagern mit opt + cmd + n
        return articleDto != null
                && articleDto.getId() == null
                && articleDto.getInternationalArticleNumber() != null
                && !articleDto.getInternationalArticleNumber().isBlank()
                && articleDto.getWidth() != null
                && articleDto.getLength() != null
                && articleDto.getHeight() != null;
    }*/

    private Article map(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setInternationalArticleNumber(articleDto.getInternationalArticleNumber());
        article.setWidth(articleDto.getWidth());
        article.setLength(articleDto.getLength());
        article.setHeight(articleDto.getHeight());
        return article;
    }

    private ArticleDto map(Article article) {
        return new ArticleDto(article.getId(), article.getInternationalArticleNumber(), article.getHeight(), article.getWidth(), article.getLength());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") String id, Article article) {
        if (!id.equals(article.getId())) {
            return ResponseEntity.badRequest().build();
        }
        if (!articleService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article with id " + id + " not found");
        }
        Article savedArticle = articleService.save(article);
        return ResponseEntity.ok(savedArticle);
    }

    // delete über params oder gepasster id?
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteById(@PathVariable(value = "id") String id) {
        if (!articleService.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        articleService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    //@DeleteMapping
    //public void delete(Article article) { articleService.delete(article); }
}
