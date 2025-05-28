package com.playground.webflux.sec04;

public record ProductDto(
        Integer id,
        String description,
        Integer price
) {
}
