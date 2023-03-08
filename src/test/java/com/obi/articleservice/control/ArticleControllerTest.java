package com.obi.articleservice.control;

import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {

    @Autowired
    private ArticleRepository articleRepository;

    @BeforeEach
        // --> necessary?
    void cleanDB() { // to wipe DB for each test in case data left from another test
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
    private MockMvc mvc;

    @DisplayName("Return array of all JSON articles")
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

    }

    private List<Article> parseToArticleList(String contentAsString) {
        return null;
    }

    @DisplayName("Return JSON of article with id = 1")
    @Test
    public void findById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("api/articles/1"); // id hardcoden oder dynamisch
        MvcResult result = mvc.perform(request).andReturn();
        Assertions.assertEquals("{\"id\":\"1\",\"internationalArticleNumber\":\"123\",\"height\":1.0,\"width\":1.0,\"length\":1.0}", result.getResponse().getContentAsString());
    }

    @DisplayName("Return 202 Status code when deleting article")
    @Test
    public void delete() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = new Article(id, "123", 2.0, 2.0, 2.0);

        RequestBuilder request = MockMvcRequestBuilders.delete("api/articles/" + id);
        MvcResult result = mvc.perform(request).andReturn();
        Assertions.assertEquals("202", result.getResponse().getStatus());
    }

    // return 404 when GET non-existing article


}
