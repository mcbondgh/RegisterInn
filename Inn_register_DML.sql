
-- SELECT STATEMENTS 
	SELECT * FROM business_info; 
	SELECT * FROM users;
	SELECT * FROM employees WHERE status = 1;
    SELECT * FROM roles;
    UPDATE employees 
    SET added_by = 1;
    
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

-- ALTER TABLE AND UPDATE STATEMENTS
	UPDATE business_info SET bsi_name = "GUEST HOUSE", updated_date = current_timestamp();
    UPDATE users 
	SET PASSWORD = 'inn@2023';

	UPDATE users 
	SET confirm_password = 'inn@2023';

	ALTER TABLE users 
	DROP COLUMN role;
	ALTER TABLE designtion RENAME designation;
    ALTER TABLE employees ADD COLUMN added_by TINYINT AFTER status;

	DESCRIBE inn_register.employees;
	
	SET foreign_key_checks = 1;


UPDATE employees SET modified_date  = DEFAULT 
WHERE id = 10;


