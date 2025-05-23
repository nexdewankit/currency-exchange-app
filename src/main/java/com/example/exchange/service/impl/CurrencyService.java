package com.example.exchange.service.impl;

import com.example.exchange.model.Currency;
import com.example.exchange.repository.CurrencyRepository;
import com.example.exchange.service.ICurrencyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyService implements ICurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyService(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    public Currency addCurrency(String code) {
        Currency currency = new Currency();
        currency.setCode(code.toUpperCase());
        return currencyRepository.save(currency);
    }

    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }
}
