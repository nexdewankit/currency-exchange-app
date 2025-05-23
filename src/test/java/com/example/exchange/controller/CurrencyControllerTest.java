package com.example.exchange.controller;

import com.example.exchange.model.Currency;
import com.example.exchange.service.ICurrencyService;
import com.example.exchange.service.IExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICurrencyService currencyService;

    @MockBean
    private IExchangeRateService exchangeRateService;

    private Currency currency;

    @BeforeEach
    void setUp() {
        currency = new Currency();
        currency.setId(UUID.randomUUID());
        currency.setCode("USD");
    }

    @Test
    void testAddCurrency() throws Exception {
        Mockito.when(currencyService.addCurrency("USD")).thenReturn(currency);

        mockMvc.perform(post("/api/currencies")
                        .param("code", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"));
    }

    @Test
    void testGetAllCurrencies() throws Exception {
        Mockito.when(currencyService.getAllCurrencies()).thenReturn(List.of(currency));

        mockMvc.perform(get("/api/currencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].code").value("USD"));
    }

}
