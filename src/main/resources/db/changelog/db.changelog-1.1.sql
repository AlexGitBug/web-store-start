--liquibase formatted sql

--changeset anisimov:1
ALTER TABLE users
    ADD COLUMN version INT;