package com.playground.webflux.sec02.repository;

import com.playground.webflux.sec02.dto.OrderDetails;
import com.playground.webflux.sec02.entity.Customer;
import com.playground.webflux.sec02.entity.CustomerOrder;
import com.playground.webflux.sec02.entity.Product;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface CustomerOrderRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {



    @Query("""
        select p.*
        from customer c 
        inner join customer_order co on c.id = co.customer_id
        inner join product p on p.id = co.product_id
        where c.name = :name
    """)
    Flux<Product> getProductsOrderByCustomer(String name);



    @Query("""
        SELECT
            co.order_id,
            c.name AS customer_name,
            p.description AS product_name,
            co.amount,
            co.order_date
        FROM
            customer c
        INNER JOIN customer_order co ON c.id = co.customer_id
        INNER JOIN product p ON co.product_id = p.id
        WHERE
            p.description = :description
        ORDER BY co.amount DESC
        """)
    Flux<OrderDetails> getOrderDetailsByProduct(String description);
}
