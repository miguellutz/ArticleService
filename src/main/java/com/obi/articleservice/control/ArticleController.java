package com.obi.articleservice.control;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obi.articleservice.dto.ArticleCreationDto;
import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.service.ArticleService;
import com.obi.articleservice.util.ArticleMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping(path = "/api/article", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

                    // replaces constructor and automatically injects dependencies from ArticleService
    private final ArticleService articleService;
    private final ObjectMapper objectMapper;

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

    /*@GetMapping("/light/{id}")
    public ResponseEntity<? extends Object> findLightArticleById(@PathVariable String id) {        // no need to return ResponseEntity since findAll will always return list even if empty?
        Optional<Article> foundArticle = articleService.findById(id);
        if (foundArticle.isPresent()) {
            ObjectNode articleLight = objectMapper.createObjectNode();
            articleLight.put("krasseId", foundArticle.get().getId());
            // { "krasseId": "daziusdiasdz" }
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }*/

    @GetMapping
    public List<ArticleDto> findAll() {
        List<Article> allArticles = articleService.findAll();
        return ArticleMapper.mapArticlesStream(allArticles);
    } // findAll( ) on articleRepository will always return a list --> therefore always returns 200

    private List<ArticleDto> mapToDtos(List<Article> allArticles) {
        List<ArticleDto> result = new ArrayList<>();
        for (Article article : allArticles) {
            ArticleDto mappedDto = ArticleMapper.mapToDto(article);
            result.add(mappedDto);
        }
        return result;
    }
    /*private List<ArticleDto> mapArticlesForEach(List<Article> allArticles) {
        List<ArticleDto> result = new ArrayList<>();
        allArticles.forEach( article -> {
            ArticleDto mappedDto = ArticleMapper.mapToDto(article);
            result.add(mappedDto);
        });
        return result;
    }*/

    // GET /article/uuid-12-udasdasd
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDto> findById(@PathVariable(value = "id") String id) { // Optional Article might not be found
        Optional<Article> foundArticle = articleService.findById(id);
        if (foundArticle.isPresent()) {
            Article article = foundArticle.get();
            return ResponseEntity.ok(ArticleMapper.mapToDto(article));     // working with ArticleDto to not expose all properties?
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }


    @PostMapping
    public ResponseEntity<ArticleDto> create(@Valid @RequestBody ArticleCreationDto articleCreationDto) { // if not passed validation --> constraint violation exception --> bad request
        /* boolean articleIsValid = isArticleValid(articleCreationDto); // shift + F6 to rename all instances
        if (!articleIsValid) { // blank considers spaces "   "
            return ResponseEntity.badRequest().build();
        } */ // --> no need for this since passed ArticleDto will always be valid?
        // throw new IllegalStateException("Oh Oh, unexpected error");
        Article newArticle = articleService.create(ArticleMapper.mapArticleCreationDtoToEntity(articleCreationDto));
        // return ResponseEntity.ok(mapToDto(newArticle));
        return ResponseEntity.status(HttpStatus.CREATED).body(ArticleMapper.mapToDto(newArticle));
        // return ResponseEntity.created(URI.create("http://localhost:8080/api/article/" + newArticle.getId())).build();
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



    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") String id, @RequestBody ArticleDto articleDto) {
        if (!id.equals(articleDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        if (!articleService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article with id " + id + " not found");
        }
        // Article updatedArticle = getUpdatedArticle(articleDto, existingById.get());
        Article updatedArticle = ArticleMapper.mapArticleDtoToEntity(articleDto);
        Article savedArticle = articleService.update(updatedArticle);
        return ResponseEntity.ok(ArticleMapper.mapToDto(savedArticle));
    }

    private static Article getUpdatedArticle(ArticleDto articleDto, Article existingArticle) {
        Article updateRequestArticle = ArticleMapper.mapArticleDtoToEntity(articleDto);
        existingArticle.setCountryArticles(updateRequestArticle.getCountryArticles());
        existingArticle.setHeight(updateRequestArticle.getHeight());
        existingArticle.setLength(updateRequestArticle.getLength());
        existingArticle.setWidth(updateRequestArticle.getWidth());
        return existingArticle;
    }

    // delete über params oder gepasster id?
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable(value = "id") String id) {
        if (!articleService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        articleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        articleService.delete();
        return ResponseEntity.noContent().build();
    }

    //@DeleteMapping
    //public void delete(Article article) { articleService.delete(article); }
}
