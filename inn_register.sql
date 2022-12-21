CREATE DATABASE IF NOT EXISTS inn_register;
USE inn_register;

-- FIRST TABLE IS THE BUSINESS_INFO TABLE.
CREATE TABLE business_info (
	id INT AUTO_INCREMENT NOT NULL,
	bsi_name VARCHAR(100),
    bsi_email VARCHAR(100),
    bsi_address VARCHAR(50),
    bsi_number VARCHAR(50),
    bsi_alt_number VARCHAR(50),
    bsi_registration_date DATE,
    bsi_workers INT,
    bsi_description VARCHAR(255),
    mng_name VARCHAR(100),
    mng_number VARCHAR(50),
    mng_email VARCHAR(100),
    updated_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
); 

CREATE TABLE roles(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(50),
    note VARCHAR(100),
    `status` TINYINT DEFAULT(1),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE id_types(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(50),
    `status` TINYINT DEFAULT(1),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE designtion(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(50),
    `status` TINYINT DEFAULT(1),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE employees(
	id INT AUTO_INCREMENT PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50),
    gender VARCHAR(50),
    email VARCHAR(50),
    phone VARCHAR(50),
    digital_address VARCHAR(50),
    id_type VARCHAR(50),
    id_number VARCHAR(50),
    employment_date DATE,
    designation VARCHAR(50),
    photo VARCHAR(100),
    salary DECIMAL(10,2),
    added_by INT,
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    `password` VARCHAR(50),
    confirm_password VARCHAR(50),
    `role` VARCHAR(50),
    role_id INT,
    `status` TINYINT DEFAULT(1),
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE modules(
	id INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50),
    `status` VARCHAR(50),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE users 
ADD FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE SET NULL; 

