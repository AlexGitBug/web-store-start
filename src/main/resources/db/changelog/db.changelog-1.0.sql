--liquibase formatted sql

--changeset anisimov:1
CREATE TABLE IF NOT EXISTS users
(
    id  SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    email      VARCHAR(128) NOT NULL UNIQUE ,
    password   VARCHAR(128),
    telephone  VARCHAR(128) NOT NULL UNIQUE ,
    birth_date DATE         NOT NULL,
    role       VARCHAR(128) NOT NULL
    );

--changeset anisimov:2
CREATE TABLE IF NOT EXISTS catalog
(
    id  SERIAL PRIMARY KEY,
    category VARCHAR(128) NOT NULL UNIQUE
    );

--changeset anisimov:3
CREATE TABLE IF NOT EXISTS product
(
    id  SERIAL PRIMARY KEY,
    brand           VARCHAR(128) NOT NULL,
    model           VARCHAR(128) NOT NULL,
    date_of_release DATE NOT NULL,
    price           INT NOT NULL,
    color           VARCHAR(128) NOT NULL,
    image           VARCHAR(128) NOT NULL,
    catalog_id      INT NOT NULL REFERENCES catalog (id)
    );

--changeset anisimov:4
CREATE TABLE IF NOT EXISTS orders
(
    id  SERIAL PRIMARY KEY ,
    delivery_city VARCHAR(128) NOT NULL,
    delivery_street VARCHAR(128) NOT NULL,
    delivery_building INT NOT NULL,
    delivery_date DATE NOT NULL,
    payment_condition VARCHAR(128) NOT NULL,
    status VARCHAR(64) NOT NULL ,
    user_id INT NOT NULL REFERENCES users (id)
    );

--changeset anisimov:5
CREATE TABLE IF NOT EXISTS shopping_cart
(
    id  SERIAL PRIMARY KEY ,
    created_at DATE NOT NULL ,
    order_id   INT NOT NULL REFERENCES orders (id) ON DELETE CASCADE ,
    product_id INT NOT NULL REFERENCES product (id) ON DELETE CASCADE,
    count INT NOT NULL
    );