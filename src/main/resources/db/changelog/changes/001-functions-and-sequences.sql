--liquibase formatted sql

--changeset dosev:001-order-code-sequence
CREATE SEQUENCE order_code_seq START 1;
--rollback DROP SEQUENCE order_code_seq;

--changeset dosev:002-int-to-base26-letters splitStatements:false
CREATE OR REPLACE FUNCTION int_to_base26_letters(n INT) RETURNS TEXT AS $$
DECLARE
result TEXT := '';
    num INT := n;
BEGIN
FOR i IN 1..3 LOOP
        result := chr(97 + (num % 26)) || result;
        num := num / 26;
END LOOP;
RETURN result;
END;
$$ LANGUAGE plpgsql IMMUTABLE;
--rollback DROP FUNCTION IF EXISTS int_to_base26_letters(INT);

--changeset dosev:003-generate-order-code splitStatements:false
CREATE OR REPLACE FUNCTION generate_order_code(seq_val BIGINT) RETURNS TEXT AS $$
DECLARE
letter_index INT;
    number_part INT;
BEGIN
    letter_index := (seq_val - 1) / 999;
    number_part := ((seq_val - 1) % 999) + 1;
RETURN int_to_base26_letters(letter_index) || '-' || lpad(number_part::text, 3, '0');
END;
$$ LANGUAGE plpgsql IMMUTABLE;
--rollback DROP FUNCTION IF EXISTS generate_order_code(BIGINT);

--changeset dosev:004-set-order-code-trigger-fn splitStatements:false
CREATE OR REPLACE FUNCTION set_order_code() RETURNS TRIGGER AS $$
BEGIN
    NEW.order_code := generate_order_code(nextval('order_code_seq'));
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
--rollback DROP FUNCTION IF EXISTS set_order_code();