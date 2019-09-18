ALTER TABLE `notifications` 
ADD COLUMN `deleted` INT NOT NULL AFTER `updated_date`;
