package com.example.exchange.repository;

import com.example.exchange.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<Currency, UUID> {
    Optional<Currency> findByCode(String code);
}
