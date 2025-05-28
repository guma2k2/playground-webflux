package com.playground.webflux.sec07;

import com.playground.webflux.sec07.dto.Product;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Map;

public class Lec04HeaderTest extends AbstractWebClient{


    private final WebClient client = createWebClient(builder -> builder.defaultHeader("caller-id", "order-service"));


    @Test
    public void defaultHeader() {
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }


    @Test
    public void overideHeader() {
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .header("caller-id", "new-value")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void headersWithMap() {
        var map = Map.of(
                "caller-id", "new-value",
                "some-key", "some-value"
        );
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .headers(h -> h.setAll(map))
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
