package com.playground.webflux.sec03.dto;

public record CustomerDto(Integer id,
                          String name,
                          String email
) {
}
