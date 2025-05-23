package com.playground.webflux.sec02;

import com.playground.webflux.sec02.entity.Customer;
import com.playground.webflux.sec02.entity.Product;
import com.playground.webflux.sec02.repository.CustomerRepository;
import com.playground.webflux.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;


public class Lec02ProductRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec02ProductRepositoryTest.class);


    @Autowired
    private ProductRepository productRepository;



    @Test
    public void findByPriceRange() {
        this.productRepository.findByPriceBetween(750, 1000)
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(3)
                .expectComplete()
                .verify();

    }

    @Test
    public void pageable() {
        this.productRepository.findBy(PageRequest.of(0, 3).withSort(Sort.by("price").ascending()))
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(200, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(250, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(300, p.getPrice()))
                .expectComplete()
                .verify();

    }







}
