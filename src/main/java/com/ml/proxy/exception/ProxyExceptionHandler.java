package com.ml.proxy.exception;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@RestControllerAdvice
public class ProxyExceptionHandler {

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ProblemDetail> handleException(@NotNull Throwable ex, ServerWebExchange exchange) {
        HttpStatusCode status = HttpStatus.INTERNAL_SERVER_ERROR;
        String errorMessage = "Error interno en el proxy";

        if (ex instanceof ResponseStatusException ep) {
            status = ep.getStatusCode();
            errorMessage = ep.getReason();
        } else {
            log.error("ProxyExceptionHandler => internal:{}", ex.getMessage());
        }

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, errorMessage);
        problemDetail.setTitle("Error en el Proxy");

        var method = exchange.getRequest().getMethod().toString();
        problemDetail.setProperty("method", method);

        var uri = exchange.getRequest().getURI();
        problemDetail.setInstance(uri);

        log.error("ProxyExceptionHandler => problemDetail: {}", problemDetail);
        return new ResponseEntity<>(problemDetail, status);
    }
}
