package com.ml.proxy.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RateLimitInfo {
    private String key;
    private Long currentRequests;
}
