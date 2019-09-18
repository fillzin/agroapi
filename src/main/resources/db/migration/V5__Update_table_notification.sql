ALTER TABLE `agrostok`.`notifications` 
ADD COLUMN `deleted` INT NOT NULL AFTER `updated_date`;
