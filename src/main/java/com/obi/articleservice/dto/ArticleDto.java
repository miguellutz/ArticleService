package com.obi.articleservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.obi.articleservice.model.CountryArticle;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
public class ArticleDto { // no need for noargsconstructor, possibility to hide properties otherwise exposed in normal article
    private final String id;        // final muss und darf nur einmal instantiert werden (best practice)
    @NotBlank(message = "International article number cannot be blank")
    private final String internationalArticleNumber;
    @NotNull(message = "Height cannot be null")
    private final Double height;
    @NotNull(message = "Width cannot be null")
    private final Double width;
    @NotNull(message = "Length cannot be null")
    private final Double length;

    private final List<CountryArticleDto> countryArticles;
}