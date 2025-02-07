package com.ml.proxy.service;

import com.ml.proxy.dto.request.ProxyRequest;
import com.ml.proxy.dto.response.ProxyResponse;
import com.ml.proxy.dto.response.ResultDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProxyService {


    public ProxyResponse<ResultDto> example(final ProxyRequest request) {
        log.info(":::::: starting example: {} :::::", request);

        return new ProxyResponse<>("ok", 200, null);
    }
}