package com.playground.webflux.sec03.config;

import com.playground.webflux.sec03.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {


    @Autowired
    private CustomerRequestHandler customerRequestHandler;

    @Bean
    public RouterFunction<ServerResponse> customRoutes() {
        return RouterFunctions.route()
                .GET("/customers", customerRequestHandler::allCustomers)
                .GET("/customers/paginated", customerRequestHandler::paginatedCustomer)
                .GET("/customers/{id}", customerRequestHandler::getCustomer)
                .POST("/customers", customerRequestHandler::saveCustomer)
                .PUT("/customers/{id}", customerRequestHandler::updateCustomer)
                .DELETE("/customers/{id}", customerRequestHandler::deleteCustomer)
                .build();


    }
}
