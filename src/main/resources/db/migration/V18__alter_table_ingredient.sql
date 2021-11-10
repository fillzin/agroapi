ALTER TABLE `product_ingredientes`
DROP FOREIGN KEY `INGREDIENTE_FK`;
ALTER TABLE `product_ingredientes`
    ADD INDEX `INGREDIENTE_FK_idx` (`ingrediente_id` ASC) VISIBLE,
DROP INDEX `INGREDIENTE_FK_idx` ;
;
ALTER TABLE `product_ingredientes`
    ADD CONSTRAINT `INGREDIENTE_FK`
        FOREIGN KEY (`ingrediente_id`)
            REFERENCES `ingrediente` (`id`);
