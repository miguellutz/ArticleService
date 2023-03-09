package com.obi.articleservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@AllArgsConstructor
@NoArgsConstructor
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
}