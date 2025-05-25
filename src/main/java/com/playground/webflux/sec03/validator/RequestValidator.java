package com.playground.webflux.sec03.validator;

import com.playground.webflux.sec03.dto.CustomerDto;
import com.playground.webflux.sec03.exceptions.ApplicationException;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {


    public static UnaryOperator<Mono<CustomerDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationException.missingName())
                .filter(hasValidEmail())
                .switchIfEmpty(ApplicationException.missingValidEmail());
    }

    private static Predicate<CustomerDto> hasName() {
        return customerDto -> Objects.nonNull(customerDto.name());
    }

    private static Predicate<CustomerDto> hasValidEmail() {
        return customerDto -> Objects.nonNull(customerDto.email()) && customerDto.email().contains("@");
    }

}
