package com.example.exchange.service;

import com.example.exchange.model.Currency;
import com.example.exchange.model.ExchangeRate;
import com.example.exchange.repository.ExchangeRateRepository;
import com.example.exchange.service.impl.CurrencyService;
import com.example.exchange.service.impl.ExchangeRateService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.*;

import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateRepository rateRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec<?> uriSpec;

    @Mock
    private WebClient.RequestHeadersSpec<?> headersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Inject the appId property
        ReflectionTestUtils.setField(exchangeRateService, "appId", "testAppId");
    }

    @Test
    void getRatesForCurrency_shouldReturnRatesMap() {
        ExchangeRate rate1 = new ExchangeRate("USD", "EUR", BigDecimal.valueOf(0.85), LocalDateTime.now());
        ExchangeRate rate2 = new ExchangeRate("USD", "GBP", BigDecimal.valueOf(0.75), LocalDateTime.now());
        when(rateRepository.findByBaseCurrency("USD")).thenReturn(List.of(rate1, rate2));

        Map<String, BigDecimal> rates = exchangeRateService.getRatesForCurrency("USD");

        assertEquals(2, rates.size());
        assertEquals(BigDecimal.valueOf(0.85), rates.get("EUR"));
        assertEquals(BigDecimal.valueOf(0.75), rates.get("GBP"));
    }

    @Test
    void getRatesForCurrency_shouldReturnEmptyMap_whenNoRates() {
        when(rateRepository.findByBaseCurrency("USD")).thenReturn(Collections.emptyList());

        Map<String, BigDecimal> rates = exchangeRateService.getRatesForCurrency("USD");

        assertTrue(rates.isEmpty());
    }
}
