CREATE DATABASE  IF NOT EXISTS `nosliw_datacollection`; 
USE `nosliw_datacollection`;

DROP TABLE IF EXISTS `ApplicationHit`;
CREATE TABLE `ApplicationHit` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `app` varchar(255) DEFAULT NULL,
  `parm` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

