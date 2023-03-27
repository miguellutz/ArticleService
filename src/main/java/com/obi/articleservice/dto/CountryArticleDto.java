package com.obi.articleservice.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
// @NoArgsConstructor (Default Constructor) --> as soon as new constructor added need to add manually
public class CountryArticleDto {

    private final String country;
    private final String title;
    private final Boolean active; // work with classes instead of primitives to prevent default value
}
