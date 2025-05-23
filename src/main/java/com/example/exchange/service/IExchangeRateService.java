package com.example.exchange.service;

import java.math.BigDecimal;
import java.util.Map;

public interface IExchangeRateService {
    void updateRates();

    Map<String, BigDecimal> getRatesForCurrency(String code);
}
