INSERT INTO users (id, first_name, last_name, email, password, telephone, birth_date, role)
VALUES (1, 'Ivan', 'Ivanov', 'ivan@gmail.com', 'ivan', '123-45-67', '1990-10-10', 'USER'),
       (2, 'Sveta', 'Svetikova', 'sveta@gmail.com', 'sveta', '123-67-47', '1985-02-02', 'USER'),
       (3, 'Petr', 'Petrov', 'petr@gmail.com', 'petr', '321-45-67', '1980-03-03', 'USER'),
       (4, 'Sasha', 'Sasheva', 'sasha@gmail.com', 'sasha', '123-45-78', '1991-10-05', 'USER'),
       (5, 'Dima', 'Dimov', 'dima@gmail.com', 'dima', '321-32-32', '1988-03-18', 'USER'),
       (6, 'Ksenia', 'Kseneva', 'ksenia@gmail.com', 'ksenia', '123-32-32', '1992-07-18', 'USER'),
       (7, 'Vasia', 'Vasev', 'vasia@gmail.com', 'vasia', '327-32-14', '1991-05-22', 'USER'),
       (8, 'Vlad', 'Vladov', 'vlad@gmail.com', 'vlad', '981-01-18', '1995-06-19', 'USER'),
       (9, 'Admin', 'Admin', 'admin@gmail.com', 'admin', '111-11-11', '1992-01-01', 'ADMIN');
SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO catalog (id, category)
VALUES (1, 'Smartphone'),
       (2, 'TV'),
       (3, 'Headphones');
SELECT SETVAL('catalog_id_seq', (SELECT MAX(id) FROM catalog));

INSERT INTO product (id, brand, model, date_of_release, price, color, image, catalog_id)
VALUES (1, 'APPLE', '12', '2020-09-12', 950, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       (2, 'APPLE', '13', '2021-09-12', 1000, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       (3, 'APPLE', '14', '2022-09-12', 1100, 'WHITE', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       (4, 'SAMSUNG', 'S21', '2022-01-10', 1050, 'WHITE', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       (5, 'SAMSUNG', 'S22', '2022-01-12', 1100, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       (6, 'SAMSUNG', 'S20', '2022-01-12', 900, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       (7, 'SAMSUNG', 'A80J', '2022-01-12', 2000, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'TV')),
        (8, 'SONY', 'XM3', '2019-01-10', 300, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Headphones')),
       (9, 'SONY', 'XM4', '2020-01-10', 350, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Headphones')),
       (10, 'SONY', 'XM5', '2021-01-10', 400, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Headphones')),
       (11, 'APPLE', 'AirPodsMax', '2021-02-15', 400, 'WHITE', 'image', (SELECT id FROM catalog WHERE category = 'Headphones')),
       (12, 'SAMSUNG', 'GalaxyBuds', '2020-10-10', 150, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Headphones'));
SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product));


INSERT INTO orders (id, delivery_city, delivery_street, delivery_building, delivery_date, payment_condition, user_id)
VALUES
    (1, 'Minsk', 'Masherova', 17, '2022-12-10', 'CARD', (SELECT id FROM users WHERE email = 'ivan@gmail.com')),
    (2, 'Minsk', 'Pobedi', 10, '2022-11-05', 'CASH', (SELECT id FROM users WHERE email = 'sveta@gmail.com')),
    (3, 'Smorgon', 'Minskaya', 11, '2022-10-04', 'CARD', (SELECT id FROM users WHERE email = 'petr@gmail.com')),
    (4, 'Minsk', 'Pobedi', 15, '2019-08-04', 'CARD', (SELECT id FROM users WHERE email = 'sasha@gmail.com')),
    (5, 'Minsk', 'Geelyna', 7, '2022-07-04', 'CASH', (SELECT id FROM users WHERE email = 'dima@gmail.com')),
    (6, 'Minsk', 'Vashina', 18, '2019-10-04', 'CASH', (SELECT id FROM users WHERE email = 'ksenia@gmail.com')),
    (7, 'Minsk', 'Porovnu', 55, '2022-11-05', 'CASH', (SELECT id FROM users WHERE email = 'vasia@gmail.com')),
    (8, 'Minsk', 'Vasheva', 10, '2021-12-04', 'CASH', (SELECT id FROM users WHERE email = 'vlad@gmail.com'));
SELECT SETVAL('orders_id_seq', (SELECT MAX(id) FROM orders));

INSERT INTO shopping_cart (created_at, order_id, product_id)
VALUES ('2022-12-10', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'ivan@gmail.com')), (SELECT id FROM product WHERE model = '13')),
       ('2022-11-05', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'sveta@gmail.com')), (SELECT id FROM product WHERE model = '14')),
       ('2022-10-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'petr@gmail.com')), (SELECT id FROM product WHERE model = 'A80J')),
       ('2022-10-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'petr@gmail.com')), (SELECT id FROM product WHERE model = 'S22')),
       ('2022-10-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'petr@gmail.com')), (SELECT id FROM product WHERE model = 'XM3')),
       ('2019-08-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'sasha@gmail.com')), (SELECT id FROM product WHERE model = 'A80J')),
       ('2022-07-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'dima@gmail.com')), (SELECT id FROM product WHERE model = 'XM4')),
       ('2022-07-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'dima@gmail.com')), (SELECT id FROM product WHERE model = 'S22')),
       ('2019-10-04',(SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'ksenia@gmail.com')), (SELECT id FROM product WHERE model = '14')),
       ('2019-10-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'ksenia@gmail.com')), (SELECT id FROM product WHERE model = 'XM4')),
       ('2022-11-05',  (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'vasia@gmail.com')), (SELECT id FROM product WHERE model = 'A80J')),
       ('2021-12-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'vlad@gmail.com')), (SELECT id FROM product WHERE model = 'XM5')),
        ('2021-12-04',(SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'vlad@gmail.com')), (SELECT id FROM product WHERE model = '13')),
        ('2021-12-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'vlad@gmail.com')), (SELECT id FROM product WHERE model = 'S21')),
        ('2021-12-04', (SELECT id FROM orders WHERE user_id = (SELECT id FROM users WHERE email = 'vlad@gmail.com')), (SELECT id FROM product WHERE model = 'S20'));