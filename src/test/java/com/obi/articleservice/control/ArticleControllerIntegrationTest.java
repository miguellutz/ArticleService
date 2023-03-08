package com.obi.articleservice.control;

import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// no need to define (webEnvironment = WebEnvironment.DEFINE_PORT)?
public class ArticleControllerIntegrationTest {

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void cleanDB() {
        articleRepository.deleteAll();
    }

    @LocalServerPort
    private Integer port;

    @DisplayName("Print current port")
    @Test
    public void printPortsInUse() {
        System.out.println(port);
    }

    @Autowired
    private TestRestTemplate restTemplate = new TestRestTemplate();

    /* @DisplayName("Return array of all JSON articles")
    @Test
    void findAll() throws Exception {
        // [{},{}]
        Article save = articleRepository.save(new Article(UUID.randomUUID().toString(), "dasdasd", 20.0, 20.0, 2.0));
        RequestBuilder request = MockMvcRequestBuilders.get("/api/articles");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$.[0].id").value(save.getId()));

    } */

    @Test
    void findById() {
        // GIVEN Article is in DB
        // ensure DB is empty
        assertThat(articleRepository.count()).isEqualTo(0);

        // write one article in DB
        String id = UUID.randomUUID().toString();
        articleRepository.save(new Article(id, "123", 2.0, 2.0, 2.0));

        // ensure DB count is 1
        assertThat(articleRepository.count()).isEqualTo(1);

        // WHEN request article by id
        // GET Request /api/article/${id}
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<ArticleDto> response = restTemplate.getForEntity("http://localhost:" + port + "/api/article/" + id, ArticleDto.class);

        // THEN assert article returned is equal to persistd one
        // assert that persisted article is returned
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @Test
    void create() { // tests package private by default
        assertThat(articleRepository.count()).isEqualTo(0);

        ArticleDto article = new ArticleDto(null, "123", 20.0, 20.0, 2.0);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ArticleDto> request = new HttpEntity<>(article, headers);

        ResponseEntity<ArticleDto> response = restTemplate.postForEntity("http://localhost:" + port + "/api/article", request, ArticleDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        ArticleDto persistedArticle = response.getBody();

        assertThat(persistedArticle.getId()).isNotBlank();
        assertThat(persistedArticle.getInternationalArticleNumber()).isEqualTo(article.getInternationalArticleNumber());
        assertThat(persistedArticle.getWidth()).isEqualTo(article.getWidth());
        assertThat(persistedArticle.getLength()).isEqualTo(article.getLength());
        assertThat(persistedArticle.getHeight()).isEqualTo(article.getHeight());

        assertThat(articleRepository.count()).isEqualTo(1);
    }

    private List<Article> parseToArticleList(String contentAsString) {
        return null;
    }

    // return 404 when GET non-existing article


}
