package com.obi.articleservice.control;

import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

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

    @DisplayName("Find persisted article and return 200 status code")
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
        ResponseEntity<ArticleDto> response = restTemplate.getForEntity("http://localhost:" + port + "/api/article/" + id, ArticleDto.class);

        // THEN assert article returned is equal to persistd one
        // assert that persisted article is returned
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @DisplayName("Find non existing article and return 404 not found")
    @Test
    void findNonExistingId() {
        // GIVEN Article is in DB
        // ensure DB is empty
        assertThat(articleRepository.count()).isEqualTo(0);

        // WHEN request article by id
        // GET Request /api/article/${id}
        ResponseEntity<ArticleDto> response = restTemplate.getForEntity("http://localhost:" + port + "/api/article/1234", ArticleDto.class);

        // THEN assert HttpStatusCode is equal to 404
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @DisplayName("Valid article is created")
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

    @DisplayName("Ensure invalid article is not created and returns bad request 400")
    @Test
    void createInvalidArticle() { // tests package private by default
        assertThat(articleRepository.count()).isEqualTo(0);

        HttpEntity<ArticleDto> request = new HttpEntity<>(
                new ArticleDto(null, null, 20.0, 20.0, 2.0), new HttpHeaders());
        ResponseEntity<ArticleDto> response = restTemplate.postForEntity("http://localhost:" + port + "/api/article", request, ArticleDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(articleRepository.count()).isEqualTo(0);

        HttpEntity<ArticleDto> request2 = new HttpEntity<>(
                new ArticleDto(null, "  ", 20.0, 20.0, 2.0), new HttpHeaders());
        ResponseEntity<ArticleDto> response2 = restTemplate.postForEntity("http://localhost:" + port + "/api/article", request2, ArticleDto.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(articleRepository.count()).isEqualTo(0);
    }

    @DisplayName("Ensure invalid article is not created and returns bad request 400")
    @ParameterizedTest
    @MethodSource({"testWithInvalidArticles"})
    void testPersistInvalidArticles(ArticleDto invalidArticle) { // tests package private by default
        assertThat(articleRepository.count()).isEqualTo(0);

        HttpEntity<ArticleDto> request = new HttpEntity<>(invalidArticle, new HttpHeaders());
        ResponseEntity<ArticleDto> response = restTemplate.postForEntity("http://localhost:" + port + "/api/article", request, ArticleDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(articleRepository.count()).isEqualTo(0);
    }

    public static Stream<Arguments> testWithInvalidArticles() {
        return Stream.of(
                Arguments.of(new ArticleDto(null, null, 20.0, 20.0, 2.0)),
                Arguments.of(new ArticleDto(null, "", 20.0, 20.0, 2.0)),
                Arguments.of(new ArticleDto(null, " ", 20.0, 20.0, 2.0)),
                Arguments.of(new ArticleDto(null, "123", null, 20.0, 2.0)),
                Arguments.of(new ArticleDto(null, "123", 20.0, null, 2.0)),
                Arguments.of(new ArticleDto(null, "123", 20.0, 20.0, null))
        );
    }
}
