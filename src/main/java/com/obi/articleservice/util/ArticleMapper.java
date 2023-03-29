package com.obi.articleservice.util;

import com.obi.articleservice.dto.ArticleCreationDto;
import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.dto.CountryArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.model.CountryArticle;
import com.obi.articleservice.model.CountryArticleId;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE) // not possible to instantiate class. Like NoArgsConstructor but private
public class ArticleMapper {    // no service since no own properties, just methods

    public static Article mapArticleDtoToEntity(ArticleDto articleDto) {
        Article article = new Article();
        article.setId(articleDto.getId());
        article.setInternationalArticleNumber(articleDto.getInternationalArticleNumber());
        article.setWidth(articleDto.getWidth());
        article.setLength(articleDto.getLength());
        article.setHeight(articleDto.getHeight());

        List<CountryArticle> countryArticles = mapCountryArticles(articleDto, article);
        article.setCountryArticles(countryArticles);
        return article;
    }

    public static Article mapArticleCreationDtoToEntity(ArticleCreationDto articleCreationDto) {
        Article article = new Article();
        article.setId(UUID.randomUUID().toString());
        article.setInternationalArticleNumber(articleCreationDto.getInternationalArticleNumber());
        article.setWidth(articleCreationDto.getWidth());
        article.setLength(articleCreationDto.getLength());
        article.setHeight(articleCreationDto.getHeight());
        article.setCountryArticles(mapArticleCreationCountryArticles(articleCreationDto, article));
        return article;
    }

    public static ArticleDto mapToDto(Article article) {
        return new ArticleDto(article.getId(), article.getInternationalArticleNumber(), article.getHeight(), article.getWidth(), article.getLength(), mapToCountryArticleDtos(article.getCountryArticles()));
    }

    public static List<ArticleDto> mapArticlesStream(List<Article> allArticles) {
        return allArticles.stream()
                .map(ArticleMapper::mapToDto)
                .collect(Collectors.toList());
    }

    private static List<CountryArticle> mapCountryArticles(ArticleDto articleDto, Article article) {
        List<CountryArticleDto> countryArticles = articleDto.getCountryArticles();
        return countryArticles.stream()
                .map(countryArticleDto -> mapCountryArticle(countryArticleDto, article))
                .collect(Collectors.toList());
    }

    private static CountryArticle mapCountryArticle(CountryArticleDto countryArticleDto, Article article) {
        CountryArticle countryArticle = new CountryArticle();
        countryArticle.setId(mapCountryArticleId(article.getId(), countryArticleDto.getCountry()));
        countryArticle.setTitle(countryArticleDto.getTitle());
        countryArticle.setActive(countryArticleDto.getActive());
        countryArticle.setArticle(article);

        return countryArticle;
    }

    private static CountryArticleId mapCountryArticleId(String id, String country) {
        CountryArticleId result = new CountryArticleId();
        result.setCountry(country);
        result.setId(id);
        return result;
    }

    private static List<CountryArticle> mapArticleCreationCountryArticles(ArticleCreationDto articleCreationDto, Article article) {
        List<CountryArticleDto> countryArticles = articleCreationDto.getCountryArticles();
        return countryArticles.stream()
                .map(countryArticleDto -> mapCountryArticle(countryArticleDto, article))
                .collect(Collectors.toList());
    }

    public static List<CountryArticleDto> mapToCountryArticleDtos(List<CountryArticle> countryArticles) {
        return countryArticles.stream()
                .map(countryArticle -> mapToCountryArticleDto(countryArticle))
                .collect(Collectors.toList());
    }

    private static CountryArticleDto mapToCountryArticleDto(CountryArticle countryArticle) {
        return new CountryArticleDto(countryArticle.getId().getCountry(), countryArticle.getTitle(), countryArticle.getActive());
    }
}
