package com.fs.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("tenant-service", r -> r.path("/tenants/**")
                        .uri("lb://tenant-service"))
                .route("user-service", r -> r.path("/auth/**", "/users/**")
                        .uri("lb://user-service"))
                .route("org-service", r -> r.path("/orgs/**")
                        .uri("lb://org-service"))
                .route("trans-service", r -> r.path("/trans/**")
                        .uri("lb://trans-service"))
                .build();
    }
}
