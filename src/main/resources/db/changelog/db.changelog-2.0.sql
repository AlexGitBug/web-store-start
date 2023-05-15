--liquibase formatted sql

--changeset anisimov:1
-- ALTER TABLE orders
--     ADD COLUMN status VARCHAR(128);