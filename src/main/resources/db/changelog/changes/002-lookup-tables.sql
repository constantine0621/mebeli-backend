--liquibase formatted sql

--changeset dosev:005-categories
CREATE TABLE categories (
                            id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            category_name VARCHAR(50) UNIQUE NOT NULL CHECK (category_name = LOWER(category_name))
);
--rollback DROP TABLE categories;

--changeset dosev:006-furniture-types
CREATE TABLE furniture_types (
                                 id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 type_name VARCHAR(50) UNIQUE NOT NULL CHECK (type_name = LOWER(type_name))
);
--rollback DROP TABLE furniture_types;

--changeset dosev:007-colors
CREATE TABLE colors (
                        id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                        color_name VARCHAR(30) UNIQUE NOT NULL CHECK (color_name = LOWER(color_name))
);
--rollback DROP TABLE colors;

--changeset dosev:008-materials
CREATE TABLE materials (
                           id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           material_name VARCHAR(50) UNIQUE NOT NULL CHECK (material_name = LOWER(material_name))
);
--rollback DROP TABLE materials;

--changeset dosev:009-order-statuses
CREATE TABLE order_statuses (
                                id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                status_name VARCHAR(20) UNIQUE NOT NULL CHECK (status_name = LOWER(status_name))
);
--rollback DROP TABLE order_statuses;

--changeset dosev:010-return-statuses
CREATE TABLE return_statuses (
                                 id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                 status_name VARCHAR(20) UNIQUE NOT NULL CHECK (status_name = LOWER(status_name))
);
--rollback DROP TABLE return_statuses;

--changeset dosev:011-seed-categories
INSERT INTO categories (category_name) VALUES
                                           ('living room'), ('kitchen'), ('bedroom'), ('office'), ('entryway');
--rollback DELETE FROM categories;

--changeset dosev:012-seed-furniture-types
INSERT INTO furniture_types (type_name) VALUES
                                            ('sofa'), ('chair'), ('table'), ('bench'), ('desk');
--rollback DELETE FROM furniture_types;

--changeset dosev:013-seed-colors
INSERT INTO colors (color_name) VALUES
                                    ('tan'), ('olive'), ('charcoal'), ('emerald'), ('walnut natural');
--rollback DELETE FROM colors;

--changeset dosev:014-seed-materials
INSERT INTO materials (material_name) VALUES
                                          ('cognac leather'), ('velvet'), ('solid oak'), ('bouclé'), ('walnut veneer');
--rollback DELETE FROM materials;

--changeset dosev:015-seed-order-statuses
INSERT INTO order_statuses (status_name) VALUES
                                             ('pending'), ('paid'), ('shipped'), ('delivered'), ('cancelled'), ('returned');
--rollback DELETE FROM order_statuses;

--changeset dosev:016-seed-return-statuses
INSERT INTO return_statuses (status_name) VALUES
                                              ('requested'), ('approved'), ('rejected'), ('completed');
--rollback DELETE FROM return_statuses;