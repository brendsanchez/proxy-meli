package com.ml.proxy.service;

import com.ml.proxy.dto.RateLimitInfo;
import com.ml.proxy.exception.ProxyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RateLimiterService {

    private static final String RATE_LIMIT_PREFIX = "rl";

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${spring.redis.lua-script}")
    private String luaScript;

    @Value("${mercado-libre.rate-limit.max-duration-seconds}")
    private Integer maxDurationInSeconds;

    /**
     * Obtener el número de peticiones realizadas por una IP para un path determinado.
     *
     * @param ip   La dirección IP del cliente que realizó las peticiones.
     * @param path El path de la API que se está consultando.
     * @return Un objeto {@link RateLimitInfo} que contiene la clave utilizada y el número de peticiones realizadas.
     * @throws ProxyException Si ambos parámetros 'ip' y 'path' están vacíos.
     */
    public RateLimitInfo getStats(String ip, String path) {
        if (ip.isEmpty() && path.isEmpty()) {
            throw new ProxyException(HttpStatus.BAD_REQUEST, "param 'ip' or 'path' or both is required");
        }

        log.info("getRequestCount => by ip:{}, path:{}", ip, path);

        String key;
        if (!ip.isEmpty() && !path.isEmpty()) {
            key = this.buildKey("comb", ip + ":" + path);
        } else if (!ip.isEmpty()) {
            key = this.buildKey("ip", ip);
        } else {
            key = this.buildKey("path", path);
        }

        var value = this.stringRedisTemplate.opsForValue().get(key);
        var currentRequest = value != null ? Long.parseLong(value) : 0L;
        return RateLimitInfo.builder().key(key).currentRequests(currentRequest).build();
    }

    public String buildKey(String type, String value) {
        return String.format("%s:%s:%s", RATE_LIMIT_PREFIX, type, value);
    }

    public Boolean executeRateLimitScript(String key, Integer maxRequests) {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>(this.luaScript, Long.class);
        Long result = this.stringRedisTemplate.execute(script, List.of(key), maxDurationInSeconds.toString(), maxRequests.toString());
        return result != null && result == 1;
    }

}