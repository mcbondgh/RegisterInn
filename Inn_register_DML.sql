
-- SELECT STATEMENTS 
	SELECT * FROM business_info; 
	SELECT * FROM users;
	SELECT * FROM employees;
    SELECT * FROM roles;
    
    SELECT r.name,  r.id, u.username FROM roles as r
    LEFT JOIN users as u
    ON r.id = u.role_id;
    
	SELECT concat(lastname, " ", firstname) as fullname, gender FROM employees ORDER BY lastname ;
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
	
	SET foreign_key_checks = 1



