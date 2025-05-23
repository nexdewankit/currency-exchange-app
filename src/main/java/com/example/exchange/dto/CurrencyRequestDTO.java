package com.example.exchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyRequestDTO {

    @Schema(description = "Currency code (e.g., USD, EUR)", example = "USD", required = true)
    @NotBlank
    private String code;
}
