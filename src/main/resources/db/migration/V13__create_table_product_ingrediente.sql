CREATE TABLE `product_ingredientes` (
                                       `id` INT NOT NULL AUTO_INCREMENT,
                                       `product_id` INT NOT NULL,
                                       `ingrediente_id` INT NOT NULL,
                                       PRIMARY KEY (`id`),
                                       INDEX `PRODUCT_FK_idx` (`product_id` ASC) VISIBLE,
                                       INDEX `INGREDIENTE_FK_idx` (`ingrediente_id` ASC) VISIBLE,
                                       CONSTRAINT `PRODUCT_FK`
                                           FOREIGN KEY (`product_id`)
                                               REFERENCES `petiscaria`.`product` (`id`)
                                               ON DELETE NO ACTION
                                               ON UPDATE NO ACTION,
                                       CONSTRAINT `INGREDIENTE_FK`
                                           FOREIGN KEY (`ingrediente_id`)
                                               REFERENCES `petiscaria`.`notifications` (`id`)
                                               ON DELETE NO ACTION
                                               ON UPDATE NO ACTION);
