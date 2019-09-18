ALTER TABLE `agrostok`.`product` CHANGE COLUMN `value` `value` DECIMAL(18,2) NOT NULL ;
ALTER TABLE `agrostok`.`sale` CHANGE COLUMN `value` `value` DECIMAL(18,2) NOT NULL ;
ALTER TABLE `agrostok`.`sale_product` CHANGE COLUMN `total` `total` DECIMAL(18,2) NOT NULL ;