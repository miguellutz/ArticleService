package com.obi.articleservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@RequiredArgsConstructor
public class ArticleCreationDto { // no need for noargsconstructor, possibility to hide properties otherwise exposed in normal article
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