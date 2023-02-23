package com.obi.articleservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
// @NoArgsConstructor (Default Constructor) --> as soon as new constructor added need to add manually
public class CountryArticle {

    @Id
    private String id;
    private String title;
    private Boolean active; // work with classes instead of primitives to prevent default value


}
