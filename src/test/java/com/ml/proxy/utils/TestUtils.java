package com.ml.proxy.utils;

import com.ml.proxy.dto.request.ProxyRequest;
import com.ml.proxy.dto.response.ProxyResponse;
import com.ml.proxy.dto.response.ResultDto;
import com.ml.proxy.model.Example;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class TestUtils {

    public static final String CELLULAR_NUMBER = "123456789";

    public static ProxyRequest exampleRequest() {
        return ProxyRequest.builder()
                .cellularNumber(CELLULAR_NUMBER)
                .build();
    }

    public static ProxyResponse<ResultDto> exampleResponse() {
        ResultDto resultDto = new ResultDto(TestUtils.CELLULAR_NUMBER);
        return new ProxyResponse<>(HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value(), resultDto);
    }

    public static Optional<Example> example() {
        Example example = Example.builder()
                .id(1)
                .name("test")
                .phoneNumber(TestUtils.CELLULAR_NUMBER)
                .build();

        return Optional.of(example);
    }
}

