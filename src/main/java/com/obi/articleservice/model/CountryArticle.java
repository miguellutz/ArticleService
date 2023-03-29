package com.obi.articleservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor //(Default Constructor) --> as soon as new constructor added need to add manually
public class CountryArticle {

    // should be automatically set by hibernate due @MapKey Annotation on Article
    @EmbeddedId
    private CountryArticleId id;
    private String title;
    private Boolean active; // work with classes instead of primitives to prevent default value

    @JsonIgnore
    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "id", nullable = false, insertable = false, updatable = false)
    private Article article;

    /*
    constructor with article null
    public CountryArticle(CountryArticleId id, String title, Boolean active) {
        this.id = id;
        this.title = title;
        this.active = active;
    }*/
}
