--liquibase formatted sql

--changeset dosev:021-customers
CREATE TABLE customers (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           email VARCHAR(255) NOT NULL,
                           password_hash VARCHAR(255) NOT NULL,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           phone VARCHAR(20),
                           is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                           created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE customers;

--changeset dosev:022-active-customer-email-index
CREATE UNIQUE INDEX idx_unique_active_customer_email
    ON customers (email)
    WHERE is_deleted = FALSE;
--rollback DROP INDEX idx_unique_active_customer_email;

--changeset dosev:023-admins
CREATE TABLE admins (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password_hash VARCHAR(255) NOT NULL,
                        first_name VARCHAR(50) NOT NULL,
                        last_name VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE admins;