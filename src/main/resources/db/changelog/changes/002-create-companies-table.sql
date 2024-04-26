
CREATE TABLE companies (
    id UUID PRIMARY KEY,
    version bigint NOT NULL DEFAULT 0,
    short_name VARCHAR(100) NOT NULL,
    long_name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);