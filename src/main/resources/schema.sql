CREATE TABLE product_package (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE,
    product_ids VARCHAR(100) ARRAY
);