package com.example.exchange.service.impl;

import com.example.exchange.model.ExchangeRate;
import com.example.exchange.repository.ExchangeRateRepository;
import com.example.exchange.service.IExchangeRateService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService implements IExchangeRateService {
    private final ExchangeRateRepository rateRepository;
    private final CurrencyService currencyService;
    private final RestTemplate restTemplate;
    private final Map<String, Map<String, BigDecimal>> currencyRatesMap = new ConcurrentHashMap<>();
    @Value("${openexchangerates.app-id}")
    private String appId;

    public void updateRates() {
        var currencies = currencyService.getAllCurrencies();
        for (var currency : currencies) {
            try {
                String url = "https://openexchangerates.org/api/latest.json?app_id=" + appId + "&base=" + currency.getCode();
                JsonNode json = restTemplate.getForObject(url, JsonNode.class);
                if (json != null && json.has("rates")) {
                    JsonNode rates = json.get("rates");
                    Map<String, BigDecimal> ratesMap = new HashMap<>();
                    for (Iterator<String> it = rates.fieldNames(); it.hasNext(); ) {
                        String target = it.next();
                        BigDecimal rate = rates.get(target).decimalValue();
                        rateRepository.save(new ExchangeRate(currency.getCode(), target, rate, LocalDateTime.now()));
                        ratesMap.put(target, rate);
                    }
                    currencyRatesMap.put(currency.getCode(), ratesMap);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    public Map<String, BigDecimal> getRatesForCurrency(String code) {
        return currencyRatesMap.getOrDefault(code.toUpperCase(), Collections.emptyMap());
    }
}
