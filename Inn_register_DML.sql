
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
    SELECT * FROM modules;
    SELECT * FROM StocksCategory;
    SELECT * FROM suppliers;
    SELECT * FROM stores;
    SELECT * FROM ProductStock;
    SELECT * FROM ProductBrand;

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
    TRUNCATE TABLE ProductStock;

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
   
	DESCRIBE inn_register.employees;
    DESCRIBE employees;
    DESCRIBE activation_key;
    DESCRIBE business_info;
    DESCRIBE users;
    DESCRIBE rooms;
    
    ALTER TABLE employees 
    MODIFY COLUMN photo BLOB;
	
	SET foreign_key_checks = 1;

UPDATE employees SET modified_date  = DEFAULT 
WHERE id = 10;

ALTER TABLE activation_key 
CHANGE COLUMN expirey_date expiry_date DATE NOT NULL;
INSERT INTO activation_key(activation_code, expiry_date)
VALUES('dsdseewe', '2022-1-1');



-- DROP TABLES SECTION ---
DROP TABLE activation_key; 
DROP TABLE activation_password;
DROP TABLE rooms;