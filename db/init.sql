CREATE TABLE account
(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    status VARCHAR(10) NOT NULL CHECK (status IN ('ACTIVE', 'INACTIVE')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE customer
(
	id SERIAL PRIMARY KEY,    
	account_id INTEGER REFERENCES account(id) NOT NULL UNIQUE,
    gender VARCHAR(10) NOT NULL CHECK (gender IN ('FEMALE', 'MALE', 'OTHER')),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    date_of_birth DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE customer_address
(
    id SERIAL PRIMARY KEY,
    customer_id INTEGER REFERENCES customer(id) NOT NULL,
    title VARCHAR(100) NOT NULL,
    country VARCHAR(100) NOT NULL,
    city VARCHAR(100) NOT NULL,
    district VARCHAR(100) NOT NULL,
    street VARCHAR(100) NOT NULL,
    house_number VARCHAR(20) NOT NULL,
    post_code VARCHAR(20) NOT NULL,
    description TEXT DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE book
(
	id SERIAL PRIMARY KEY,    
    title VARCHAR(100) NOT NULL,
    genre VARCHAR(50) NOT NULL,
    author VARCHAR(100) NOT NULL,
    publish_date DATE NOT NULL,
    pages INTEGER NOT NULL,
    price DECIMAL(8,2) NOT NULL CHECK (price > 0.00),
	total_quantity INTEGER NOT NULL CHECK (total_quantity >= 0),
    sold_quantity INTEGER NOT NULL CHECK (sold_quantity >= 0),
    available_quantity INTEGER NOT NULL CHECK (available_quantity >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE orders
(
	id SERIAL PRIMARY KEY, 
	order_code UUID NOT NULL UNIQUE,
	customer_id INTEGER REFERENCES customer(id) NOT NULL,
    book_id INTEGER REFERENCES book(id) NOT NULL,
    delivery_address_id INTEGER REFERENCES customer_address(id) NOT NULL,
    order_quantity INTEGER NOT NULL CHECK (order_quantity > 0),
    order_price DECIMAL(8,2) NOT NULL CHECK (order_price > 0.00),
    status VARCHAR(15) NOT NULL CHECK (status IN ('IN_PROGRESS', 'PACKAGED', 'TRANSPORTING', 'IN_DELIVERY', 'DELIVERED', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(255) NOT NULL,
    version INTEGER NOT NULL DEFAULT 0
);

INSERT INTO account(id, email, phone, password, status, created_by, updated_by)
VALUES (1, 'test@test.com', '01234567890', '$2a$10$nTiFm7Sv23IpykI3G2IC4u/Xcfe90ql35e7H00ya9ZdH35ZX5vMrS', 'ACTIVE', 'AUTO', 'AUTO');

INSERT INTO customer(id, account_id, gender, first_name, last_name, date_of_birth, created_by, updated_by)
VALUES (1, 1, 'MALE', 'Firstname', 'Lastname', '1990-01-01', 'AUTO', 'AUTO');