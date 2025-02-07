package com.ml.proxy.service;
/*
import com.ml.proxy.dto.response.ProxyResponse;
import com.ml.proxy.dto.response.ResultDto;
import com.ml.proxy.repository.ExampleRepository;
import com.ml.proxy.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProxyServiceTest {

    @Mock
    private MercadoLibreClient mercadoLibreClient;

    @Mock
    private ExampleRepository exampleRepository;

    @InjectMocks
    private ProxyService service;

    @Test
    @DisplayName("example ok from db")
    void whenExampleOkDb() {
        when(this.exampleRepository.findOneByPhoneNumber(any())).thenReturn(TestUtils.example());

        ProxyResponse<ResultDto> response = this.service.example(TestUtils.exampleRequest());
        assertEquals(HttpStatus.OK.getReasonPhrase() , response.message());
        assertEquals(HttpStatus.OK.value() , response.code());
        assertEquals(TestUtils.CELLULAR_NUMBER , response.data().getResult());

        verify(this.exampleRepository, times(1)).findOneByPhoneNumber(TestUtils.CELLULAR_NUMBER);
        verify(this.mercadoLibreClient, times(0)).getResponseFromIntegration(TestUtils.CELLULAR_NUMBER);
    }

    @Test
    @DisplayName("example ok from client")
    void whenExampleOkClient() {
        when(this.exampleRepository.findOneByPhoneNumber(any())).thenReturn(Optional.empty());
        when(this.mercadoLibreClient.getResponseFromIntegration(anyString())).thenReturn(TestUtils.exampleResponse());

        ProxyResponse<ResultDto> response = this.service.example(TestUtils.exampleRequest());
        assertEquals(HttpStatus.OK.getReasonPhrase() , response.message());
        assertEquals(HttpStatus.OK.value() , response.code());
        assertEquals(TestUtils.CELLULAR_NUMBER , response.data().getResult());

        verify(this.exampleRepository, times(1)).findOneByPhoneNumber(TestUtils.CELLULAR_NUMBER);
        verify(this.mercadoLibreClient, times(1)).getResponseFromIntegration(TestUtils.CELLULAR_NUMBER);
    }
}*/