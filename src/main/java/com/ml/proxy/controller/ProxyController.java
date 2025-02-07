package com.ml.proxy.controller;

import com.ml.proxy.dto.request.ProxyRequest;
import com.ml.proxy.dto.response.ProxyResponse;
import com.ml.proxy.dto.response.ErrorResponse;
import com.ml.proxy.dto.response.ResultDto;
import com.ml.proxy.service.ProxyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Proxy", description = "Controller para ...")
@RequiredArgsConstructor
public class ProxyController {

    private final ProxyService service;

    @Operation(summary = "example.",
            responses = {
                    @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            })
    @PostMapping(value = "/example", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProxyResponse<ResultDto> example(@RequestBody ProxyRequest request) {
        return this.service.example(request);
    }

}