package com.obi.articleservice.control;

import com.obi.articleservice.dto.ArticleDto;
import com.obi.articleservice.model.Article;
import com.obi.articleservice.repository.ArticleRepository;
import com.obi.articleservice.service.ArticleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// never implement more than 5 dependencies --> if necessary split into different classes
@WebMvcTest(ArticleController.class)
public class ArticleControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ArticleService articleService;

    @Test
    void testStartup() {
        // empty by design
    }

    @Test
    void findById() throws Exception {
        // GIVEN
        String id = UUID.randomUUID().toString();
        RequestBuilder request = MockMvcRequestBuilders.get("/api/article/" + id);

        Article foundArticle = new Article(id, "3242", 20.0, 2.0, 2.0);
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


}
