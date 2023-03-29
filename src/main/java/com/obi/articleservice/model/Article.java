package com.obi.articleservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "article")
// @NoArgsConstructor --> default constructor
@Data      // für standard plain old java objects (pojos) --> getter, setter, noargs... (von Lombok)
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id // for mapping id to DB entity for articleRepository.findById
    private String id; // final muss und darf nur einmal instantiert werden (best practice)
    @NotBlank(message = "International article number cannot be blank") // with @NotBlank --> NotEmpty and NotNull redundant
    private String internationalArticleNumber;
    @NotNull(message = "Height cannot be null")
    private Double height;
    @NotNull(message = "Width cannot be null")
    private Double width;
    @NotNull(message = "Length cannot be null")
    private Double length;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.EAGER) // only loaded if necessary (when using getter)
    private List<CountryArticle> countryArticles;

    /*
    public Article(String id, String internationalArticleNumber, Double height, Double width, Double length, List<CountryArticle> countryArticles) {
        this.id = id;
        this.internationalArticleNumber = internationalArticleNumber;
        this.height = height;
        this.width = width;
        this.length = length;
        this.countryArticles = countryArticles;
        // ensure article is correctly referenced in CountryArticle
        this.countryArticles.forEach(countryArticle -> countryArticle.setArticle(this));
    }*/
}