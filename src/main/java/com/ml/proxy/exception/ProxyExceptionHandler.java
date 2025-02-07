package com.ml.proxy.exception;

import com.ml.proxy.dto.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.ResourceAccessException;

@Slf4j
@ControllerAdvice
public class ProxyExceptionHandler {

    @ExceptionHandler(RestClientResponseException.class)
    public ResponseEntity<ErrorResponse> restClientError(RestClientResponseException ex) {
        var errorResponse = this.errorRestClientResponse(ex);
        log.error("ExceptionHandler restClientError: {} ", errorResponse);
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }

    private ErrorResponse errorRestClientResponse(RestClientResponseException restClientResponseException) {
        try {
            var responseBody = restClientResponseException.getResponseBodyAsString();
            var mapper = new ObjectMapper();
            return mapper.readValue(responseBody, ErrorResponse.class);
        } catch (JsonProcessingException ex) {
            log.error("ExceptionHandler errorRestClientResponse: {}", ex.getMessage());
            return ErrorResponse.builder()
                    .message(restClientResponseException.getStatusText())
                    .code(restClientResponseException.getStatusCode().value())
                    .build();
        }
    }
    
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<ErrorResponse> resourceAccessError(ResourceAccessException ex) {
        var errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        log.error("ExceptionHandler resourceAccessError: {} ", ex.getMessage());
        return ResponseEntity.status(errorResponse.getCode()).body(errorResponse);
    }
}