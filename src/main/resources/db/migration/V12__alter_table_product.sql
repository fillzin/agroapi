ALTER TABLE `product`
    ADD COLUMN `two_price` TINYINT NULL AFTER `description_value_2`,
ADD COLUMN `salgadinho` TINYINT NULL AFTER `two_price`;
