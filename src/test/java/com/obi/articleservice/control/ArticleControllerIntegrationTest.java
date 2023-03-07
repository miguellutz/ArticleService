package com.obi.articleservice.control;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // no need to define (webEnvironment = WebEnvironment.DEFINE_PORT)?
public class ArticleControllerIntegrationTest {

    @LocalServerPort
    private Integer port;

    @Test
    public void printPortsInUse() {
        System.out.println(port);
    }

}
