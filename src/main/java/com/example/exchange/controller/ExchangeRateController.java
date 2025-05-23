package com.example.exchange.controller;


import com.example.exchange.service.IExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
@Tag(name = "Exchange API", description = "Manage exchange rates")
public class ExchangeRateController {
    private final IExchangeRateService exchangeRateService;

    @Operation(summary = "Get exchange rates", description = "Fetches the exchange rates for a given currency code")
    @GetMapping("/{code}/rates")
    public Map<String, BigDecimal> getRates(@PathVariable String code) {
        return exchangeRateService.getRatesForCurrency(code);
    }

    @Operation(summary = "update exchange rates", description = "Fetches the exchange rates from exhange")
    @GetMapping("/updateRate")
    public void updateExchangeRate() {
        exchangeRateService.updateRates();
    }
}
