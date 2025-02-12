package com.ml.proxy.configuration;

import com.ml.proxy.filter.AuthGatewayFilter;
import com.ml.proxy.filter.RateLimitFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final AuthGatewayFilter authGatewayFilter;
    private final RateLimitFilter rateLimitFilter;

    @Value("${mercado-libre.api-url}")
    private String mercadoLibreApiUrl;

    /**
     * Configura las rutas del Gateway, incluyendo el filtro de autenticación
     * y el filtro de limitación de tasa, y enruta las peticiones a la API de Mercado Libre.
     *
     * @param builder El constructor de rutas de Spring Cloud Gateway.
     * @return La configuración de las rutas personalizadas.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("mercado_libre_proxy", r -> r
                        .path("/**").and()
                        .not(r1 -> r1.path("/swagger-ui/**", "/v3/api-docs/**", "/webjars/**"))
                        .filters(f -> f.filter(this.authGatewayFilter).filter(this.rateLimitFilter))
                        .uri(this.mercadoLibreApiUrl))
                .build();
    }
}
