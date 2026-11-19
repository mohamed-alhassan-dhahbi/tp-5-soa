package com.iset.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    // Part 1: dynamic routes from Eureka (http://localhost:8080/CUSTOMER-SERVICE/customers/1)
    @Bean
    DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient rdc,
                                                        DiscoveryLocatorProperties dlp) {
        return new DiscoveryClientRouteDefinitionLocator(rdc, dlp);
    }

    // Part 2: static route to the external Muslim Salat API + filters + circuit breaker
    // Test: http://localhost:8080/muslim/tunis.json
    @Bean
    RouteLocator staticRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(r -> r.path("/muslim/**")
                .filters(f -> f
                    .addRequestHeader("x-rapidapi-host", "muslimsalat.p.rapidapi.com")
                    .addRequestHeader("x-rapidapi-key", "bc63a09f9fmsh658cbb8ae118ad6p14305cjsnb159a2cc3de4")
                    .rewritePath("/muslim/(?<segment>.*)", "/${segment}")
                    .circuitBreaker(c -> c.setName("muslimsalat")
                        .setFallbackUri("forward:/defaultMuslim")))
                .uri("https://muslimsalat.p.rapidapi.com"))
            .build();
    }
}