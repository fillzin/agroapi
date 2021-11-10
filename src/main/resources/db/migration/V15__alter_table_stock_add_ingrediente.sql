ALTER TABLE `stock`
DROP FOREIGN KEY `EST_PROD_ID`;
ALTER TABLE `stock`
    ADD COLUMN `ingrediente_id` INT NULL AFTER `user_updated_id`,
CHANGE COLUMN `product_id` `product_id` INT NULL ,
ADD INDEX `EST_ING_ID_idx` (`ingrediente_id` ASC) VISIBLE;
;
ALTER TABLE `stock`
    ADD CONSTRAINT `EST_PROD_ID`
        FOREIGN KEY (`product_id`)
            REFERENCES `product` (`id`),
ADD CONSTRAINT `EST_ING_ID`
  FOREIGN KEY (`ingrediente_id`)
  REFERENCES `ingrediente` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;


ALTER TABLE `stock`
    ADD COLUMN `value` DECIMAL(18,2) NULL AFTER `count`;

