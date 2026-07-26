--liquibase formatted sql

--changeset dosev:017-furniture-products
CREATE TABLE furniture_products (
                                    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                    title VARCHAR(100) NOT NULL,
                                    description TEXT,
                                    price DECIMAL(10, 2) NOT NULL CHECK (price >= 0),
                                    discount_percentage SMALLINT NOT NULL DEFAULT 0 CHECK (discount_percentage BETWEEN 0 AND 100),
                                    category_id INT NOT NULL REFERENCES categories(id),
                                    furniture_type_id INT NOT NULL REFERENCES furniture_types(id),
                                    material_id INT NOT NULL REFERENCES materials(id),
                                    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
--rollback DROP TABLE furniture_products;

--changeset dosev:018-furniture-variants
CREATE TABLE furniture_variants (
                                    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                    furniture_product_id INT NOT NULL REFERENCES furniture_products(id),
                                    color_id INT NOT NULL REFERENCES colors(id),
                                    sku VARCHAR(50) NOT NULL UNIQUE CHECK (sku = UPPER(sku)),
                                    stock_quantity INT NOT NULL DEFAULT 0 CHECK (stock_quantity >= 0),
                                    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,
                                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                    UNIQUE (furniture_product_id, color_id)
);
--rollback DROP TABLE furniture_variants;

--changeset dosev:019-variant-images
CREATE TABLE variant_images (
                                id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                furniture_variant_id INT NOT NULL REFERENCES furniture_variants(id),
                                image_url TEXT NOT NULL,
                                display_order SMALLINT NOT NULL DEFAULT 0,
                                is_primary BOOLEAN NOT NULL DEFAULT FALSE
);
--rollback DROP TABLE variant_images;

--changeset dosev:020-one-primary-image-index
CREATE UNIQUE INDEX idx_one_primary_image_per_variant
    ON variant_images (furniture_variant_id)
    WHERE is_primary = TRUE;
--rollback DROP INDEX idx_one_primary_image_per_variant;