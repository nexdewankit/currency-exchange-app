
CREATE TABLE currency (
    id UUID PRIMARY KEY,
    code VARCHAR(10) UNIQUE NOT NULL
);

CREATE TABLE exchange_rate (
    id UUID PRIMARY KEY,
    base_currency VARCHAR(10),
    target_currency VARCHAR(10),
    rate NUMERIC,
    fetched_at TIMESTAMP
);
