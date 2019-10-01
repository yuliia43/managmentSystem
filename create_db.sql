create database trunking;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `is_banned` bit(1) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `surname` varchar(20) DEFAULT NULL,
  `user_account_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa2ixh18irxw16xxl1ka3gfth6` (`user_account_id`)
) 

CREATE TABLE `role` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) 

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `roles_id` bigint(20) NOT NULL,
  KEY `FKj9553ass9uctjrmh0gkqsmv0d` (`roles_id`),
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`)
) 

CREATE TABLE `request` (
  `id` bigint(20) NOT NULL,
  `creator` varchar(255) DEFAULT NULL,
  `price` bigint(20) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `request` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `master_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK22e2ep54nspx0we4lfrbtgv6q` (`master_id`)
) 

CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`)
) 


INSERT INTO `trucking`.`user` (`id`, `email`,  `first_name`,  `password`, `surname`) VALUES ('1', 'z@z.z', 'Masha','$2a$10$eR2BDVBw85T5yf.isbwUhu0GdPvTLdWnWpboAwnwVW1p9S8lA1kU.', 'Mushkina');
INSERT INTO `trucking`.`user` (`id`, `email`,  `first_name`,  `password`, `surname`) VALUES ('2', 'master1@m', 'Dasha','$2a$10$eR2BDVBw85T5yf.isbwUhu0GdPvTLdWnWpboAwnwVW1p9S8lA1kU.', 'Mushkina');
INSERT INTO `trucking`.`user` (`id`, `email`,  `first_name`,  `password`, `surname`) VALUES ('3', 'u@u.u', 'Vasiliy','$2a$10$eR2BDVBw85T5yf.isbwUhu0GdPvTLdWnWpboAwnwVW1p9S8lA1kU.', 'Mushkina');

INSERT INTO `trucking`.`role` (`id`, `name`) VALUES ('1', 'ROLE_MANAGER');
INSERT INTO `trucking`.`role` (`id`, `name`) VALUES ('2', 'ROLE_USER');
INSERT INTO `trucking`.`role` (`id`, `name`) VALUES ('3', 'ROLE_MASTER');

INSERT INTO `trucking`.`user_roles` (`user_id`, `role_id`) VALUES ('1', '1');
INSERT INTO `trucking`.`user_roles` (`user_id`, `role_id`) VALUES ('2', '3');
INSERT INTO `trucking`.`user_roles` (`user_id`, `role_id`) VALUES ('3', '2');
