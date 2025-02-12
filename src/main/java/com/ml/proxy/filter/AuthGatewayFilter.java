package com.ml.proxy.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthGatewayFilter implements GatewayFilter, Ordered {

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${mercado-libre.access-token}")
    private String accessToken;

    /**
     * Filtra las solicitudes para agregar un encabezado de autorización con el token de acceso
     * de Mercado Libre si no está presente o está mal formado en la solicitud.
     *
     * @param exchange El intercambio de la solicitud y respuesta.
     * @param chain La cadena de filtros del Gateway.
     * @return Mono vacío cuando la solicitud ha sido procesada y la cadena de filtros continúa.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            String newAuthHeader = BEARER_PREFIX.concat(accessToken);

            request = request.mutate()
                    .header(HttpHeaders.AUTHORIZATION, newAuthHeader)
                    .build();

            return chain.filter(exchange.mutate().request(request).build());
        }

        return chain.filter(exchange);
    }

    /**
     * Define el orden de ejecución del filtro.
     * El valor 0 significa que se ejecutará antes que otros filtros con orden mayor.
     *
     * @return El orden de ejecución del filtro.
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
