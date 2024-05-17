-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema menuguru
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `menuguru` ;

-- -----------------------------------------------------
-- Schema menuguru
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `menuguru` DEFAULT CHARACTER SET utf8 ;
USE `menuguru` ;

-- -----------------------------------------------------
-- Table `menuguru`.`CustomerStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`CustomerStatus` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`CustomerStatus` (
  `idCustomerStatus` INT NOT NULL,
  `Status` VARCHAR(45) NULL,
  PRIMARY KEY (`idCustomerStatus`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menuguru`.`Customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`Customer` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`Customer` (
  `idCustomer` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `documentNumber` VARCHAR(11) NULL,
  `eMail` VARCHAR(100) NULL,
  `idCustomerStatus` INT NOT NULL,
  PRIMARY KEY (`idCustomer`),
  INDEX `fk_Customer_CustomerStatus1_idx` (`idCustomerStatus` ASC) VISIBLE,
  CONSTRAINT `fk_Customer_CustomerStatus1`
    FOREIGN KEY (`idCustomerStatus`)
    REFERENCES `menuguru`.`CustomerStatus` (`idCustomerStatus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menuguru`.`OrderStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`OrderStatus` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`OrderStatus` (
  `idOrderStatus` INT NOT NULL,
  `statusName` VARCHAR(45) NULL,
  PRIMARY KEY (`idOrderStatus`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menuguru`.`Order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`Order` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`Order` (
  `idOrder` INT NOT NULL AUTO_INCREMENT,
  `idOrderStatus` INT NOT NULL,
  `idCustomer` INT NULL,
  `totalPrice` DOUBLE NOT NULL,
  `paymentIdentifier` CHAR(36) NULL,
  `readyToPrepare` DATETIME NULL,
  PRIMARY KEY (`idOrder`),
  INDEX `fk_Order_OrderStatus_idx` (`idOrderStatus` ASC) VISIBLE,
  INDEX `fk_Order_Customer1_idx` (`idCustomer` ASC) VISIBLE,
  CONSTRAINT `fk_Order_OrderStatus`
    FOREIGN KEY (`idOrderStatus`)
    REFERENCES `menuguru`.`OrderStatus` (`idOrderStatus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Order_Customer1`
    FOREIGN KEY (`idCustomer`)
    REFERENCES `menuguru`.`Customer` (`idCustomer`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menuguru`.`ProductType`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`ProductType` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`ProductType` (
  `idProductType` INT NOT NULL,
  `productTypeName` VARCHAR(45) NULL,
  PRIMARY KEY (`idProductType`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menuguru`.`ProductStatus`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`ProductStatus` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`ProductStatus` (
  `idProductStatus` INT NOT NULL,
  `Status` VARCHAR(45) NULL,
  PRIMARY KEY (`idProductStatus`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menuguru`.`Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`Product` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`Product` (
  `idProduct` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `idProductType` INT NOT NULL,
  `description` VARCHAR(250) NULL,
  `price` DOUBLE NULL,
  `image` BLOB NULL,
  `idProductStatus` INT NOT NULL,
  PRIMARY KEY (`idProduct`),
  INDEX `fk_Product_ProductType1_idx` (`idProductType` ASC) VISIBLE,
  INDEX `fk_Product_ProductStatus1_idx` (`idProductStatus` ASC) VISIBLE,
  CONSTRAINT `fk_Product_ProductType1`
    FOREIGN KEY (`idProductType`)
    REFERENCES `menuguru`.`ProductType` (`idProductType`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Product_ProductStatus1`
    FOREIGN KEY (`idProductStatus`)
    REFERENCES `menuguru`.`ProductStatus` (`idProductStatus`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `menuguru`.`OrderItem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `menuguru`.`OrderItem` ;

CREATE TABLE IF NOT EXISTS `menuguru`.`OrderItem` (
  `idOrderItem` INT NOT NULL AUTO_INCREMENT,
  `idOrder` INT NOT NULL,
  `idProduct` INT NOT NULL,
  `actualPrice` DOUBLE NULL,
  `quantity` INT NULL,
  PRIMARY KEY (`idOrderItem`, `idOrder`, `idProduct`),
  INDEX `fk_OrderItem_Product1_idx` (`idProduct` ASC) VISIBLE,
  INDEX `fk_OrderItem_Order1_idx` (`idOrder` ASC) VISIBLE,
  CONSTRAINT `fk_OrderProduct_Product1`
    FOREIGN KEY (`idProduct`)
    REFERENCES `menuguru`.`Product` (`idProduct`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrderProduct_Order1`
    FOREIGN KEY (`idOrder`)
    REFERENCES `menuguru`.`Order` (`idOrder`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
-- begin attached script 'script'
-- Generate default product types
INSERT INTO `menuguru`.`ProductType` (`idProductType`, `productTypeName`) VALUES (1, 'Lanche');
INSERT INTO `menuguru`.`ProductType` (`idProductType`, `productTypeName`) VALUES (2, 'Acompanhamento');
INSERT INTO `menuguru`.`ProductType` (`idProductType`, `productTypeName`) VALUES (3, 'Bebida');
INSERT INTO `menuguru`.`ProductType` (`idProductType`, `productTypeName`) VALUES (4, 'Sobremesa');

-- Generate default order status
INSERT INTO `menuguru`.`OrderStatus` (`idOrderStatus`, `statusName`) VALUES (1, 'Pendente Pagamento');
INSERT INTO `menuguru`.`OrderStatus` (`idOrderStatus`, `statusName`) VALUES (2, 'Recebido');
INSERT INTO `menuguru`.`OrderStatus` (`idOrderStatus`, `statusName`) VALUES (3, 'Em preparacao');
INSERT INTO `menuguru`.`OrderStatus` (`idOrderStatus`, `statusName`) VALUES (4, 'Pronto');
INSERT INTO `menuguru`.`OrderStatus` (`idOrderStatus`, `statusName`) VALUES (5, 'Finalizado');
INSERT INTO `menuguru`.`OrderStatus` (`idOrderStatus`, `statusName`) VALUES (6, 'Cancelado');

-- Generate default product status
INSERT INTO `menuguru`.`ProductStatus` (`idProductStatus`, `Status`) VALUES (1, 'Ativo');
INSERT INTO `menuguru`.`ProductStatus` (`idProductStatus`, `Status`) VALUES (2, 'Inativo');

-- Generate default customer status
INSERT INTO `menuguru`.`CustomerStatus` (`idCustomerStatus`, `Status`) VALUES (1, 'Ativo');
INSERT INTO `menuguru`.`CustomerStatus` (`idCustomerStatus`, `Status`) VALUES (2, 'Inativo');
-- end attached script 'script'
