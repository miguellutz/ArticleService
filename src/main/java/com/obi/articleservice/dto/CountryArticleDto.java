package com.obi.articleservice.dto;

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
public class CountryArticleDto {

    @Id
    private String id;
    private String title;
    private Boolean active; // work with classes instead of primitives to prevent default value


}
