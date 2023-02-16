package com.obi.articleservice.repository;

import com.obi.articleservice.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,String> { // Entity = Article, id of type String

}
