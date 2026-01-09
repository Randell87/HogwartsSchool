-- Таблица машин
CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    price DECIMAL(12, 2) NOT NULL
);

-- Таблица людей
CREATE TABLE person (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL CHECK (age >= 0),
    has_license BOOLEAN NOT NULL DEFAULT false,
    car_id BIGINT REFERENCES car(id)
);