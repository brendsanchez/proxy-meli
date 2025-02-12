package com.ml.proxy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@TestPropertySource(locations = "classpath:application-local.properties")
class ProxyTest {

    private static final GenericContainer<?> redisContainer =
            new GenericContainer<>(DockerImageName.parse("redis:7.2"))
                    .withExposedPorts(6379);

    private WebTestClient webTestClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @BeforeAll
    static void setup() {
        redisContainer.start();

        System.setProperty("spring.redis.host", redisContainer.getHost());
        System.setProperty("spring.redis.port", redisContainer.getMappedPort(6379).toString());
    }

    @BeforeEach
    void init() {
        this.webTestClient = WebTestClient.bindToServer()
                .baseUrl("https://api.mercadolibre.com")
                .build();
    }

    @Test
    void givenValidRequest_whenCallProxy_thenRoutesCorrectly() {
        webTestClient.get()
                .uri("/products/search?status=active&site_id=MLA&product_identifier=0123456789")
                .exchange()
                .expectStatus()
                .isOk();
    }
}
