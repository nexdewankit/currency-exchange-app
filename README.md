# Currency Exchange Application

## Overview

This Spring Boot application allows users to manage currencies and fetch their exchange rates from the public API [openexchangerates.org](https://openexchangerates.org).

Users can:
- Add currencies manually.
- Retrieve the list of added currencies.
- Get the latest exchange rates for a given currency.

Exchange rates are fetched hourly, stored in a PostgreSQL database, and cached in-memory for fast access.

---

## Features

- Developed using Java 17 and Spring Boot 3.2.
- RESTful API endpoints for managing currencies and exchange rates.
- Integration with [openexchangerates.org](https://openexchangerates.org) for live exchange rate data.
- PostgreSQL database with schema migrations managed by Flyway.
- Scheduled hourly updates of exchange rates.
- OpenAPI/Swagger documentation for API endpoints.
- Unit and integration tests using JUnit 5 and Spring Test Framework.
- PostgreSQL runs inside Docker container via `docker-compose`.

---

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose (for running PostgreSQL)

---

## Getting Started

### 1. Clone the repository

```bash
git clone https://your.repo.url/currency-exchange-app.git
cd currency-exchange-app

## Step 1
create database currencydb
Run the application
open swaager ui - http://localhost:8080/swagger-ui/index.html