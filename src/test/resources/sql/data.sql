INSERT INTO users (first_name, last_name, email, password, telephone, birth_date, role)
VALUES ('Ivan', 'Ivanov', 'ivan@gmail.com', 'ivan', '123-45-67', '1990-10-10', 'USER'),
       ('Sveta', 'Svetikova', 'sveta@gmail.com', 'sveta', '123-67-47', '1985-02-02', 'USER'),
       ('Petr', 'Petrov', 'petr@gmail.com', 'petr', '321-45-67', '1980-03-03', 'USER'),
       ('Sasha', 'Sasheva', 'sasha@gmail.com', 'sasha', '123-45-78', '1991-10-05', 'USER'),
       ('Dima', 'Dimov', 'dima@gmail.com', 'dima', '321-32-32', '1988-03-18', 'USER'),
       ('Ksenia', 'Kseneva', 'ksenia@gmail.com', 'ksenia', '123-32-32', '1992-07-18', 'USER'),
       ('Vasia', 'Vasev', 'vasia@gmail.com', 'vasia', '327-32-14', '1991-05-22', 'USER'),
       ('Vlad', 'Vladov', 'vlad@gmail.com', 'vlad', '981-01-18', '1995-06-19', 'USER'),
       ('Admin', 'Admin', 'admin@gmail.com', 'admin', '111-11-11', '1992-01-01', 'ADMIN');
-- SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO catalog (category)
VALUES ('Smartphone'),
       ('TV'),
       ('Headphones');
-- SELECT SETVAL('catalog_id_seq', (SELECT MAX(id) FROM catalog));

INSERT INTO product (brand, model, date_of_release, price, color, image, catalog_id)
VALUES ('APPLE', '12', '2020-09-12', 950, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       ('APPLE', '13', '2021-09-12', 1000, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       ('APPLE', '14', '2022-09-12', 1100, 'WHITE', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       ('SAMSUNG', 'S21', '2022-01-10', 1050, 'WHITE', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       ('SAMSUNG', 'S22', '2022-01-12', 1100, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       ('SAMSUNG', 'S20', '2022-01-12', 900, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'Smartphone')),
       ('SAMSUNG', 'A80J', '2022-01-12', 2000, 'BLACK', 'image', (SELECT id FROM catalog WHERE category = 'TV'));
-- SELECT SETVAL('product_id_seq', (SELECT MAX(id) FROM product));

INSERT INTO orders (delivery_city, delivery_street, delivery_building, delivery_date, payment_condition, user_id)
VALUES
    ('Minsk', 'Masherova', 17, '2022-12-10', 'CARD', (SELECT id FROM users WHERE email = 'ivan@gmail.com')),
    ('Minsk', 'Pobedi', 10, '2022-11-05', 'CASH', (SELECT id FROM users WHERE email = 'sveta@gmail.com')),
    ('Smorgon', 'Minskaya', 11, '2022-10-04', 'CARD', (SELECT id FROM users WHERE email = 'petr@gmail.com')),
    ('Minsk', 'Pobedi', 15, '2019-08-04', 'CARD', (SELECT id FROM users WHERE email = 'sasha@gmail.com')),
    ('Minsk', 'Geelyna', 7, '2022-07-04', 'CASH', (SELECT id FROM users WHERE email = 'dima@gmail.com')),
    ('Minsk', 'Vashina', 18, '2019-10-04', 'CASH', (SELECT id FROM users WHERE email = 'ksenia@gmail.com')),
    ('Minsk', 'Porovnu', 55, '2022-11-05', 'CASH', (SELECT id FROM users WHERE email = 'vasia@gmail.com')),
    ('Minsk', 'Vasheva', 10, '2021-12-04', 'CASH', (SELECT id FROM users WHERE email = 'vlad@gmail.com'));

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