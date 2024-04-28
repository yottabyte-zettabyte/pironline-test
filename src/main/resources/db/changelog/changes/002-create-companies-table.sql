
CREATE TABLE companies (
    id UUID PRIMARY KEY,
    version bigint NOT NULL DEFAULT 0,
    short_name VARCHAR(100) NOT NULL,
    long_name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    inn VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now(),
    CONSTRAINT unq_companies_inn UNIQUE (inn)
);