CREATE TABLE `oauth_approvals` (
  `userId` varchar(256) DEFAULT NULL,
  `clientId` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `status` varchar(10) DEFAULT NULL,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL
);

CREATE TABLE `oauth_client_details` (
  `client_id` varchar(255) NOT NULL,
  `client_secret` varchar(255) NOT NULL,
  `web_server_redirect_uri` varchar(2048) DEFAULT NULL,
  `scope` varchar(255) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `resource_ids` varchar(1024) DEFAULT NULL,
  `authorized_grant_types` varchar(1024) DEFAULT NULL,
  `authorities` varchar(1024) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
);

CREATE TABLE `oauth_code` (
  `code` varchar(256) DEFAULT NULL,
  `authentication` mediumblob
);

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` mediumblob,
  `authentication` mediumblob
);

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
);


CREATE TABLE `permission_role` (
  `permission_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `permission_id` (`permission_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `permission_role_ibfk_1` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `permission_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
);

CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` mediumblob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` mediumblob,
  `refresh_token` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
);

CREATE TABLE `oauth_client_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` mediumblob,
  `authentication_id` varchar(256) NOT NULL,
  `user_name` varchar(256) DEFAULT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`authentication_id`)
);


CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` tinyint(4) NOT NULL,
  `nome` varchar(255) DEFAULT NULL,
  `data_inclusao` datetime NOT NULL,
  `data_alteracao` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
);

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `phone2` varchar(20) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `user_created_id` int(11) NOT NULL,
  `user_updated_id` int(11) DEFAULT NULL,
  `type` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `PER_USER_FK_idx` (`user_created_id`),
  KEY `PER_UP_USER_FK_idx` (`user_updated_id`),
  CONSTRAINT `PER_CRE_USER_FK` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`),
  CONSTRAINT `PER_UP_USER_FK` FOREIGN KEY (`user_updated_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `value` decimal(6,2) NOT NULL,
  `created_count` int(11) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `user_created_id` int(11) NOT NULL,
  `user_updated_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `PRO_CRE_USER_FK_idx` (`user_created_id`),
  KEY `PRO_UPD_USER_FK_idx` (`user_updated_id`),
  CONSTRAINT `PRO_CRE_USER_FK` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`),
  CONSTRAINT `PRO_UPD_USER_FK` FOREIGN KEY (`user_updated_id`) REFERENCES `user` (`id`)
);


CREATE TABLE `role_user` (
  `role_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  KEY `role_id` (`role_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `role_user_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `role_user_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `sale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `value` decimal(6,2) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `user_created_id` int(11) NOT NULL,
  `user_updated_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `SAL_CRE_USER_FK_idx` (`user_created_id`),
  KEY `SAL_UPD_USER_FK_idx` (`user_updated_id`),
  CONSTRAINT `SAL_CRE_USER_FK` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`),
  CONSTRAINT `SAL_UPD_USER_FK` FOREIGN KEY (`user_updated_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `user_created_id` int(11) NOT NULL,
  `user_updated_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_PROD_USER` (`product_id`,`user_created_id`),
  KEY `EST_PROD_ID_idx` (`product_id`),
  KEY `EST_USER_ID_idx` (`user_created_id`),
  KEY `EST_UPD_USER_FK_idx` (`user_updated_id`),
  CONSTRAINT `EST_PROD_ID` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `EST_UPD_USER_FK` FOREIGN KEY (`user_updated_id`) REFERENCES `user` (`id`),
  CONSTRAINT `EST_USER_ID` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `sale_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sale_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `stock_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `total` decimal(6,2) NOT NULL,
  `user_created_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `SALE_PROD_SALE_idx` (`sale_id`),
  KEY `SALE_PROD_PROD_FK_idx` (`product_id`),
  KEY `SALE_PROD_EST_FK_idx` (`stock_id`),
  KEY `SALE_PROD_USER_FK_idx` (`user_created_id`),
  CONSTRAINT `SALE_PROD_EST_FK` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`id`),
  CONSTRAINT `SALE_PROD_PROD_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `SALE_PROD_SALE_FK` FOREIGN KEY (`sale_id`) REFERENCES `sale` (`id`),
  CONSTRAINT `SALE_PROD_USER_FK` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`)
);

CREATE TABLE `stock_hist` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `user_created_id` int(11) NOT NULL,
  `user_updated_id` int(11) DEFAULT NULL,
  `stock_id` int(11) NOT NULL,
  `operation` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `STO_PRO_FK_idx` (`product_id`),
  KEY `STO_CRE_USER_FK_idx` (`user_created_id`),
  KEY `STO_UPD_USER_FK_idx` (`user_updated_id`),
  CONSTRAINT `STO_CRE_USER_FK` FOREIGN KEY (`user_created_id`) REFERENCES `user` (`id`),
  CONSTRAINT `STO_PRO_FK` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `STO_UPD_USER_FK` FOREIGN KEY (`user_updated_id`) REFERENCES `user` (`id`)
);

