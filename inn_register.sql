CREATE DATABASE IF NOT EXISTS inn_register;
USE inn_register;

-- FIRST TABLE IS THE BUSINESS_INFO TABLE.
CREATE TABLE business_info (
	id INT AUTO_INCREMENT NOT NULL,
	bsi_name VARCHAR(100),
    alias VARCHAR(10),
    bsi_email VARCHAR(100),
    bsi_address VARCHAR(50),
    bsi_number VARCHAR(50),
    bsi_alt_number VARCHAR(50),
    bsi_registration_date DATE,
    bsi_workers INT,
    bsi_description VARCHAR(255),
    hero_image mediumblob,
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

CREATE TABLE roomsCategory(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(50),
    `status` TINYINT DEFAULT(1),
    price DECIMAL(10, 2) DEFAULT(0.00),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE StocksCategory(
	Id INT PRIMARY KEY AUTO_INCREMENT,
    CategoryName VARCHAR(50), 
    DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Suppliers(
	id INT PRIMARY KEY AUTO_INCREMENT,
    status TINYINT DEFAULT 1,
    supplierName VARCHAR(100) NOT NULL,
    contact VARCHAR(50) NOT NULL,
    location VARCHAR(100) NOT NULL,
    DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP
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
    status TINYINT DEFAULT 1,
    added_by INT,
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE users(
	id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50),
    `password` VARCHAR(50),
    confirm_password VARCHAR(50),
    emp_id INT, 
    role_id INT,
    is_default INT DEFAULT 0,
    `status` TINYINT DEFAULT(1),
    added_by TINYINT,
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP,
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE modules(
	id INT AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(50),
    `status` VARCHAR(50),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE activation_key(
	id int AUTO_INCREMENT NOT NULL, 
    activation_code VARCHAR(255),
    start_date DATETIME DEFAULT NOW(),
    expiry_date DATE NULL,
    updated_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
);

CREATE TABLE activation_password(
	id INT PRIMARY KEY AUTO_INCREMENT,
    admin_key VARCHAR(255),
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE rooms(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    roomNo VARCHAR(50),
	categoryId INT,
    `status` TINYINT DEFAULT(1),
    isBooked TINYINT DEFAULT (0),
    standardPrice DECIMAL(10, 2) DEFAULT(0.00),
    dateAdded DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stores(
	id INT PRIMARY KEY AUTO_INCREMENT,
    StoreName VARCHAR(100),
    StoreDescription VARCHAR(100) DEFAULT 'NOT SPECIFIED',
    DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ProductStock(
	Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(255) NOT NULL,
    ProductType VARCHAR(10) NOT NULL,
    ProductBrand VARCHAR(100) NOT NULL,
    Category VARCHAR(50) NOT NULL,
    Supplier VARCHAR(100) DEFAULT 'Unknown',
    Notes TEXT,
	ExpiryDate DATE,
	StoreId INT NOT NULL DEFAULT 1,
    ActiveStatus TINYINT DEFAULT 0,
    DeleteStatus TINYINT DEFAULT 0,
    AddedBy INT,
    DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE ProductBrand(
	Id INT AUTO_INCREMENT PRIMARY KEY,
    BrandName VARCHAR(100),
    DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stockLevels(
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
    ProductId BIGINT,
    StockLevel INT,
    CurrentQty INT,
    PresentUnitQty INT,
    PresentPackQty INT,
    PresentPackPerQty INT,
    PreviousUnitQty INT,
    PreviousPackQty INT,
    PreviousPackPerQty INT,
    BeforeUnitQty INT,
    BeforePackQty INT,
    BeforePerPackQty INT,
    StockGuage INT,
    UpdatedBy INT,
    UpdatedDate DATETIME DEFAULT CURRENT_TIMESTAMP
);



ALTER TABLE users 
ADD FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE SET NULL;



