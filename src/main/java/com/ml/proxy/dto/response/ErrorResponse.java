package com.ml.proxy.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Status de error del servicio")
    private String status;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Código de error del servicio")
    private Integer code;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Subtipo de error del servicio")
    private String subType;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Tipo de error del servicio")
    private String type;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Descripción de error del servicio")
    private String message;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "Detalle de error del servicio")
    private String detail;
}
