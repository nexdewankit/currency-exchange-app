package com.example.exchange.service;

import com.example.exchange.model.Currency;

import java.util.List;

public interface ICurrencyService {
    Currency addCurrency(String code);

    List<Currency> getAllCurrencies();
}
