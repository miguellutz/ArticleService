package com.obi.articleservice.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CountryArticleId implements Serializable {
    private String id;
    private String country;
}
