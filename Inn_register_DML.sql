
-- SELECT STATEMENTS 
	SELECT * FROM business_info; 
	SELECT * FROM users;
	SELECT * FROM employees;
    SELECT * FROM roles;
    UPDATE employees 
    SET added_by = 1;
    SELECT * FROM activation_key;
    SELECT * FROM activation_password;
    SELECT admin_key FROM activation_password;
    SELECT * FROM id_types;
    SELECT * FROM designation;
    SELECT * FROM rooms;
    SELECT * FROM roomsCategory;
    SELECT * FROM messageTemplates;
    SELECT * FROM modules;
    SELECT * FROM productPrices;
    SELECT * FROM stores;
    SELECT * FROM ProductItems;
    SELECT * FROM stocklevels;
    SELECT * FROM productBrand;
	SELECT * FROM StocksCategory;
    SELECT * FROM suppliers;

-- STOCK LEVEL TABLE JOIN
   SELECT 
	sl.id, pi.productName, stocklevel, currentStockLevel, currentBoxQuantity,
    currentQuantityPerBox, previousStockLevel, previousBoxQuantity, previousQuantityPerBox,
    gage, username, lastModified
   FROM StockLevels AS sl
   INNER JOIN ProductItems AS pi
		ON productId = pi.id
   INNER JOIN users as us
		ON sl.modifiedBy = us.id
	WHERE pi.deleteStatus = 0;
    
    -- PRODUCT ITEMS TABLE JOIN
    SELECT pi.id, productName, supplyType, CategoryName, supplierName, BrandName, expiryDate, 
    StoreName, note, activeStatus, deleteStatus, username, pi.DateCreated
    FROM productItems as pi
    INNER JOIN StocksCategory as sc
		ON pi.categoryId = sc.id
    INNER JOIN suppliers AS supId
		ON supplieriD = supId.id
    JOIN productBrand AS pb
		ON brandId = pb.id
	JOIN stores AS s
		ON storeId = s.id
	JOIN users AS us
		ON us.id = addedBy
	WHERE deleteStatus = 0;
    
    -- TABLE JOIN FOR messageTemplates 
    SELECT templateId, templateTitle, templateBody, dateCreated, dateModified, username 
    FROM messagetemplates as mt
    INNER JOIN users as u
    ON  u.id = createdBy;
    
    -- TABLE JOIN FOR PRODUCT PRICES.
    SELECT productId, productName purchasePrice, sellingPrice, profitPerItem, previousPurchasePrice, previousSellingPrice, previousProfit, username , dateModified
    FROM productprices as pp
    JOIN productItems as pi 
		ON productId = pp.id
	JOIN users as us
		ON modifiedBy = us.id
	WHERE deleteStatus = 0;
    
    -- TABLE JOIN FOR SENT MESSAGES 
    SELECT sm.id, mobileNumber, messageTitle, messageBody, messageStatus, balance, sentDate, username FROM sentmessages AS sm
    INNER JOIN users AS us
    ON sm.sendBy = us.id;

    ALTER TABLE users 
    ADD COLUMN emp_id INT AFTER confirm_password;
    
    SELECT r.name,  r.id, u.username FROM roles as r
    LEFT JOIN users as u
    ON r.id = u.role_id;
    
	SELECT concat(lastname,' ', firstname) as fullname, gender FROM employees ORDER BY lastname ;
	
    SELECT * FROM employees WHERE(status = 1 AND CONCAT(lastname,' ', firstname) = lower('MCBOND EMELIA'));
    SELECT * FROM employees WHERE(status = 1 AND CONCAT(lastname,' ', firstname) = 'MCBOND EMELIA');
    
    SELECT * FROM roles;

-- TRUNCATE TABLE STATEMENTS ONLY; 
	TRUNCATE TABLE employees;
    TRUNCATE TABLE users;
    TRUNCATE TABLE activation_password;
    TRUNCATE TABLE roles;
    TRUNCATE TABLE rooms;
    TRUNCATE TABLE StocksCategory;
    TRUNCATE TABLE stores;
    TRUNCATE TABLE productitems;
    TRUNCATE TABLE ProductBrand;
    TRUNCATE TABLE StockLevels;

-- ALTER TABLE AND UPDATE STATEMENTS
	UPDATE business_info SET bsi_name = "GUEST HOUSE", updated_date = current_timestamp();
    UPDATE users 
	SET PASSWORD = 'inn@2023';
