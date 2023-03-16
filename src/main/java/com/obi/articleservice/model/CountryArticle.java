package com.obi.articleservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
// @NoArgsConstructor (Default Constructor) --> as soon as new constructor added need to add manually
public class CountryArticle {

    // should be automatically set by hibernate due @MapKey Annotation on Article
    @Id
    private String id;
    private String title;
    private Boolean active; // work with classes instead of primitives to prevent default value

    @MapKey(name = "id")
    @ManyToOne
    private Article article;

}
