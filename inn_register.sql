CREATE DATABASE IF NOT EXISTS inn_register;
USE inn_register;

CREATE TABLE IF NOT EXISTS db_connection(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    server_name VARCHAR(100) NOT NULL,
    database_name VARCHAR(100) NOT NULL,
    db_username VARCHAR(100) NOT NULL,
    db_password VARCHAR(100),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);
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

CREATE TABLE roomPrices(
	id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(50),
    `status` TINYINT DEFAULT(1),
    price DECIMAL(10, 2) DEFAULT(0.00),
    allotedTime INT,
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

CREATE TABLE sms_api_key(
	id iNT  PRIMARY KEY,
    api_key VARCHAR(255),
    sender_name VARCHAR(20) NOT NULL,
    dae_added DATETIME DEFAULT NOW()
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

CREATE TABLE ProductItems(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100),
    supplyType VARCHAR(50),
    categoryId INT,
    supplierId INT,
    brandID INT,
    storeId INT DEFAULT 1,
    ExpiryDate DATE,
    note TEXT,
    activeStatus TINYINT DEFAULT 1,
    DeleteStatus TINYINT DEFAULT 0,
    addedBy INT,
    dateCreated DATETIME 
);

CREATE TABLE ProductBrand(
	Id INT AUTO_INCREMENT PRIMARY KEY,
    BrandName VARCHAR(100),
    DateCreated DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stockLevels(
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
    productId BIGINT,
    stockLevel INT,
    currentStockLevel INT,
    currentBoxQuantity INT,
    currentQuantityPerBox INT,
    oldStockLevel INT,
    previousStockLevel INT,
    previousBoxQuantity INT,
    previousQuantityPerBox INT,
    gage INT,
    modifiedBy INT,
    lastModified DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE productPrices(
	id BIGINT PRIMARY KEY AUTO_INCREMENT, 
    productId BIGINT,
    purchasePrice DECIMAL(5,2) DEFAULT 0.00,
    sellingPrice DECIMAL(5,2) DEFAULT 0.00,
    profitPerItem DECIMAL(5,2) DEFAULT 0.00,
    previousPurchasePrice DECIMAL(5,2) DEFAULT 0.00,
    previousSellingPrice DECIMAL(5,2) DEFAULT 0.00,
    previousProfit DECIMAL(5,2) DEFAULT 0.00,
    modifiedBy INT,
    dateModified DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE internal_stock_items(
	itemId INT AUTO_INCREMENT PRIMARY KEY,
    internal_item_name VARCHAR(255),
    internal_item_category VARCHAR(100), 
    remaining_quantity INT NOT NULL,
    current_quantity INT NOT NULL,
    previous_quantity INT NOT NULL,
    total_cost_price DECIMAL(10,2),
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP,
    added_by INT,
    is_requested BOOLEAN DEFAULT 0,
    isDeleted BOOLEAN DEFAULT FALSE,
    date_modified DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- ALTER TABLE internal_stock_items ADD COLUMN is_requested BOOLEAN DEFAULT 0 AFTER added_by;

CREATE TABLE sentMessages(
	id INT AUTO_INCREMENT PRIMARY KEY,
    mobileNumber INT,
    messageTitle VARCHAR(255),
    messageBody TEXT,
    messageStatus TINYTEXT,
    sentDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    sendBy int
);

CREATE TABLE messageTemplates(
	templateId INT PRIMARY KEY AUTO_INCREMENT,
    templateTitle VARCHAR(100) NOT NULL,
    templateBody TEXT NOT NULL, 
    createdBy TINYINT DEFAULT 1,
    dateCreated DATETIME,
    dateModified DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE guests(
	guest_id INT AUTO_INCREMENT PRIMARY KEY,
    checkin_id int,
    guest_name VARCHAR(255),
    guest_number VARCHAR(100),
    guest_id_type VARCHAR(50),
    guest_id_number VARCHAR(50),
    date_added DATETIME DEFAULT CURRENT_TIMESTAMP(),
    added_by int   
);

CREATE TABLE checkIn(
	checkin_id INT AUTO_INCREMENT PRIMARY KEY,
    room_id INT,
    duration_id INT,
    checkin_time TIME, 
    due_time TIME, 
    check_in_status BOOLEAN DEFAULT FALSE,
	checkin_comment TEXT,
    booked_by int,
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE checkout(
	checkout_id INT AUTO_INCREMENT PRIMARY KEY,
    checkin_id INT,
    guestName VARCHAR(100),
    roomNo VARCHAR(100),
    checkout_time TIME,
    overtime VARCHAR(50),
    checkedout_by INT,
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS extra_time(
	extra_id INT AUTO_INCREMENT,
    booking_id INT,
    duration_id int,
    is_active BOOLEAN DEFAULT 1, -- (0) MEANS THE EXTRA-TIME IS CHECKED OUT (1) MEANS THE CHECK OUT IS active
    exit_time DATETIME,
    payment_method VARCHAR(20),
    cash DOUBLE(10,2),
    momo DOUBLE(10,2),
    momo_trans_id LONG,
    booked_by INT NOT NULL,
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(extra_id),
    FOREIGN KEY(booking_id) REFERENCES checkin(checkin_id) ON DELETE SET NULL,
    FOREIGN KEY(duration_id) REFERENCES roomprices(id) ON DELETE SET NULL
);

CREATE TABLE payment_Transactions(
	payment_Id INT AUTO_INCREMENT PRIMARY KEY,
    checkin_id INT,
	payment_method VARCHAR(50),
    cash_amount DECIMAL(10,2),
    momo_amount DECIMAL(10,2),
    transaction_ID LONG,
    total_bill DECIMAL(10,2),
    client_change DECIMAL(10,2),
    added_by INT,
    date_created DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales_payments(
	payment_id INT AUTO_INCREMENT PRIMARY KEY,
    sales_trans_id VARCHAR(50) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    momo_trans_id VARCHAR(50) DEFAULT '0',
    total_bill DECIMAL(10,2) NOT NULL,
    sales_change DECIMAL(5,2),
    sales_note VARCHAR(255),
    payment_added_by INT NOT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE sales_transaction(
	sales_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    item_id INT NOT NULL,
    item_quantity INT NOT NULL,
    item_cost DECIMAL(5,2) NOT NULL,
    sales_trans_id VARCHAR(50),
    payment_id INT NOT NULL,
    sales_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(payment_id) REFERENCES sales_payments(payment_id)
);

CREATE TABLE internal_stock_request(
	request_id INT AUTO_INCREMENT PRIMARY KEY,
    stock_id INT,
    requested_quantity INT NOT NULL,
    request_status BOOLEAN DEFAULT FALSE,
    requested_date VARCHAR(50),
    requested_by INT,
    approved_date VARCHAR(50),
    approved_by INT,
    date_created DATETIME DEFAULT NOW()
);





ALTER TABLE users 
ADD FOREIGN KEY(role_id) REFERENCES roles(id) ON DELETE SET NULL;



