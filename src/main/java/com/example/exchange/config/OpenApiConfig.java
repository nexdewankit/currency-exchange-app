package com.example.exchange.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Currency Exchange API")
                        .description("API to manage currencies and fetch exchange rates")
                        .version("1.0"));
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}