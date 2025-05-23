package com.example.exchange.controller;

import com.example.exchange.dto.CurrencyRequestDTO;
import com.example.exchange.model.Currency;
import com.example.exchange.service.ICurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ICurrencyService currencyService;

    @Autowired
    private ObjectMapper objectMapper;

    private Currency usdCurrency;

    @BeforeEach
    void setup() {
        usdCurrency = new Currency();
        usdCurrency.setCode("USD");
    }

    @Test
    void addCurrency_ShouldReturnCurrencyResponse() throws Exception {
        when(currencyService.addCurrency(anyString())).thenReturn(usdCurrency);

        CurrencyRequestDTO requestDTO = new CurrencyRequestDTO();
        requestDTO.setCode("USD");

        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("USD"));
    }

    @Test
    void getAllCurrencies_ShouldReturnListOfCurrencies() throws Exception {
        Currency eurCurrency = new Currency();
        eurCurrency.setCode("EUR");

        when(currencyService.getAllCurrencies()).thenReturn(List.of(usdCurrency, eurCurrency));

        mockMvc.perform(get("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("USD"))
                .andExpect(jsonPath("$[1].code").value("EUR"));
    }
}
