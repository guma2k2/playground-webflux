package com.playground.webflux.sec02;

import com.playground.webflux.sec02.entity.Customer;
import com.playground.webflux.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


public class Lec01CustomRepositoryTest extends AbstractTest {

    private static final Logger log = LoggerFactory.getLogger(Lec01CustomRepositoryTest.class);


    @Autowired
    private CustomerRepository customerRepository;


    @Test
    public void findAll() {
        this.customerRepository.findAll()
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }


    @Test
    public void findById() {
        this.customerRepository.findById(2)
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals("mike", c.getName()))
                .expectComplete()
                .verify();
    }


    @Test
    public void findByName() {
        this.customerRepository.findByName("mike")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals(2, c.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmailEndingWith() {
        this.customerRepository.findByEmailEndingWith("e@gmail.com")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void insertAndDelete() {
        // insert
        Customer customer = new Customer();
        customer.setEmail("thuan@gmail.com");
        customer.setName("thuan");
        this.customerRepository.save(customer)
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertNotNull(c.getId()))
                .expectComplete()
                .verify();

        //count
        this.customerRepository.count()
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();

        // delete
        this.customerRepository.deleteById(11)
                .then(this.customerRepository.count())
                .as(StepVerifier::create)
                .expectNext(10L)
                .expectComplete()
                .verify();

    }

    @Test
    public void updateCustomer() {
        this.customerRepository.findByName("ethan")
                .doOnNext(c -> c.setName("ethann"))
                .flatMap(c -> this.customerRepository.save(c))
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(c -> Assertions.assertEquals(c.getName(), "ethann"))
                .expectComplete()
                .verify();

    }





}
