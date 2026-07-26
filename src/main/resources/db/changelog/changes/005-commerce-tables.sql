--liquibase formatted sql

--changeset dosev:024-cart-items
CREATE TABLE cart_items (
                            id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            customer_id UUID NOT NULL REFERENCES customers(id),
                            furniture_variant_id INT NOT NULL REFERENCES furniture_variants(id),
                            quantity INT NOT NULL CHECK (quantity > 0),
                            created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            UNIQUE (customer_id, furniture_variant_id)
);
--rollback DROP TABLE cart_items;

--changeset dosev:025-cart-items-customer-index
CREATE INDEX idx_cart_items_customer ON cart_items(customer_id);
--rollback DROP INDEX idx_cart_items_customer;

--changeset dosev:026-orders
CREATE TABLE orders (
                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        order_code VARCHAR(7) NOT NULL UNIQUE,
                        customer_id UUID NOT NULL REFERENCES customers(id),
                        status_id INT NOT NULL REFERENCES order_statuses(id),
                        fulfillment_method VARCHAR(10) NOT NULL
                            CHECK (fulfillment_method IN ('pickup','delivery')),
                        address_line VARCHAR(255),
                        city VARCHAR(100),
                        postal_code VARCHAR(20),
                        country VARCHAR(100),
                        total_amount DECIMAL(10, 2) NOT NULL CHECK (total_amount >= 0),
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CHECK (
                            (fulfillment_method = 'pickup'
                                AND address_line IS NULL AND city IS NULL AND postal_code IS NULL AND country IS NULL)
                                OR
                            (fulfillment_method = 'delivery'
                                AND address_line IS NOT NULL AND city IS NOT NULL AND postal_code IS NOT NULL AND country IS NOT NULL)
                            )
);
--rollback DROP TABLE orders;

--changeset dosev:027-order-code-trigger
CREATE TRIGGER trg_set_order_code
    BEFORE INSERT ON orders
    FOR EACH ROW
    EXECUTE FUNCTION set_order_code();
--rollback DROP TRIGGER IF EXISTS trg_set_order_code ON orders;

--changeset dosev:028-order-items
CREATE TABLE order_items (
                             id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             order_id INT NOT NULL REFERENCES orders(id),
                             furniture_variant_id INT NOT NULL REFERENCES furniture_variants(id),
                             quantity INT NOT NULL CHECK (quantity > 0),
                             unit_price DECIMAL(10, 2) NOT NULL CHECK (unit_price >= 0),
                             discount_percentage SMALLINT NOT NULL DEFAULT 0 CHECK (discount_percentage BETWEEN 0 AND 100),
                             discounted_unit_price DECIMAL(10, 2)
                                 GENERATED ALWAYS AS (ROUND(unit_price * (1 - discount_percentage / 100.0), 2)) STORED,
                             line_total DECIMAL(10, 2)
                                 GENERATED ALWAYS AS (ROUND(unit_price * (1 - discount_percentage / 100.0) * quantity, 2)) STORED,
                             created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE order_items;

--changeset dosev:029-order-items-indexes
CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_variant ON order_items(furniture_variant_id);
--rollback DROP INDEX idx_order_items_order;

--changeset dosev:030-returns
CREATE TABLE returns (
                         id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         order_item_id INT NOT NULL REFERENCES order_items(id),
                         quantity INT NOT NULL CHECK (quantity > 0),
                         reason TEXT,
                         status_id INT NOT NULL REFERENCES return_statuses(id),
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE returns;

--changeset dosev:031-returns-index
CREATE INDEX idx_returns_order_item ON returns(order_item_id);
--rollback DROP INDEX idx_returns_order_item;

--changeset dosev:032-reviews
CREATE TABLE reviews (
                         id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         customer_id UUID NOT NULL REFERENCES customers(id),
                         furniture_product_id INT NOT NULL REFERENCES furniture_products(id),
                         rating SMALLINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                         comment TEXT,
                         created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         UNIQUE (customer_id, furniture_product_id)
);
--rollback DROP TABLE reviews;

--changeset dosev:033-reviews-index
CREATE INDEX idx_reviews_product ON reviews(furniture_product_id);
--rollback DROP INDEX idx_reviews_product;