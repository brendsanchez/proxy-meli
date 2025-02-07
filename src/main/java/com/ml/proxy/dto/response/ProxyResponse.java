package com.ml.proxy.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProxyResponse<T>(
        @Schema(description = "Descripción del código de respuesta")
        String message,

        @Schema(description = "Código de respuesta")
        Integer code,

        T data
) {
}