ALTER TABLE `stock`
DROP FOREIGN KEY `EST_PROD_ID`;
ALTER TABLE `stock`
    ADD COLUMN `value` DECIMAL(18,2) NULL AFTER `count`,
CHANGE COLUMN `product_id` `product_id` INT NULL ;
ALTER TABLE `stock`
    ADD CONSTRAINT `EST_PROD_ID`
        FOREIGN KEY (`product_id`)
            REFERENCES `product` (`id`);
