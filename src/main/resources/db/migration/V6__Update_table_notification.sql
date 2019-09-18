ALTER TABLE `notifications` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;


ALTER TABLE `person` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;


ALTER TABLE `product` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;


ALTER TABLE `sale` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;

ALTER TABLE `stock` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;

ALTER TABLE `stock_hist` 
CHANGE COLUMN `created_date` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `updated_date` `updated_date` TIMESTAMP NULL DEFAULT NULL ;



ALTER TABLE `user` 
CHANGE COLUMN `data_inclusao` `created_date` TIMESTAMP NOT NULL ,
CHANGE COLUMN `data_alteracao` `updated_date` TIMESTAMP NULL DEFAULT NULL ;




