package com.example.exchange.controller;

import com.example.exchange.dto.CurrencyRequestDTO;
import com.example.exchange.dto.CurrencyResponseDTO;
import com.example.exchange.model.Currency;
import com.example.exchange.service.ICurrencyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
@Tag(name = "Currency API", description = "Manage currencies and their exchange rates")
public class CurrencyController {

    private final ICurrencyService currencyService;

    @Operation(summary = "Add a currency", description = "Adds a new currency by its code (e.g., USD, EUR)")
    @PostMapping
    public CurrencyResponseDTO addCurrency(@Valid @RequestBody CurrencyRequestDTO request) {
        Currency currency = currencyService.addCurrency(request.getCode());
        return new CurrencyResponseDTO(currency.getCode());
    }

    @Operation(summary = "Get all currencies", description = "Retrieves a list of all added currencies")
    @GetMapping
    public List<CurrencyResponseDTO> getAllCurrencies() {
        return currencyService.getAllCurrencies().stream()
                .map(currency -> new CurrencyResponseDTO(currency.getCode()))
                .collect(Collectors.toList());
    }
}
