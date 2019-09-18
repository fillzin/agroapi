ALTER TABLE `agrostok`.`notifications` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;


ALTER TABLE `agrostok`.`person` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;


ALTER TABLE `agrostok`.`product` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;


ALTER TABLE `agrostok`.`sale` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;

ALTER TABLE `agrostok`.`stock` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;

ALTER TABLE `agrostok`.`stock_hist` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;



ALTER TABLE `agrostok`.`user` 
CHANGE COLUMN `data_inclusao` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `data_alteracao` `updated_date` TIMESTAMP NULL DEFAULT NULL ;




