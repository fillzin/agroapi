ALTER TABLE `product`
    ADD COLUMN `value_sale` DECIMAL(18,2) NULL AFTER `min_stock`,
ADD COLUMN `value_sale2` DECIMAL(18,2) NULL AFTER `value_sale`,
ADD COLUMN `description_value` VARCHAR(45) NULL AFTER `value_sale2`,
ADD COLUMN `description_value_2` VARCHAR(45) NULL AFTER `description_value`;
