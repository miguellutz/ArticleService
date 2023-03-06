package com.obi.articleservice;

import com.obi.articleservice.service.ArticleService;
import com.obi.articleservice.test.FirstService;
import com.obi.articleservice.test.FourthService;
import com.obi.articleservice.test.SecondService;
import com.obi.articleservice.test.ThirdService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ArticleServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArticleServiceApplication.class, args);

    }

}
