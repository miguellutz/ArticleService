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
    public List<Article> findAll(){
        return articleRepository.findAll();
    }

    public Article save(String id,
                        String internationalArticleNumber,
                        double height,
                        double width,
                        double length) {

        Optional<Article> article = articleRepository.findById(id);
        if (article.isPresent()) {
            article.get().setInternationalArticleNumber(internationalArticleNumber)
                    .setHeight(height)
                    .setWidth(width)
                    .setLength(length);
            articleRepository.save(article.get()); // warum nicht einfach article?
            // warum hier nicht möglich article zu returnen?
        }
        Article newArticle = new Article();
        newArticle.setId(id)
                .setInternationalArticleNumber(internationalArticleNumber)
                .setHeight(height)
                .setWidth(width)
                .setLength(length);
        articleRepository.save(newArticle);
        return newArticle;
    }

    public Optional<Article> findById(String id){
        Optional<Article> article = articleRepository.findById(id);
        return article;
    }

    public void delete(Article article){
        articleRepository.deleteById(article.getId());
    }

    public String deleteById(String id){
        Optional<Article> foundArticle = articleRepository.findById(id);
        if (foundArticle.isPresent()) {
            articleRepository.deleteById(id);
            return "Article deleted";
        }
        return "Article not found";
    }
}
