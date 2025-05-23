package com.example.exchange.service;

import com.example.exchange.model.Currency;
import com.example.exchange.repository.CurrencyRepository;
import com.example.exchange.service.impl.CurrencyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CurrencyServiceTest {

    private CurrencyRepository currencyRepository;
    private CurrencyService currencyService;

    @BeforeEach
    void setUp() {
        currencyRepository = Mockito.mock(CurrencyRepository.class);
        currencyService = new CurrencyService(currencyRepository);
    }

    @Test
    void testAddCurrency() {
        Currency currency = new Currency();
        currency.setCode("USD");
        when(currencyRepository.save(currency)).thenReturn(currency);

        Currency saved = currencyService.addCurrency("USD");
        assertEquals("USD", saved.getCode());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void testGetAllCurrencies() {
        Currency currency=new Currency();
        Currency currency1= new Currency();
        currency.setCode("USD");
        currency.setCode("EUR");
        when(currencyRepository.findAll()).thenReturn(List.of(currency,currency1));

        List<Currency> currencies = currencyService.getAllCurrencies();

        assertEquals(2, currencies.size());
    }
}
