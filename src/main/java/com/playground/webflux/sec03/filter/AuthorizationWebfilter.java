package com.playground.webflux.sec03.filter;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Service
@Order(2)
public class AuthorizationWebfilter implements WebFilter {

    private static final Map<String, Category> TOKEN_CATEGORY_MAP = Map.of(
            "secret123", Category.STANDARD,
            "secret456", Category.PRIME
    );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Category category = exchange.getAttributeOrDefault("category", Category.STANDARD);
        return switch (category){
            case STANDARD -> standard(exchange, chain);
            case PRIME -> prime(exchange, chain);
        };
    }


    public Mono<Void> prime(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange);
    }

    public Mono<Void> standard(ServerWebExchange exchange, WebFilterChain chain) {
        boolean isGet = HttpMethod.GET.equals(exchange.getRequest().getMethod());
        if (isGet) {
            return chain.filter(exchange);
        }
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));

    }
}
