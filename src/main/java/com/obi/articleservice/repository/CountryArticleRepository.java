package com.obi.articleservice.repository;

import com.obi.articleservice.model.CountryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryArticleRepository extends JpaRepository<CountryArticle, String> {
}
