ALTER TABLE `sale_product`
DROP FOREIGN KEY `SALE_PROD_EST_FK`;
ALTER TABLE `sale_product`
    CHANGE COLUMN `stock_id` `stock_id` INT NULL ;
ALTER TABLE `sale_product`
    ADD CONSTRAINT `SALE_PROD_EST_FK`
        FOREIGN KEY (`stock_id`)
            REFERENCES `stock` (`id`);
