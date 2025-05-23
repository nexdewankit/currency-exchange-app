package com.example.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyResponseDTO {

    @Schema(description = "Currency code", example = "USD")
    private String code;

}
