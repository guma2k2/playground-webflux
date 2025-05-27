package com.playground.webflux.sec03.config;

import com.playground.webflux.sec03.dto.CustomerDto;
import com.playground.webflux.sec03.exceptions.ApplicationException;
import com.playground.webflux.sec03.service.CustomerService;
import com.playground.webflux.sec03.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
public class CustomerRequestHandler {

    @Autowired
    private CustomerService customerService;

    public Mono<ServerResponse> allCustomers(ServerRequest request) {
//        request.pathVariable();
//        request.queryParam();
//        request.headers();
        return customerService.getAllCustomers()
                .as(flux -> ServerResponse.ok().body(flux, CustomerDto.class));
    }



    public Mono<ServerResponse> paginatedCustomer(ServerRequest request) {
        Integer page = request.queryParam("page").map(Integer::parseInt).orElse(1);
        Integer size = request.queryParam("size").map(Integer::parseInt).orElse(3);

        return customerService.getAllCustomers(page, size)
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(customerService::saveCustomer)
                .flatMap(ServerResponse.ok()::bodyValue);
    }


    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(mono -> customerService.updateCustomer(id, mono))
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        int id = Integer.parseInt(request.pathVariable("id"));
        return customerService.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then(ServerResponse.ok().build());
    }
}
