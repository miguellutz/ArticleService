package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ArticleService {       // bean: class with business logic (spring / frameworks)

    // no need for @Autowired due to bean detection from spring boot
    private final ArticleRepository articleRepository;

    /*
    @Autowired
    public ArticleService(@Autowired ArticleRepository articleRepository,@Autowired CountryArticleRepository countryArticleRepository) {
        this.articleRepository = articleRepository;
        this.countryArticleRepository = countryArticleRepository;
    }
    */

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
        if (article.isPresent()) {
            article.get().setLength(3);
            return articleRepository.save(article.get());
        }
        return null;
    }

    public void deleteArticle() {
        articleRepository.deleteById("1234");
    }


    public Article save(Article article){
        return null;
    }

    public void delete(Article article){
        deleteById(article.getId());
    }

    public Optional<Article> findById(String id){
        return Optional.empty();
    }

    public void deleteById(String id){

    }

    public List<Article> findAll(){
        return Collections.EMPTY_LIST;
    }

}
