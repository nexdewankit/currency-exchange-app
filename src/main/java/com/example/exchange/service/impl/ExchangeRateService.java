package com.example.exchange.service.impl;

import com.example.exchange.model.ExchangeRate;
import com.example.exchange.repository.ExchangeRateRepository;
import com.example.exchange.service.IExchangeRateService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExchangeRateService implements IExchangeRateService {

    private final ExchangeRateRepository rateRepository;
    private final CurrencyService currencyService;
    private final WebClient webClient;

    @Value("${openexchangerates.app-id}")
    private String appId;

    public void updateRates(int value) {
        var currencies = currencyService.getAllCurrencies();
        for (var currency : currencies) {
            try {
                String url = "https://openexchangerates.org/api/latest.json?app_id=" + appId + "&base=" + currency.getCode();

                JsonNode json = webClient.get()
                        .uri(url)
                        .retrieve()
                        .onStatus(
                                httpStatus -> httpStatus.is4xxClientError() || httpStatus.is5xxServerError(),
                                clientResponse -> {
                                    log.error("HTTP error {} while fetching rates for base currency {}", clientResponse.statusCode(), currency.getCode());
                                    return clientResponse.createException();
                                }
                        )
                        .bodyToMono(JsonNode.class)
                        .onErrorResume(e -> {
                            log.error("Error while calling API for currency {}: {}", currency.getCode(), e.getMessage(), e);
                            return Mono.empty(); // Gracefully handle error by returning empty
                        })
                        .block();

                if (json != null && json.has("rates")) {
                    JsonNode rates = json.get("rates");
                    for (Iterator<String> it = rates.fieldNames(); it.hasNext(); ) {
                        String target = it.next();
                        BigDecimal rate = rates.get(target).decimalValue();
                        rateRepository.save(new ExchangeRate(currency.getCode(), target, rate, LocalDateTime.now()));
                    }
                }
            } catch (Exception e) {
                if (value < 3) {
                    updateRates(value + 1);
                }
                log.error("Error updating rates for base currency {}: {}", currency.getCode(), e.getMessage(), e);
            }
        }
    }

    public Map<String, BigDecimal> getRatesForCurrency(String code) {
        Map<String, BigDecimal> ratesMap = new HashMap<>();
        List<ExchangeRate> exchangeRates = rateRepository.findByBaseCurrency(code);
        if (exchangeRates != null) {
            for (ExchangeRate exchangeRate : exchangeRates) {
                ratesMap.put(exchangeRate.getTargetCurrency(), exchangeRate.getRate());
            }
        }
        return ratesMap;
    }
}
