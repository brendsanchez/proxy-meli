package com.ml.proxy.controller;

import com.ml.proxy.dto.RateLimitInfo;
import com.ml.proxy.service.RateLimiterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Controlador que maneja las solicitudes relacionadas con la información de limitación de tasa (rate limit)
 * para las peticiones realizadas a través del proxy.
 */

@Slf4j
@RestController
@Tag(name = "Proxy")
@RequestMapping("proxy")
@RequiredArgsConstructor
public class ProxyController {

    private final RateLimiterService rateLimiterService;

    @Operation(summary = "Retorna los stats por ip, path o ambas.",
            responses = {
                    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = RateLimitInfo.class))),
                    @ApiResponse(responseCode = "400", content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
                    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))},
            parameters = {
                    @Parameter(name = "ip", in = ParameterIn.QUERY, description = "IP address for filtering", example = "0:0:0:0:0:0:0:1"),
                    @Parameter(name = "path", in = ParameterIn.QUERY, description = "Path for filtering", example = "/products/search")
            })
    @GetMapping(value = "/stats", produces = APPLICATION_JSON_VALUE)
    public RateLimitInfo getStats(@RequestParam(required = false, defaultValue = "") String ip,
                                  @RequestParam(required = false, defaultValue = "") String path) {
        return this.rateLimiterService.getStats(ip, path);
    }

}