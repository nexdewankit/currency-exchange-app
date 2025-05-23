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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeRateServiceTest {

    @Mock
    private ExchangeRateRepository rateRepository;

    @Mock
    private CurrencyService currencyService;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set openexchangerates.app-id manually since @Value won't inject in test
        ReflectionTestUtils.setField(exchangeRateService, "appId", "dummy-app-id");
    }

    @Test
    void testUpdateRates() throws Exception {
        Currency currency = new Currency();
        currency.setCode("USD");

        List<Currency> currencies = List.of(currency);

        Map<String, BigDecimal> mockRates = new HashMap<>();
        mockRates.put("EUR", new BigDecimal("0.85"));
        mockRates.put("INR", new BigDecimal("74.5"));

        String jsonResponse = """
        {
          "rates": {
            "EUR": 0.85,
            "INR": 74.5
          }
        }
        """;

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonResponse);

        when(currencyService.getAllCurrencies()).thenReturn(currencies);
        when(restTemplate.getForObject(anyString(), eq(JsonNode.class))).thenReturn(jsonNode);
        when(rateRepository.save(any(ExchangeRate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        exchangeRateService.updateRates();

        Map<String, BigDecimal> rates = exchangeRateService.getRatesForCurrency("USD");

        assertEquals(new BigDecimal("0.85"), rates.get("EUR"));
        assertEquals(new BigDecimal("74.5"), rates.get("INR"));

        verify(rateRepository, times(2)).save(any(ExchangeRate.class));
    }

    @Test
    void testGetRatesForCurrencyWhenEmpty() {
        Map<String, BigDecimal> result = exchangeRateService.getRatesForCurrency("XXX");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
