package com.obi.articleservice.control;

import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.dto.CountryArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.model.CountryArticle;
import com.obi.articleservice.model.CountryArticleId;
import com.obi.articleservice.repository.ArticleRepository;
import com.obi.articleservice.util.TestDataUtil;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleControllerIntegrationTest {

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
    void beforeTest() {
        articleRepository.deleteAll();
        apiUrl = "http://localhost:" + port + "/api/article";
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

    private String apiUrl;

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
        articleRepository.save(TestDataUtil.createArticle());

        // ensure DB count is 1
        assertThat(articleRepository.count()).isEqualTo(1);

        // WHEN request article by id
        // GET Request /api/article/${id}
        ResponseEntity<ArticleDto> response = restTemplate.getForEntity(apiUrl + "/" + id, ArticleDto.class);

        // THEN assert article returned is equal to persisted one
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

        String url = apiUrl + "/1234";
        ResponseEntity<ArticleDto> response = restTemplate.getForEntity(url, ArticleDto.class);

        // THEN assert HttpStatusCode is equal to 404
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
    }

    @DisplayName("Get all articles and return 200 status code even when no articles persisted")
    @Test
    void findAllEmptyDb() {
        // GIVEN no articles in DB
        // assert DB is empty
        assertThat(articleRepository.count()).isEqualTo(0);

        // WHEN request all articles
        // GET request /api/article
        ResponseEntity<ArticleDto[]> response = restTemplate.getForEntity(apiUrl, ArticleDto[].class);

        // THEN assert HttpStatusCode is equal to 200
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
    }

    @DisplayName("Get all persisted articles and return 200 status code")
    @Test
    void findAll() {

        // GIVEN
        assertThat(articleRepository.count()).isEqualTo(0);

        articleRepository.save(TestDataUtil.createArticle());
        articleRepository.save(TestDataUtil.createArticle());
        articleRepository.save(TestDataUtil.createArticle());
        assertThat(articleRepository.count()).isEqualTo(3);

        // WHEN get find all
        ResponseEntity<ArticleDto[]> response = restTemplate.getForEntity(apiUrl, ArticleDto[].class);

        // THEN ensure all articles are returned
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(200));
        ArticleDto[] articleDtos = response.getBody();
        assertThat(articleDtos).isNotNull();
        assertThat(articleDtos.length).isEqualTo(3); // Arrays --> .length, Lists --> .size()
        /*assertThat(persistedArticle.getId()).isEqualTo(id);
        assertThat(persistedArticle.getInternationalArticleNumber()).isEqualTo("123");*/
    }

    @DisplayName("Valid article is created")
    @Test
    void create() { // tests package private by default
        assertThat(articleRepository.count()).isEqualTo(0);
        List<CountryArticleDto> countryArticleDtos = new ArrayList<>();
        countryArticleDtos.add(new CountryArticleDto("DE", "Kovalex", false));
        ArticleDto articleDto = new ArticleDto(null, "123", 2.0, 2.0, 2.0, countryArticleDtos);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ArticleDto> request = new HttpEntity<>(articleDto, headers);

        ResponseEntity<ArticleDto> response = restTemplate.postForEntity(apiUrl, request, ArticleDto.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));

        assertThat(articleRepository.count()).isEqualTo(1);

        ArticleDto persistedArticle = response.getBody();
        String generatedId = persistedArticle.getId();
        assertThat(generatedId).isNotBlank();

        Article foundById = articleRepository.findById(generatedId).orElseThrow();
        assertThat(foundById.getInternationalArticleNumber()).isEqualTo(articleDto.getInternationalArticleNumber());
        assertThat(foundById.getWidth()).isEqualTo(articleDto.getWidth());
        assertThat(foundById.getLength()).isEqualTo(articleDto.getLength());
        assertThat(foundById.getHeight()).isEqualTo(articleDto.getHeight());
    }

    @DisplayName("Ensure invalid article is not created and returns bad request 400")
    @Test
    void createInvalidArticle() { // tests package private by default
        // GIVEN invalid article with internationalArticleNumber of null
        assertThat(articleRepository.count()).isEqualTo(0);
        List<CountryArticleDto> countryArticleDtos = new ArrayList<>();
        countryArticleDtos.add(new CountryArticleDto("DE", "Kovalex", false));

        HttpEntity<ArticleDto> request = new HttpEntity<>(
                new ArticleDto(null, null, 20.0, 20.0, 2.0, countryArticleDtos), new HttpHeaders());

        // WHEN post invalid article
        ResponseEntity<ArticleDto> response = restTemplate.postForEntity(apiUrl, request, ArticleDto.class);

        // THEN return 400 bad request and do not persist article in DB
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(articleRepository.count()).isEqualTo(0);

        // GIVEN invalid article with empty internationalArticleNumber
        HttpEntity<ArticleDto> request2 = new HttpEntity<>(
                new ArticleDto(null, "  ", 20.0, 20.0, 2.0, countryArticleDtos), new HttpHeaders());

        // WHEN post invalid article
        ResponseEntity<ArticleDto> response2 = restTemplate.postForEntity(apiUrl, request2, ArticleDto.class);

        // THEN return 400 bad request and do not persist article in DB
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(articleRepository.count()).isEqualTo(0);
    }

    @DisplayName("Ensure invalid article is not created and returns bad request 400")
    @ParameterizedTest
    @MethodSource({"testWithInvalidArticles"})
    void testPersistInvalidArticles(ArticleDto invalidArticle) { // tests package private by default
        assertThat(articleRepository.count()).isEqualTo(0);

        ParameterizedTypeReference<Map<String, String>> responseType = new ParameterizedTypeReference<>() {
        };

        HttpEntity<ArticleDto> request = new HttpEntity<>(invalidArticle, new HttpHeaders());
        //ResponseEntity<ArticleDto> response = restTemplate.postForEntity("http://localhost:" + port + "/api/article",request, ArticleDto.class); --> response is Error Map and not ArticleDto (deserialization error)
        ResponseEntity<Map<String, String>> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, responseType);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(400));
        assertThat(articleRepository.count()).isEqualTo(0);
    }

    public static Stream<Arguments> testWithInvalidArticles() {

        List<CountryArticleDto> countryArticleDtos = new ArrayList<>();
        countryArticleDtos.add(new CountryArticleDto("DE", "Kovalex", false));

        return Stream.of(
                Arguments.of(new ArticleDto(null, null, 20.0, 20.0, 2.0, countryArticleDtos)),
                Arguments.of(new ArticleDto(null, "", 20.0, 20.0, 2.0, countryArticleDtos)),
                Arguments.of(new ArticleDto(null, " ", 20.0, 20.0, 2.0, countryArticleDtos)),
                Arguments.of(new ArticleDto(null, "123", null, 20.0, 2.0, countryArticleDtos)),
                Arguments.of(new ArticleDto(null, "123", 20.0, null, 2.0, countryArticleDtos)),
                Arguments.of(new ArticleDto(null, "123", 20.0, 20.0, null, countryArticleDtos))
        );
    }

    @DisplayName("Ensure existing article is updated")
    @Test
    void update() {
        // GIVEN ensure article exists in DB
        assertThat(articleRepository.count()).isEqualTo(0);

        String id = UUID.randomUUID().toString();

        // create Article
        Article newArticle = new Article();
        newArticle.setId(id);
        //create and set countryArticles
        List<CountryArticle> countryArticles = new ArrayList<>();
        countryArticles.add(new CountryArticle(new CountryArticleId(null, "DE"), "Kovalex", true, newArticle));
        countryArticles.add(new CountryArticle());
        newArticle.setCountryArticles(countryArticles);
        // set internetionalArticleNumger width
        //, "123", 2.0, 2.0, 2.0, countryArticles

        articleRepository.save(newArticle);

        assertThat(articleRepository.count()).isEqualTo(1);

        // WHEN article is updated via PUT Request
        newArticle.setInternationalArticleNumber("321");

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Article> requestUpdate = new HttpEntity<>(newArticle, headers);
        ResponseEntity<ArticleDto> response = restTemplate.exchange(apiUrl + "/" + id, HttpMethod.PUT, requestUpdate, ArticleDto.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        // THEN ensure article is updated in DB
        Article updatedArticleFromDB = articleRepository.findById(id).orElseThrow();
        assertThat(updatedArticleFromDB.getInternationalArticleNumber()).isEqualTo("321");
    }

    @DisplayName("Ensure update on non-existing article throws 404 not found")
    @Test
    void updateNonExistingArticle() {
        // GIVEN no articles in DB
        assertThat(articleRepository.count()).isEqualTo(0);

        // WHEN  non existing article is updated
        ArticleDto articleDto = new ArticleDto("123", "123", 2.0, 2.0, 2.0, new ArrayList<>());

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<ArticleDto> requestUpdate = new HttpEntity<>(articleDto, headers);
        ResponseEntity<?> response = restTemplate.exchange(apiUrl + "/123", HttpMethod.PUT, requestUpdate, Void.class);

        // THEN return 404 not found
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @DisplayName("Ensure update on article with other id than path throws 400 bad request")
    @Test
    void updateIdMismatchArticle() {
        // GIVEN article in DB
        Article article = TestDataUtil.createArticle();
        articleRepository.save(article);
        assertThat(articleRepository.count()).isEqualTo(1);

        // WHEN update on article with different path
        Article foundArticle = articleRepository.findById("123").get();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Article> requestUpdate = new HttpEntity<>(foundArticle, headers);
        ResponseEntity<?> response = restTemplate.exchange(apiUrl + "1", HttpMethod.PUT, requestUpdate, Void.class);

        // THEN return bad request
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("Ensure deletion of article returns 200")
    @Test
    void delete() {
        assertThat(articleRepository.count()).isEqualTo(0);

        String id = UUID.randomUUID().toString();
        Article article = new Article(id, "123", 2.0, 2.0, 2.0, new ArrayList<>());
        articleRepository.save(article);

        assertThat(articleRepository.count()).isEqualTo(1);

        HttpEntity<Article> requestDeletion = new HttpEntity<>(article);
        ResponseEntity<Void> response = restTemplate.exchange(apiUrl + "/" + id, HttpMethod.DELETE, requestDeletion, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(articleRepository.count()).isEqualTo(0);
    }

    @DisplayName("Ensure deletion of non-existing article returns 400 bad request")
    @Test
    void deleteNonExistingArticle() {
        // GIVEN no articles in DB
        assertThat(articleRepository.count()).isEqualTo(0);

        // WHEN Delete request to non existing id
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Article> requestDeletion = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(apiUrl+ "/" + UUID.randomUUID().toString(), HttpMethod.DELETE, requestDeletion, Void.class);

        // THEN return 404 not found
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
