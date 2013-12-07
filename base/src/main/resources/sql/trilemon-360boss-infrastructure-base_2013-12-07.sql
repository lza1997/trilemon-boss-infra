# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.27)
# Database: trilemon-360boss-infrastructure-base
# Generation Time: 2013-12-07 12:55:18 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table app_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app_user`;

CREATE TABLE `app_user` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL DEFAULT '0',
  `app_key` varchar(16) COLLATE utf8_bin NOT NULL DEFAULT '',
  `nick` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `parent_user_id` bigint(11) NOT NULL DEFAULT '0',
  `sub_account` tinyint(1) NOT NULL DEFAULT '0',
  `status` tinyint(11) NOT NULL DEFAULT '0',
  `article_code` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `item_code` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `version` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `deadline` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_sign_in_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_sign_in_ip` varchar(15) COLLATE utf8_bin NOT NULL DEFAULT '',
  `roles` varchar(128) COLLATE utf8_bin NOT NULL DEFAULT '',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



# Dump of table app_user_sign_in_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `app_user_sign_in_log`;

CREATE TABLE `app_user_sign_in_log` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `version` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `scope` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `sign` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `outer_trade_code` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `article_code` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `item_code` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `sign_in_ip` varchar(15) COLLATE utf8_bin NOT NULL DEFAULT '',
  `sign_in_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



# Dump of table buyer_blacklist
# ------------------------------------------------------------

DROP TABLE IF EXISTS `buyer_blacklist`;

CREATE TABLE `buyer_blacklist` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) NOT NULL DEFAULT '0',
  `buyer_nick` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '',
  `type` tinyint(11) NOT NULL DEFAULT '0',
  `status` tinyint(11) NOT NULL DEFAULT '0',
  `filter_count` int(11) NOT NULL DEFAULT '0',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_buyer_blacklist_user_id_and_type_and_filter_count` (`user_id`,`type`,`filter_count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



# Dump of table taobao_api_usage
# ------------------------------------------------------------

DROP TABLE IF EXISTS `taobao_api_usage`;

CREATE TABLE `taobao_api_usage` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `service_name` varchar(16) NOT NULL DEFAULT '',
  `service_id` varchar(16) NOT NULL DEFAULT '0',
  `taobao_app_key` varchar(16) NOT NULL DEFAULT '',
  `api_name` varchar(128) NOT NULL DEFAULT '',
  `successful_call` bigint(11) unsigned NOT NULL DEFAULT '0',
  `failed_call` bigint(11) unsigned NOT NULL DEFAULT '0',
  `avg_exec_time` int(11) unsigned NOT NULL DEFAULT '0',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table taobao_app
# ------------------------------------------------------------

DROP TABLE IF EXISTS `taobao_app`;

CREATE TABLE `taobao_app` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `article_code` varchar(16) NOT NULL DEFAULT '',
  `app_secret` varchar(32) NOT NULL DEFAULT '',
  `app_key` varchar(16) NOT NULL DEFAULT '',
  `sandbox_app_key` varchar(16) NOT NULL DEFAULT '',
  `sandbox_app_secret` varchar(32) NOT NULL DEFAULT '',
  `app_callback_url` varchar(512) NOT NULL DEFAULT '',
  `app_extra_callback_url` varchar(512) NOT NULL DEFAULT '',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_taobao_app_app_key` (`app_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table taobao_seller
# ------------------------------------------------------------

DROP TABLE IF EXISTS `taobao_seller`;

