ALTER TABLE `sale`
    ADD COLUMN `condominio` VARCHAR(45) NULL AFTER `user_updated_id`,
ADD COLUMN `bloco` VARCHAR(45) NULL AFTER `condominio`,
ADD COLUMN `casa` VARCHAR(45) NULL AFTER `bloco`;