SELECT DISTINCT(lower(username)) FROM users;
	UPDATE users 
	SET confirm_password = 'inn@2023';

	ALTER TABLE users 
	DROP COLUMN role;
    DROP TABLE rooms;
	ALTER TABLE designtion RENAME designation;
    ALTER TABLE employees MODIFY COLUMN added_by TINYINT DEFAULT 1 AFTER status;
    ALTER TABLE business_info ADD COLUMN hero_image MEDIUMBLOB AFTER bsi_registration_date;
    ALTER  TABLE users MODIFY COLUMN password VARCHAR(255);
    ALTER  TABLE users MODIFY COLUMN confirm_password VARCHAR(255);
    ALTER TABLE users MODIFY COLUMN is_default TINYINT DEFAULT 0 ;
    ALTER TABLE employees ADD COLUMN status TINYINT DEFAULT 1 AFTER salary;
    ALTER TABLE roles DROP column note;
    ALTER TABLE rooms MODIFY COLUMN addedBy VARCHAR(50) DEFAULT 'Super Admin';
    
    -- 27/01/2023
    ALTER TABLE roomscategory ADD COLUMN  price DECIMAL(10, 2) DEFAULT(0.00) AFTER `status`;
    
    /* 11-01-2022 ALTER THE ROLES TABLE.*/

	-- 10/01/2023
    ALTER TABLE employees MODIFY COLUMN photo BLOB;
    -- 13/01/2023
    ALTER TABLE users ADD COLUMN added_by TINYINT AFTER status;
    
    -- 27/01/2023
	ALTER TABLE rooms ADD COLUMN isBooked TINYINT DEFAULT 0 AFTER `status`;
	ALTER TABLE rooms CHANGE COLUMN Price standardPrice DECIMAL(10,2) DEFAULT 0.00;
    
    -- 28/01/23
   ALTER TABLE rooms CHANGE categoryId CategoryName VARCHAR(50);
   
   -- 18/02/2023
   ALTER TABLE ProductStock DROP COLUMN ReceiptNo;    
   ALTER TABLE productstock ADD COLUMN ProductBrand VARCHAR(100) AFTER ProductDescription;
   ALTER TABLE ProductStock ADD COLUMN productQuantity INT AFTER QtyPerPack;
   -- 21/02/2023 
   ALTER TABLE productstock DROP COLUMN UnitQuantity;
   ALTER TABLE productstock DROP COLUMN PackQuantity;
   ALTER TABLE productstock DROP COLUMN QtyPerPack;
   ALTER TABLE productstock DROP COLUMN ProductQuantity;
   ALTER TABLE productstock ADD COLUMN ProductType VARCHAR(10) AFTER PRODUCTNAME;
   
   -- 05-03-2023
   ALTER TABLE productitems MODIFY COLUMN dateCreated DATETIME;
   ALTER TABLE productitems CHANGE COLUMN productStatus  activeStatus TINYINT DEFAULT 0;
   ALTER TABLE productitems CHANGE COLUMN isDeleted  deleteStatus TINYINT DEFAULT 0;
   
   -- 14-03-2023
   ALTER TABLE sentmessages ADD COLUMN balance INT AFTER messageStatus; 
   
	DESCRIBE inn_register.employees;
    DESCRIBE employees;
    DESCRIBE activation_key;
    DESCRIBE business_info;
    DESCRIBE users;
    DESCRIBE rooms;
    
    ALTER TABLE employees 
    MODIFY COLUMN photo BLOB;
	
    -- FOREIGN KEY CHECKS
	SET foreign_key_checks = 0;

UPDATE employees SET modified_date  = DEFAULT 
WHERE id = 10;

ALTER TABLE activation_key 
CHANGE COLUMN expirey_date expiry_date DATE NOT NULL;
INSERT INTO activation_key(activation_code, expiry_date)
VALUES('dsdseewe', '2022-1-1');

INSERT INTO ProductStock(ProductName, ProductDescription, ProductBrand, Category, Supplier, Notes, ExpiryDate, UnitQuantity, PackQuantity, QtyPerPack, productQuantity, AddedBy)
VALUES('VITAMILK', 'SD', 'DSD', 'D', 'DSD',  'DSDSD', '2020-10-20', 5, 5, 5, 5, 1);

-- DROP TABLES SECTION ---
DROP TABLE activation_key; 
DROP TABLE activation_password;
DROP TABLE rooms;
DROP TABLE ProductItems;
DROP TABLE stocklevels;
DROP TABLE productPrices;
