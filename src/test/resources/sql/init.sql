CREATE TABLE IF NOT EXISTS users
(
    id  SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    last_name  VARCHAR(128) NOT NULL,
    email      VARCHAR(128) NOT NULL,
    password   VARCHAR(128) NOT NULL,
    telephone  VARCHAR(128) NOT NULL,
    birth_date DATE         NOT NULL,
    role       VARCHAR(128) NOT NULL
    );

CREATE TABLE IF NOT EXISTS catalog
(
    id  SERIAL PRIMARY KEY,
    category VARCHAR(128) NOT NULL UNIQUE,
    );

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

CREATE TABLE IF NOT EXISTS orders
(
    id  SERIAL PRIMARY KEY,
    delivery_city VARCHAR(128) NOT NULL,
    delivery_street VARCHAR(128) NOT NULL,
    delivery_building VARCHAR(128) NOT NULL,
    delivery_date VARCHAR(128) NOT NULL,
    payment_condition VARCHAR(128) NOT NULL,
    user_id INT NOT NULL REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS shopping_cart
(
    id  SERIAL PRIMARY KEY,
    created_at DATE NOT NULL,
    order_id   INT NOT NULL REFERENCES orders (id),
    product_id INT NOT NULL REFERENCES product (id)
    UNIQUE (order_id, product_id)
    );