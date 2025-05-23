package com.example.exchange.scheduler;

import com.example.exchange.service.impl.ExchangeRateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RateScheduler {
    private final ExchangeRateService exchangeRateService;

    public RateScheduler(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Scheduled(fixedRate = 3600000)
    public void fetchRates() {
        exchangeRateService.updateRates(0);
    }
}
