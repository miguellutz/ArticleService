package com.obi.articleservice.control;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.service.ArticleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// never implement more than 5 dependencies --> if necessary split into different classes
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ArticleService articleService;

    private List<Article> articleList;

    @DisplayName("Test whether dependencies boot and tests run")
    @Test
    void testStartup() {
        // empty by design
    }

    @Test
    void findById() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        RequestBuilder request = get("/api/article/" + id);

        Article foundArticle = new Article(id, "123", 20.0, 2.0, 2.0);
        Mockito.when(articleService.findById(id)).thenReturn(Optional.of(foundArticle));

        //WHEN //THEN
        // {"id": "283746283746", "international"}
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.internationalArticleNumber").value(foundArticle.getInternationalArticleNumber()))
                .andExpect(jsonPath("$.height").value(foundArticle.getHeight()))
                .andExpect(jsonPath("$.width").value(foundArticle.getWidth()))
                .andExpect(jsonPath("$.length").value(foundArticle.getLength()));
        Mockito.verify(articleService, Mockito.times(1)).findById(id);  // verify that articleService mock was used once
    }

    @Test
    void findByNonExistingId() throws Exception {

        RequestBuilder request = get("/api/article/1234");
        Mockito.when(articleService.findById("1234")).thenReturn(Optional.empty());

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    void findAll() throws Exception {
        RequestBuilder request = get("/api/article");

        /* MvcResult result = mvc.perform(get("/api/article")).andReturn();
        result.getResponse().getContentAsString();*/

        Mockito.when(articleService.findAll()).thenReturn(articleList);
        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void save() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String id = UUID.randomUUID().toString();
        ArticleDto articleDto = new ArticleDto(id, "123", 2.0, 2.0, 2.0);
        Article article = new Article(id, "123", 2.0, 2.0, 2.0);


        Mockito.when(articleService.save(article)).thenReturn(article);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/article")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(articleDto));

        mvc.perform(request)
                .andExpect(status().isOk()) // --> not .isCreated() 201
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.internationalArticleNumber").value("123"))
                .andExpect(jsonPath("$.height").value(2.0))
                .andExpect(jsonPath("$.width").value(2.0))
                .andExpect(jsonPath("$.height").value(2.0));
    }

    @Test
    void update() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        String id = UUID.randomUUID().toString();
        Article article = new Article(id, "123", 2.0, 2.0, 2.0);

        Mockito.when(articleService.existsById(id)).thenReturn(true);
        Mockito.when(articleService.save(article)).thenReturn(article);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity requestUpdate = new HttpEntity(article, headers);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/article/" + id, requestUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(article));

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        String id = UUID.randomUUID().toString();
        Article article = new Article(id, "123", 2.0, 2.0, 2.0);

        //doNothing().when(articleService).deleteById(id);
        // Mockito.when(articleService.deleteById(id)).thenReturn(void); --> how with mockito?
        Mockito.when(articleService.existsById(id)).thenReturn(true);
        willDoNothing().given(articleService).deleteById(id);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/article/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mvc.perform(request)
            .andExpect(status().isOk());
    }

    @Test
    void deleteNonExistingArticle() throws Exception {
        Mockito.when(articleService.existsById("123")).thenReturn(false);

        RequestBuilder request = MockMvcRequestBuilders.delete("api/article/123");

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

}
