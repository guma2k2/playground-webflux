package com.playground.webflux.sec02.repository;

import com.playground.webflux.sec02.entity.Customer;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Flux<Customer> findByName(String name);

//    @Query("""
//        select c.id, c.name, c.email
//        from customer c
//        where c.email like concat('%', :email)
//    """)
//    Flux<Customer> findByEmailEndingWith(@Param("email") String email);


    Flux<Customer> findByEmailEndingWith(@Param("email") String email);
}
