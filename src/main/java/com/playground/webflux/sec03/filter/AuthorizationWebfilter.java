package com.playground.webflux.sec03.filter;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Service
public class AuthenticationWebfilter implements WebFilter {

    private static final Map<String, Category> TOKEN_CATEGORY_MAP = Map.of(
            "secret123", Category.STANDARD,
            "secret456", Category.PRIME
    );
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("auth-token");
        if (Objects.nonNull(token) && TOKEN_CATEGORY_MAP.containsKey(token)) {
            return chain.filter(exchange);
        }
        return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));
    }
}
