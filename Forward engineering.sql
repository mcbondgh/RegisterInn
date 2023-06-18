-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema inn_register
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema inn_register
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `inn_register` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `inn_register` ;

-- -----------------------------------------------------
-- Table `inn_register`.`activation_key`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`activation_key` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `activation_code` VARCHAR(255) NULL DEFAULT NULL,
  `start_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `expiry_date` DATE NOT NULL,
  `updated_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`activation_password`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`activation_password` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `admin_key` VARCHAR(255) NULL DEFAULT NULL,
  `date_added` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`business_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`business_info` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `bsi_name` VARCHAR(100) NULL DEFAULT NULL,
  `alias` VARCHAR(10) NULL DEFAULT NULL,
  `bsi_email` VARCHAR(100) NULL DEFAULT NULL,
  `bsi_address` VARCHAR(50) NULL DEFAULT NULL,
  `bsi_number` VARCHAR(50) NULL DEFAULT NULL,
  `bsi_alt_number` VARCHAR(50) NULL DEFAULT NULL,
  `bsi_registration_date` DATE NULL DEFAULT NULL,
  `hero_image` MEDIUMBLOB NULL DEFAULT NULL,
  `bsi_workers` INT NULL DEFAULT NULL,
  `bsi_description` VARCHAR(255) NULL DEFAULT NULL,
  `mng_name` VARCHAR(100) NULL DEFAULT NULL,
  `mng_number` VARCHAR(50) NULL DEFAULT NULL,
  `mng_email` VARCHAR(100) NULL DEFAULT NULL,
  `updated_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`checkin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`checkin` (
  `checkin_id` INT NOT NULL AUTO_INCREMENT,
  `room_id` INT NULL DEFAULT NULL,
  `duration_id` INT NULL DEFAULT NULL,
  `checkin_time` TIME NULL DEFAULT NULL,
  `due_time` TIME NULL DEFAULT NULL,
  `check_in_status` TINYINT(1) NULL DEFAULT '0' COMMENT 'if value = 1 then client is booked or checked in \\nif value = 0 then client is checkout. ',
  `checkin_comment` TEXT NULL DEFAULT NULL,
  `booked_by` INT NULL DEFAULT NULL,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`checkin_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`checkout`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`checkout` (
  `checkout_id` INT NOT NULL AUTO_INCREMENT,
  `checkin_id` INT NULL DEFAULT NULL,
  `guestName` VARCHAR(100) NULL DEFAULT NULL,
  `roomNo` VARCHAR(100) NULL DEFAULT NULL,
  `checkout_time` TIME NULL DEFAULT NULL,
  `overtime` VARCHAR(50) NULL DEFAULT NULL,
  `checkedout_by` INT NULL DEFAULT NULL,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`checkout_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`designation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`designation` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `status` TINYINT NULL DEFAULT 1,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`employees`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`employees` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `firstname` VARCHAR(50) NULL DEFAULT NULL,
  `lastname` VARCHAR(50) NULL DEFAULT NULL,
  `gender` VARCHAR(50) NULL DEFAULT NULL,
  `email` VARCHAR(50) NULL DEFAULT NULL,
  `phone` VARCHAR(50) NULL DEFAULT NULL,
  `digital_address` VARCHAR(50) NULL DEFAULT NULL,
  `id_type` VARCHAR(50) NULL DEFAULT NULL,
  `id_number` VARCHAR(50) NULL DEFAULT NULL,
  `employment_date` DATE NULL DEFAULT NULL,
  `designation` VARCHAR(50) NULL DEFAULT NULL,
  `photo` BLOB NULL DEFAULT NULL,
  `salary` DECIMAL(10,2) NULL DEFAULT NULL,
  `status` TINYINT NULL DEFAULT '1',
  `added_by` INT NULL DEFAULT NULL,
  `date_added` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`guests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`guests` (
  `guest_id` INT NOT NULL AUTO_INCREMENT,
  `checkin_id` INT NULL DEFAULT NULL,
  `guest_name` VARCHAR(255) NULL DEFAULT NULL,
  `guest_number` VARCHAR(100) NULL DEFAULT NULL,
  `guest_id_type` VARCHAR(50) NULL DEFAULT NULL,
  `guest_id_number` VARCHAR(50) NULL DEFAULT NULL,
  `date_added` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `added_by` INT NULL DEFAULT NULL,
  PRIMARY KEY (`guest_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`id_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`id_types` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `status` TINYINT NULL DEFAULT 1,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`internal_stock_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`internal_stock_items` (
  `itemId` INT NOT NULL AUTO_INCREMENT,
  `internal_item_name` VARCHAR(255) NULL DEFAULT NULL,
  `internal_item_category` VARCHAR(100) NULL DEFAULT NULL,
  `remaining_quantity` INT NOT NULL DEFAULT '0',
  `current_quantity` INT NOT NULL DEFAULT '0',
  `previous_quantity` INT NOT NULL DEFAULT '0',
  `total_cost_price` DECIMAL(10,2) NULL DEFAULT '0.00',
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `added_by` INT NULL DEFAULT '1',
  `isDeleted` TINYINT(1) NULL DEFAULT '0',
  `date_modified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`itemId`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`internal_stock_request`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`internal_stock_request` (
  `request_id` INT NOT NULL AUTO_INCREMENT,
  `stock_id` INT NULL DEFAULT NULL,
  `requested_quantity` INT NOT NULL,
  `request_status` TINYINT(1) NULL DEFAULT '0',
  `requested_date` VARCHAR(50) NULL DEFAULT NULL,
  `requested_by` INT NULL DEFAULT NULL,
  `approved_date` VARCHAR(50) NULL DEFAULT NULL,
  `approved_by` INT NULL DEFAULT NULL,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`request_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`messagetemplates`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`messagetemplates` (
  `templateId` INT NOT NULL AUTO_INCREMENT,
  `templateTitle` VARCHAR(100) NOT NULL,
  `templateBody` TEXT NOT NULL,
  `createdBy` TINYINT NULL DEFAULT '1',
  `dateCreated` DATETIME NULL DEFAULT NULL,
  `dateModified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`templateId`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`modules`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`modules` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `status` VARCHAR(50) NULL DEFAULT NULL,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`payment_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`payment_transaction` (
  `payment_Id` INT NOT NULL AUTO_INCREMENT,
  `checkin_id` INT NULL DEFAULT NULL,
  `payment_method` VARCHAR(50) NULL DEFAULT NULL,
  `cash_amount` DECIMAL(10,2) NULL DEFAULT NULL,
  `momo_amount` DECIMAL(10,2) NULL DEFAULT NULL,
  `transaction_ID` MEDIUMTEXT NULL DEFAULT NULL,
  `total_bill` DECIMAL(10,2) NULL DEFAULT NULL,
  `client_change` DECIMAL(10,2) NULL DEFAULT NULL,
  `added_by` INT NULL DEFAULT NULL,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`productbrand`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`productbrand` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `BrandName` VARCHAR(100) NULL DEFAULT NULL,
  `DateCreated` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`productitems`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`productitems` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `ProductName` VARCHAR(100) NOT NULL,
  `supplyType` VARCHAR(50) NULL DEFAULT '1',
  `categoryId` INT NULL DEFAULT '1',
  `supplierId` INT NULL DEFAULT 1,
  `brandID` INT NULL DEFAULT NULL,
  `storeId` INT NULL DEFAULT '1',
  `ExpiryDate` DATE NULL DEFAULT NULL,
  `note` TEXT NULL DEFAULT NULL,
  `activeStatus` TINYINT(1) NULL DEFAULT '1',
  `deleteStatus` TINYINT NULL DEFAULT '0',
  `addedBy` INT NULL DEFAULT NULL,
  `dateCreated` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `ProductName_UNIQUE` (`ProductName` ASC) VISIBLE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`productprices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`productprices` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `productId` BIGINT NULL DEFAULT NULL,
  `purchasePrice` DECIMAL(5,2) NULL DEFAULT '0.00',
  `sellingPrice` DECIMAL(5,2) NULL DEFAULT '0.00',
  `profitPerItem` DECIMAL(5,2) NULL DEFAULT '0.00',
  `previousPurchasePrice` DECIMAL(5,2) NULL DEFAULT '0.00',
  `previousSellingPrice` DECIMAL(5,2) NULL DEFAULT '0.00',
  `previousProfit` DECIMAL(5,2) NULL DEFAULT '0.00',
  `modifiedBy` INT NULL DEFAULT NULL,
  `dateModified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `status` TINYINT NULL DEFAULT 1,
  `is_default` TINYINT NULL DEFAULT '0',
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 31
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`roomprices`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`roomprices` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NULL DEFAULT NULL,
  `status` TINYINT NULL DEFAULT 1,
  `price` DECIMAL(10,2) NULL DEFAULT 0.00,
  `allotedTime` INT NULL DEFAULT NULL,
  `date_created` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`rooms`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`rooms` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `roomNo` VARCHAR(50) NULL DEFAULT NULL,
  `CategoryName` VARCHAR(50) NULL DEFAULT NULL,
  `status` TINYINT NULL DEFAULT 1,
  `isBooked` TINYINT NULL DEFAULT '0',
  `standardPrice` DECIMAL(10,2) NULL DEFAULT '0.00',
  `dateAdded` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`sales_payments`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`sales_payments` (
  `payment_id` INT NOT NULL AUTO_INCREMENT,
  `sales_trans_id` VARCHAR(50) NOT NULL,
  `payment_method` VARCHAR(50) NOT NULL,
  `momo_trans_id` VARCHAR(50) NULL DEFAULT '0',
  `total_bill` DECIMAL(10,2) NOT NULL,
  `sales_change` DECIMAL(5,2) NULL DEFAULT NULL,
  `sales_note` VARCHAR(255) NULL DEFAULT NULL,
  `payment_added_by` INT NOT NULL,
  `payment_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`payment_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`sales_transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`sales_transaction` (
  `sales_id` BIGINT NOT NULL AUTO_INCREMENT,
  `item_id` INT NOT NULL,
  `item_quantity` INT NOT NULL,
  `item_cost` DECIMAL(5,2) NOT NULL,
  `sales_trans_id` VARCHAR(50) NULL DEFAULT NULL,
  `payment_id` INT NOT NULL,
  `sales_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`sales_id`),
  INDEX `payment_id` (`payment_id` ASC) VISIBLE,
  CONSTRAINT `sales_transaction_ibfk_1`
    FOREIGN KEY (`payment_id`)
    REFERENCES `inn_register`.`sales_payments` (`payment_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`sentmessages`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`sentmessages` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `mobileNumber` VARCHAR(20) NULL DEFAULT NULL,
  `messageTitle` VARCHAR(255) NULL DEFAULT NULL,
  `messageBody` TEXT NULL DEFAULT NULL,
  `messageStatus` TINYTEXT NULL DEFAULT NULL,
  `balance` INT NULL DEFAULT NULL,
  `sentDate` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `sendBy` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 42
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`sms_api_key`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`sms_api_key` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `api_key` VARCHAR(255) NOT NULL,
  `sender_name` VARCHAR(20) NOT NULL,
  `date_added` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`stocklevels`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`stocklevels` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `productId` BIGINT NULL DEFAULT NULL,
  `stockLevel` INT NULL DEFAULT '0',
  `currentStockLevel` INT NULL DEFAULT '0',
  `currentBoxQuantity` INT NULL DEFAULT '0',
  `currentQuantityPerBox` INT NULL DEFAULT '0',
  `oldStockLevel` INT NOT NULL DEFAULT '0',
  `previousStockLevel` INT NULL DEFAULT '0',
  `previousBoxQuantity` INT NULL DEFAULT '0',
  `previousQuantityPerBox` INT NULL DEFAULT '0',
  `gage` INT NULL DEFAULT '10',
  `modifiedBy` INT NULL DEFAULT NULL,
  `lastModified` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`stockscategory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`stockscategory` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `CategoryName` VARCHAR(50) NULL DEFAULT NULL,
  `DateCreated` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`stores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`stores` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `StoreName` VARCHAR(100) NULL DEFAULT NULL,
  `StoreDescription` VARCHAR(100) NULL DEFAULT 'NOT SPECIFIED',
  `DateCreated` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 10
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`suppliers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`suppliers` (
  `Id` INT NOT NULL AUTO_INCREMENT,
  `status` TINYINT NULL DEFAULT '1',
  `supplierName` VARCHAR(100) NOT NULL DEFAULT 'Default',
  `Contact` VARCHAR(50) NOT NULL,
  `Location` VARCHAR(100) NOT NULL,
  `DateCreated` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`Id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `inn_register`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `inn_register`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(50) NULL DEFAULT NULL,
  `password` VARCHAR(50) NULL DEFAULT NULL,
  `confirm_password` VARCHAR(50) NULL DEFAULT NULL,
  `emp_id` INT NULL DEFAULT NULL,
  `role_id` INT NULL DEFAULT NULL,
  `is_default` TINYINT NULL DEFAULT '0',
  `status` TINYINT NULL DEFAULT 1,
  `added_by` TINYINT NULL DEFAULT NULL,
  `date_added` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
