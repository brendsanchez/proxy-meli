package com.ml.proxy.filter;

import com.ml.proxy.service.RateLimiterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * Filtro de Gateway para aplicar limitación de tasa en base a la IP, el path de la API
 * y la combinación de ambos (IP + path).
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RateLimitFilter implements GatewayFilter, Ordered {

    private final RateLimiterService rateLimiterService;

    @Value("${mercado-libre.rate-limit.ip-max-requests}")
    private Integer ipMaxRequests;

    @Value("${mercado-libre.rate-limit.path-max-requests}")
    private Integer pathMaxRequests;

    @Value("${mercado-libre.rate-limit.comb-max-requests}")
    private Integer combMaxRequests;

    /**
     * Filtra las solicitudes para comprobar si se han alcanzado los límites de peticiones
     * y devuelve un error 429 (Too Many Requests) si es necesario.
     *
     * @param exchange El intercambio de la solicitud y la respuesta.
     * @param chain    La cadena de filtros del Gateway.
     * @return Mono vacío si la solicitud no supera los límites; de lo contrario, devuelve un error.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.defer(() -> {
            HttpStatusCode statusCode = exchange.getResponse().getStatusCode();
            String ip = this.getIp(exchange.getRequest().getRemoteAddress());
            String path = exchange.getRequest().getURI().getPath();

            if (statusCode != null && statusCode.isError()) {
                log.error("RateLimitFilter => {}, ip:{}, path:{}", statusCode, ip, path);
                return Mono.empty();
            }

            Map<String, Integer> keys = Map.of(
                    this.rateLimiterService.buildKey("ip", ip), ipMaxRequests,
                    this.rateLimiterService.buildKey("path", path), pathMaxRequests,
                    this.rateLimiterService.buildKey("comb", ip + ":" + path), combMaxRequests
            );

            for (Map.Entry<String, Integer> entry : keys.entrySet()) {
                Boolean allowed = this.rateLimiterService.executeRateLimitScript(entry.getKey(), entry.getValue());
                if (Boolean.FALSE.equals(allowed)) {
                    exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
                    return exchange.getResponse().setComplete();
                }
            }

            return Mono.empty();
        }));
    }

    /**
     * Define el orden de ejecución del filtro.
     * El valor 1 asegura que se ejecute después del filtro de autenticación (con orden 0).
     *
     * @return El orden de ejecución del filtro.
     */
    @Override
    public int getOrder() {
        return 1;
    }

    private String getIp(InetSocketAddress inetSocketAddress) {
        if (isNull(inetSocketAddress)) {
            return "0.0.0.0.0";
        }
        return inetSocketAddress.getAddress().getHostAddress();
    }
}
