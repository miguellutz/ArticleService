package com.obi.articleservice.util;

import com.obi.articleservice.dto.ArticleCreationDto;
import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.dto.CountryArticleDto;
import com.obi.articleservice.model.Article;
import static org.assertj.core.api.Assertions.assertThat;

import com.obi.articleservice.model.CountryArticle;
import com.obi.articleservice.model.CountryArticleId;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArticleMapperTest {

    @Test
    void mapArticleDtoToEntity() {

        // GIVEN dto
        List<CountryArticleDto> countryArticleDtos = new ArrayList<>();
        CountryArticleDto countryArticleDto1 = new CountryArticleDto("DE", "Kovalex", true);
        CountryArticleDto countryArticleDto2 = new CountryArticleDto("AU", "Decking board", false);

        countryArticleDtos.add(countryArticleDto1);
        countryArticleDtos.add(countryArticleDto2);

        ArticleDto articleDto = new ArticleDto("123", "123", 1.0, 2.0, 3.0, countryArticleDtos);

        /*Article article = new Article();
        article.setId("123");
        List<CountryArticle> countryArticles = new ArrayList<>();
        CountryArticle countryArticle1 = new CountryArticle(new CountryArticleId(null, "DE"), "Kovalex", true, article);
        CountryArticle countryArticle2 = new CountryArticle(new CountryArticleId(null, "AU"), "Decking board", false, article);
        countryArticles.add(countryArticle1);
        countryArticles.add(countryArticle2);*/


        // WHEN mapped to entity
        Article mappedToEntity = ArticleMapper.mapArticleDtoToEntity(articleDto);

        // THEN ensure all properties are set
        assertThat(mappedToEntity.getId()).isEqualTo("123");
        assertThat(mappedToEntity.getInternationalArticleNumber()).isEqualTo("123");
        assertThat(mappedToEntity.getHeight()).isEqualTo(1.0);
        assertThat(mappedToEntity.getWidth()).isEqualTo(2.0);
        assertThat(mappedToEntity.getLength()).isEqualTo(3.0);
        assertThat(mappedToEntity.getCountryArticles().size()).isEqualTo(2);
    }

    @Test
    void mapArticleCreationDtoToEntity() {

        // Given articleCreationDto
        List<CountryArticleDto> countryArticleDtos = new ArrayList<>();
        CountryArticleDto countryArticleDto1 = new CountryArticleDto("DE", "Kovalex", true);
        CountryArticleDto countryArticleDto2 = new CountryArticleDto("AU", "Decking board", false);
        countryArticleDtos.add(countryArticleDto1);
        countryArticleDtos.add(countryArticleDto2);

        ArticleCreationDto articleCreationDto = new ArticleCreationDto("123", 1.0, 2.0, 3.0, countryArticleDtos);

        // WHEN articleCreationDto is mapped to entity
        Article mappedToEntity = ArticleMapper.mapArticleCreationDtoToEntity(articleCreationDto);

        //THEN ensure all properties are set
        assertThat(mappedToEntity.getId()).isNotEmpty();
        assertThat(mappedToEntity.getInternationalArticleNumber()).isEqualTo("123");
        assertThat(mappedToEntity.getHeight()).isEqualTo(1.0);
        assertThat(mappedToEntity.getWidth()).isEqualTo(2.0);
        assertThat(mappedToEntity.getLength()).isEqualTo(3.0);
        assertThat(mappedToEntity.getCountryArticles().size()).isEqualTo(2);
    }

    @Test
    void mapToDto() {
        // GIVEN article
        Article article = new Article();
        article.setId("123");

        List<CountryArticle> countryArticles = new ArrayList<>();
        CountryArticle countryArticle1 = new CountryArticle(new CountryArticleId(null, "DE"), "Kovalex", true, article);
        CountryArticle countryArticle2 = new CountryArticle(new CountryArticleId(null, "AU"), "Decking board", false, article);
        countryArticles.add(countryArticle1);
        countryArticles.add(countryArticle2);

        article.setInternationalArticleNumber("123");
        article.setHeight(1.0);
        article.setWidth(2.0);
        article.setLength(3.0);
        article.setCountryArticles(countryArticles);

        // WHEN article is mapped to DTO
        ArticleDto articleDto = ArticleMapper.mapToDto(article);

        // THEN ensure all properties are set
        assertThat(articleDto.getId()).isEqualTo("123");
        assertThat(articleDto.getInternationalArticleNumber()).isEqualTo("123");
        assertThat(articleDto.getHeight()).isEqualTo(1.0);
        assertThat(articleDto.getWidth()).isEqualTo(2.0);
        assertThat(articleDto.getLength()).isEqualTo(3.0);
        assertThat(articleDto.getCountryArticles().size()).isEqualTo(2);
    }
}