package com.ml.proxy.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

@ToString
@Getter
public class ProxyException extends ResponseStatusException {

    public ProxyException(HttpStatusCode statusCode, String errorMessage) {
        super(statusCode, errorMessage);
    }
}