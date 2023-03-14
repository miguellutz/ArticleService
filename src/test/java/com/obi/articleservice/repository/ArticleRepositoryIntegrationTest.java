package com.obi.articleservice.repository;

import com.obi.articleservice.model.Article;
import static org.assertj.core.api.Assertions.assertThat;   // static imports when importing specific method
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;
// TODO Use DataJpaTest
@SpringBootTest
public class ArticleRepositoryIntegrationTest {
    @Autowired      // is dependent on ArticleRepository --> needs to be injected by Spring
    private ArticleRepository articleRepository;

    @Test
    public void testSaveArticle() {
        Article article = new Article()
                .setId(UUID.randomUUID().toString())        // method chaining due to lombok and Article return type instead of void
                .setInternationalArticleNumber("123")
                .setHeight(2.0)
                .setLength(2.0)
                .setWidth(2.0);
        articleRepository.save(article);
    }
    @Test
    public void testReadArticle() {

        // GIVEN
        String id = UUID.randomUUID().toString();
        assertThat(articleRepository.findById(id)).isNotPresent();

        Article article = new Article()
                .setId(id)        // method chaining due to lombok and Article return type instead of void
                .setInternationalArticleNumber("123")
                .setHeight(2.0)
                .setLength(2.0)
                .setWidth(2.0);
        articleRepository.save(article);

        // WHEN
        Optional<Article> foundById = articleRepository.findById(article.getId());  // never pass null, never return null!!!

        // THEN
        assertThat(foundById).isPresent();

        /*foundById.ifPresentOrElse(
                foundArticle -> System.out.println(foundArticle.getId()+ " is present"),
                () -> System.out.println("not present"));*/
    }
}
