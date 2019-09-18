CREATE TABLE `notifications` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `msg` VARCHAR(80) NOT NULL,
  `readed` TINYINT(1) NOT NULL,
  `user_created_id` INT NOT NULL,
  `user_updated_id` INT NULL,
  `created_date` DATETIME NOT NULL,
  `updated_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `NOT_USER_CRE_FK_idx` (`user_created_id` ASC) ,
  INDEX `NOT_USER_UPD_FK_idx` (`user_updated_id` ASC) ,
  CONSTRAINT `NOT_USER_CRE_FK`
    FOREIGN KEY (`user_created_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `NOT_USER_UPD_FK`
    FOREIGN KEY (`user_updated_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