CREATE TABLE `taobao_seller` (
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `uid` varchar(32) NOT NULL DEFAULT '',
  `nick` varchar(64) NOT NULL DEFAULT '',
  `sex` varchar(1) NOT NULL DEFAULT '0',
  `buyer_credit_level` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `buyer_credit_score` int(11) unsigned NOT NULL DEFAULT '0',
  `buyer_credit_total_num` int(11) unsigned NOT NULL DEFAULT '0',
  `buyer_credit_good_num` int(11) unsigned NOT NULL DEFAULT '0',
  `seller_credit_level` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `seller_credit_score` int(11) unsigned NOT NULL DEFAULT '0',
  `seller_credit_total_num` int(11) unsigned NOT NULL DEFAULT '0',
  `seller_credit_good_num` int(11) unsigned NOT NULL DEFAULT '0',
  `location_country` varchar(32) NOT NULL DEFAULT '',
  `location_state` varchar(32) NOT NULL DEFAULT '',
  `location_city` varchar(32) NOT NULL DEFAULT '',
  `location_district` varchar(64) NOT NULL DEFAULT '',
  `location_address` varchar(64) NOT NULL DEFAULT '',
  `location_zip` varchar(16) NOT NULL DEFAULT '',
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `last_visit` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `birthday` date NOT NULL DEFAULT '0000-00-00',
  `type` varchar(1) NOT NULL DEFAULT '0',
  `has_more_pic` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `item_img_num` int(11) unsigned NOT NULL DEFAULT '0',
  `item_img_size` int(11) unsigned NOT NULL DEFAULT '0',
  `prop_img_num` int(11) unsigned NOT NULL DEFAULT '0',
  `prop_img_size` int(11) unsigned NOT NULL DEFAULT '0',
  `auto_repost` varchar(16) NOT NULL DEFAULT '0',
  `promoted_type` varchar(16) NOT NULL DEFAULT '0',
  `status` varchar(16) NOT NULL DEFAULT '0',
  `alipay_bind` varchar(16) NOT NULL DEFAULT '',
  `consumer_protection` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `avatar` varchar(1024) NOT NULL DEFAULT '',
  `liangpin` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `sign_food_seller_promise` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `has_shop` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_lightning_consignment` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `has_sub_stock` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `is_golden_seller` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `vip_info` varchar(16) NOT NULL DEFAULT '0',
  `email` varchar(64) NOT NULL DEFAULT '',
  `magazine_subscribe` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `vertical_market` varchar(256) NOT NULL DEFAULT '',
  `online_gaming` tinyint(1) unsigned NOT NULL DEFAULT '0',
  `alipay_id` varchar(16) NOT NULL DEFAULT '',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table taobao_session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `taobao_session`;

CREATE TABLE `taobao_session` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `app_key` varchar(16) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `access_token` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `token_type` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `taobao_user_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `taobao_user_nick` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `expires_in` int(11) unsigned NOT NULL DEFAULT '0',
  `refresh_token` varchar(64) NOT NULL DEFAULT '',
  `re_expires_in` int(64) unsigned NOT NULL DEFAULT '0',
  `sub_taobao_user_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `sub_taobao_user_nick` varchar(32) NOT NULL DEFAULT '',
  `r1_expires_in` int(11) unsigned NOT NULL DEFAULT '0',
  `w1_expires_in` int(11) unsigned NOT NULL DEFAULT '0',
  `r2_expires_in` int(11) unsigned NOT NULL DEFAULT '0',
  `w2_expires_in` int(11) unsigned NOT NULL DEFAULT '0',
  `state` varchar(1024) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `index_taobao_session_visitor_id_and_app_key` (`taobao_user_id`,`app_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table taobao_shop
# ------------------------------------------------------------

DROP TABLE IF EXISTS `taobao_shop`;

CREATE TABLE `taobao_shop` (
  `user_id` bigint(11) unsigned NOT NULL DEFAULT '0',
  `cid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `nick` varchar(32) NOT NULL DEFAULT '',
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `description` varchar(5096) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `bulletin` varchar(2048) NOT NULL DEFAULT '',
  `pic_path` varchar(1024) NOT NULL DEFAULT '',
  `created` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `modified` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `shop_score_item_score` varchar(11) NOT NULL DEFAULT '0',
  `shop_score_service_score` varchar(11) NOT NULL DEFAULT '0',
  `shop_score_delivery_score` varchar(11) NOT NULL DEFAULT '0',
  `remain_count` bigint(11) unsigned NOT NULL DEFAULT '0',
  `all_count` bigint(11) unsigned NOT NULL DEFAULT '0',
  `used_count` bigint(11) unsigned NOT NULL DEFAULT '0',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `index_taobao_shop_nick` (`nick`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
