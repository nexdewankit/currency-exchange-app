package com.example.exchange.repository;

import com.example.exchange.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, UUID> {
    List<ExchangeRate> findByBaseCurrency(String code);
}
