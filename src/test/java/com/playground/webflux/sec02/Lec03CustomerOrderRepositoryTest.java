package com.playground.webflux.sec02;

import com.playground.webflux.sec02.repository.CustomerOrderRepository;
import com.playground.webflux.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;


public class Lec03CustomerOrderRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec03CustomerOrderRepositoryTest.class);


    @Autowired
    private CustomerOrderRepository customerOrderRepository;



    @Test
    public void productsOrderByCustomer() {
        this.customerOrderRepository.getProductsOrderByCustomer("mike")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();

    }

    @Test
    public void orderDetailsByProduct() {
        this.customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(dto -> Assertions.assertEquals(975, dto.amount()))
                .assertNext(dto -> Assertions.assertEquals(950, dto.amount()))
                .expectComplete()
                .verify();

    }


}
