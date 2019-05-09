/*
 Navicat Premium Data Transfer

 Source Server         : 155.34
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 47.244.155.34:3306
 Source Schema         : wallet

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 09/05/2019 19:17:25
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for crypto_address
-- ----------------------------
DROP TABLE IF EXISTS `crypto_address`;
CREATE TABLE `crypto_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `address` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `keystore` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_address` (`address`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for crypto_coin
-- ----------------------------
DROP TABLE IF EXISTS `crypto_coin`;
CREATE TABLE `crypto_coin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `symbol` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `ticker` decimal(36,18) NOT NULL DEFAULT '0.000000000000000000',
  `contract` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `decimals` int(11) NOT NULL DEFAULT '0',
  `confirm` int(11) NOT NULL DEFAULT '0',
  `gas_limit` decimal(20,0) NOT NULL DEFAULT '0',
  `gas_price` decimal(20,0) NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_symbol` (`symbol`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for crypto_transaction
-- ----------------------------
DROP TABLE IF EXISTS `crypto_transaction`;
CREATE TABLE `crypto_transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `coin_id` bigint(20) NOT NULL,
  `amount` decimal(36,18) NOT NULL DEFAULT '0.000000000000000000',
  `from_address` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `to_address` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  `gas_limit` decimal(20,0) NOT NULL,
  `gas_price` decimal(20,0) NOT NULL,
  `transaction_hash` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `confirm` int(11) NOT NULL DEFAULT '0',
  `block_number` int(11) NOT NULL DEFAULT '0',
  `error_message` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `remarks` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`),
  KEY `fk_coin` (`coin_id`),
  CONSTRAINT `fk_coin` FOREIGN KEY (`coin_id`) REFERENCES `crypto_coin` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `email` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `nickname` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `portrait` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `language` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `password_salt` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `login_password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `payment_password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `status` tinyint(4) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_delete` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

SET FOREIGN_KEY_CHECKS = 1;
