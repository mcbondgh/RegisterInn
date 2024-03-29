
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
    SELECT * FROM payment_transaction;
    SELECT * FROM internal_stock_items;
    SELECT * FROM checkin;
    
    SELECT productId, productName, purchasePrice, sellingPrice, profitPerItem, previousPurchasePrice, previousSellingPrice, previousProfit, username , dateModified
          FROM productprices as pp
		JOIN productItems as pi 
		ON productId = pi.id
		JOIN users as us
		ON modifiedBy = us.id
		WHERE deleteStatus = 0;

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
    SELECT productId, productName, purchasePrice, sellingPrice, profitPerItem, previousPurchasePrice, previousSellingPrice, previousProfit, username , dateModified
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
    
    -- TABLE JOIN FOR CHECK _IN FOR SELECTING ONLY TODAY'S TRANSACTIONS ONLY
    SELECT checkin_id, roomNo, checkin_time, due_time, check_in_status, allotedTime, ci.date_created as checkin_date FROM checkin as ci
	INNER JOIN rooms as r
		ON ci.room_id = r.id
	INNER JOIN roomprices as rp
		ON duration_id = rp.id
	WHERE DATE(ci.date_created)=current_date() or check_in_status = 1;
    
    -- TABLE JOIN FOR INTERNAL_STOCK_ITEMS ON USERS TABLE TO GET USERNAME BY ID
    SELECT itemId, internal_item_name, internal_item_category, remaining_quantity, current_quantity, previous_quantity, total_cost_price, username, date_created, isDeleted, date_modified FROM internal_stock_items as si 
    INNER JOIN users as u
		ON si.added_by = u.id 
    WHERE(isDeleted = 0) ORDER BY internal_item_name ASC;
    
    -- TABLE JOIN FOR CHECKOUT TABLE VIEW...
    SELECT ci.checkin_id, guest_name, guest_number, r.roomNo, rp.name, pt.payment_method, total_bill, due_time, checkin_time, co.checkout_time, overtime, checkin_comment, username, ci.date_created FROM guests AS g
		JOIN checkin as ci
                    ON ci.checkin_id = g.checkin_id
                    JOIN rooms as r
                    ON ci.room_id = r.id
                    JOIN roomprices as rp
                    ON duration_id = rp.id
                    JOIN payment_transaction as pt
                    ON ci.checkin_id = pt.checkin_id
                    JOIN checkout AS co
                    ON ci.checkin_id = co.checkin_id
                    JOIN users as u
                    ON ci.booked_by = u.id
                   WHERE DATE(ci.date_created) BETWEEN '2023-05-05'  AND '2023-05-06' ;
    
    -- TABLE JOIN FOR STOCK LEVEL 
    SELECT pi.id, productName, stockLevel, sellingPrice FROM stocklevels AS sl
    INNER JOIN productItems AS pi 
		ON sl.productId = pi.id
	INNER JOIN productprices AS pp
		ON pi.id = pp.productid
	WHERE stockLevel > 0;
    
    
    -- TABLE JOIN TO GET SALES SUMMARY;
    SELECT DISTINCT(sales_id), pi.productName, s.item_quantity, s.item_cost, s.sales_date, username FROM sales_transaction AS s
    INNER JOIN productItems AS pi ON s.item_id = pi.id
    INNER JOIN users AS u INNER JOIN sales_payments as sp
    ON u.id = payment_added_by 
    WHERE payment_added_by = 1 and s.sales_trans_id = sp.sales_trans_id AND DATE(s.sales_date) BETWEEN 2023-05-14 AND CURRENT_DATE();
    
    -- TABLE JOIN FORM EXTRA TIME 
    SELECT booking_id, rm.id AS room_id, roomNo, ext.date_created, exit_time, name FROM extra_time AS ext
    INNER JOIN roomPrices AS rp
		ON duration_id = rp.id
	INNER JOIN rooms AS rm 
    INNER JOIN checkin AS ci
		ON rm.id = ci.room_id
	WHERE booking_id = ci.checkin_id;
    
    
    -- TABLE JOIN TO GET INTERNAL REQUEST TITMS BASES ON REQUESTED ITEMS ONLY 
    SELECT request_id, internal_item_name, request_status, requested_quantity, requested_date, username FROM internal_stock_request AS isr
INNER JOIN internal_stock_items isi
	ON itemId = stock_id
INNER JOIN users AS u
	ON requested_by = u.id
WHERE request_status = 0;
	
    -- TABLE JOIN FOR SELECTING INTERNAL REQUEST ITEMS BASED ON ITEM CATEGORY.
SELECT itemId, internal_item_name, remaining_quantity, requested_quantity FROM internal_stock_items
INNER JOIN internal_stock_request ON
itemId = stock_id
WHERE(internal_item_category = "WASHING SOAP" AND remaining_quantity > 0 AND remaining_quantity = 0);
    
    SELECT itemId, internal_item_name, remaining_quantity, is_requested FROM internal_stock_items
    WHERE(internal_item_category = "WASHING SOAP" AND remaining_quantity > 0);
    
    
    SELECT itemId, internal_item_name, remaining_quantity FROM internal_stock_items
    WHERE(internal_item_category = "WASHING SOAP");
    
    ALTER TABLE users 
    ADD COLUMN emp_id INT AFTER confirm_password;
    
    SELECT r.name,  r.id, u.username FROM roles as r
    LEFT JOIN users as u
    ON r.id = u.role_id;
    
	SELECT concat(lastname,' ', firstname) as fullname, gender FROM employees ORDER BY lastname ;
	
    SELECT SUM(total_bill) as total_sales FROM sales_payments WHERE (payment_added_by = 1 AND DATE(payment_date) = CURRENT_DATE());
    
    SELECT * FROM employees WHERE(status = 1 AND CONCAT(lastname,' ', firstname) = lower('MCBOND EMELIA'));
    SELECT * FROM employees WHERE(status = 1 AND CONCAT(lastname,' ', firstname) = 'MCBOND EMELIA');
    
    SELECT SUM(total_bill) AS amount FROM payment_transaction WHERE added_by = 2 AND DATE(date_created) = CURRENT_DATE();
    
   SELECT payment_id as pi FROM sales_payments ORDER BY pi DESC LIMIT 1; 
    
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
    TRUNCATE TABLE productprices;
    TRUNCATE TABLE messageTemplates;
    TRUNCATE TABLE guests;
    TRUNCATE TABLE checkin;
    TRUNCATE TABLE payment_transaction;
    Truncate Table checkout;

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
   
   -- 31/03/2023
   ALTER TABLE roomprices ADD COLUMN allotedTime INT AFTER price;
   
   -- 08/05/2023
   ALTER TABLE productitems
   MODIFY COLUMN activeStatus BOOLEAN DEFAULT 1;
   ALTER TABLE roomsCategory RENAME roomPrices;
   
   -- 10/05/23
   ALTER TABLE sales_transaction CHANGE sales_id sales_id BIGINT AUTO_INCREMENT PRIMARY KEY ;
   ALTER TABLE sales_transaction DROP primary key;
   
   
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

-- INSERT INTO sms_api_key(api_key, sender_name) VALUES('5c926b098c1087dac3f6', 'InnRegister');

-- DROP TABLES SECTION ---
DROP TABLE activation_key; 
DROP TABLE activation_password;
DROP TABLE rooms;
DROP TABLE ProductItems;
DROP TABLE stocklevels;
DROP TABLE productPrices;
DROP TABLE checkin;
