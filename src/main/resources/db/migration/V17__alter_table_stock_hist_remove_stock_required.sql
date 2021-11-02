ALTER TABLE `stock_hist`
DROP FOREIGN KEY `STO_PRO_FK`;
ALTER TABLE `stock_hist`
    CHANGE COLUMN `product_id` `product_id` INT NULL ;
ALTER TABLE `stock_hist`
    ADD CONSTRAINT `STO_PRO_FK`
        FOREIGN KEY (`product_id`)
            REFERENCES `product` (`id`);
