package com.playground.webflux.sec03.controller;


import com.playground.webflux.sec03.dto.CustomerDto;
import com.playground.webflux.sec03.exceptions.ApplicationException;
import com.playground.webflux.sec03.service.CustomerService;
import com.playground.webflux.sec03.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @GetMapping
    public Flux<CustomerDto> allCustomers() {
        return customerService.getAllCustomers();
    }


    @GetMapping("paginated")
    public Mono<List<CustomerDto>> allCustomers(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        return customerService.getAllCustomers(page, size).collectList();
    }

    @GetMapping("{id}")
    public Mono<CustomerDto> getCustomer(@PathVariable Integer id) {
        return this.customerService.getCustomerById(id)
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }


    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> customerDto) {
        return customerDto.transform(RequestValidator.validate())
                .as(mono -> customerService.saveCustomer(mono));
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> updateCustomer(@RequestBody Mono<CustomerDto> customerDto, @PathVariable("id") Integer id) {
        return customerDto.transform(RequestValidator.validate())
                .as(validReq -> customerService.updateCustomer(id, validReq))
                .switchIfEmpty(ApplicationException.customerNotFound(id));
    }

    @DeleteMapping("{id}")
    public Mono<Void> deleteCustomer(@PathVariable Integer id) {
        return customerService.deleteCustomerById(id)
                .filter(b -> b == true)
                .switchIfEmpty(ApplicationException.customerNotFound(id))
                .then();
    }
}
