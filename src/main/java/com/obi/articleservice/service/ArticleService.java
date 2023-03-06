package com.obi.articleservice.service;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


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
    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public Article save(Article article) {
        if(article.getId()==null || article.getId().isBlank()){
            article.setId(UUID.randomUUID().toString());
        }
        return articleRepository.save(article);
    }

    public Optional<Article> findById(String id){
        return articleRepository.findById(id);
    }

    public void delete(Article article){
        articleRepository.deleteById(article.getId());
    }

    // Wie kann ich String mit message returnen und gleichzeitig aber auch gelöschtes Objekt?
    public void deleteById(String id){
       articleRepository.deleteById(id);
    }

    public boolean existsById(String id) {
        return articleRepository.existsById(id);
    }
}
