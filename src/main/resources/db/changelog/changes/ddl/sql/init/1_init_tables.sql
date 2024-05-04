CREATE TABLE IF NOT EXISTS product
(
    id             BIGSERIAL PRIMARY KEY,
    amount         INTEGER
);

CREATE TABLE IF NOT EXISTS reservation
(
    id              BIGSERIAL PRIMARY KEY,
    client_id       UUID NOT NULL UNIQUE,
    expiration_date DATE NOT NULL,
    amount          INTEGER,
    product_id      BIGINT REFERENCES product (id)
);

