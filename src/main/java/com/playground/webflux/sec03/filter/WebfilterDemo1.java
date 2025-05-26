//package com.playground.webflux.sec03.filter;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//
//@Service
//@Order(1)
//public class WebfilterDemo1 implements WebFilter {
//
//    private static final Logger log = LoggerFactory.getLogger(WebfilterDemo1.class);
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
////        log.info("received");
//        return chain.filter(exchange);
//    }
//}
