package com.example.exchange.controller;

import com.example.exchange.model.Currency;
import com.example.exchange.service.ICurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
@Tag(name = "Currency API", description = "Manage currencies and their exchange rates")
public class CurrencyController {

    private final ICurrencyService currencyService;

    @Operation(summary = "Add a currency", description = "Adds a new currency by its code (e.g., USD, EUR)")
    @PostMapping
    public Currency addCurrency(@RequestParam String code) {
        return currencyService.addCurrency(code);
    }

    @Operation(summary = "Get all currencies", description = "Retrieves a list of all added currencies")
    @GetMapping
    public List<Currency> getAllCurrencies() {
        return currencyService.getAllCurrencies();
    }

}
