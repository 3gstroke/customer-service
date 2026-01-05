-- 1. Crear schema
CREATE SCHEMA IF NOT EXISTS customer_db;

-- 2. Secuencia (Panache)
CREATE SEQUENCE IF NOT EXISTS customer_db.customers_seq
    START WITH 1
    INCREMENT BY 50;

-- 3. Tabla customers
CREATE TABLE IF NOT EXISTS customer_db.customers (
    id BIGINT NOT NULL DEFAULT nextval('customer_db.customers_seq'),
    document_number VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    CONSTRAINT pk_customers PRIMARY KEY (id),
    CONSTRAINT uk_customers_document UNIQUE (document_number),
    CONSTRAINT uk_customers_email UNIQUE (email)
);


INSERT INTO customer_db.customers (document_number, email, name, status) VALUES
('DOC001', 'user1@mail.com',  'Juan Pérez',      'ACTIVE'),
('DOC002', 'user2@mail.com',  'María Gómez',     'ACTIVE'),
('DOC003', 'user3@mail.com',  'Carlos López',    'ACTIVE'),
('DOC004', 'user4@mail.com',  'Ana Torres',      'INACTIVE'),
('DOC005', 'user5@mail.com',  'Luis Fernández',  'ACTIVE'),
('DOC006', 'user6@mail.com',  'Sofía Ramírez',   'ACTIVE'),
('DOC007', 'user7@mail.com',  'Pedro Sánchez',   'INACTIVE'),
('DOC008', 'user8@mail.com',  'Lucía Castro',    'ACTIVE'),
('DOC009', 'user9@mail.com',  'Jorge Medina',    'ACTIVE'),
('DOC010', 'user10@mail.com', 'Valentina Ruiz',  'ACTIVE');
