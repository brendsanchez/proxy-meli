package com.ml.proxy.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProxyRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, example = "123456789", description = "numero de celular")
    private String cellularNumber;
}