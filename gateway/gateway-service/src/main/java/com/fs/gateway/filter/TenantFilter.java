package com.fs.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class TenantFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String host = exchange.getRequest().getHeaders().getFirst("Host");
            String subdomain = host != null ? host.split("\\.")[0] : "default";

            log.info("Host: {}, Subdomain: {}", host, subdomain);

            ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
                    .header("X-Tenant-Id", extractTenantId(subdomain).toString())
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        };
    }

    private Long extractTenantId(String subdomain) {
        return 1L;
    }
}
