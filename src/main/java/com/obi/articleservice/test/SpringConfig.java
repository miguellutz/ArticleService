package com.obi.articleservice.test;

import com.obi.articleservice.service.ArticleService;
import com.obi.articleservice.test.FirstService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean       // has business logic
    public FirstService firstService(){
        return new FirstService("dasdasd");
    }
}
