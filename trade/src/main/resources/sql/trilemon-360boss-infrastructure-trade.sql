# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.27)
# Database: trilemon-360boss-infrastructure-trade
# Generation Time: 2013-10-11 09:32:43 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table trade_async
# ------------------------------------------------------------

DROP TABLE IF EXISTS `trade_async`;

CREATE TABLE `trade_async` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(11) unsigned NOT NULL,
  `sync_app_key` varchar(16) NOT NULL DEFAULT '',
  `sync_status` tinyint(3) unsigned NOT NULL,
  `trade_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `trade_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `download_file` varchar(512) NOT NULL DEFAULT '',
  `sync_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sync_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `service_name` varchar(32) NOT NULL DEFAULT '',
  `service_id` varchar(32) NOT NULL DEFAULT '',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;



# Dump of table trade_sync
# ------------------------------------------------------------

DROP TABLE IF EXISTS `trade_sync`;

CREATE TABLE `trade_sync` (
  `id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL DEFAULT '0',
  `sync_app_key` varchar(16) NOT NULL DEFAULT '',
  `sync_trade_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sync_trade_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sync_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sync_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `sync_status` tinyint(4) NOT NULL DEFAULT '4',
  `sync_lock` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `sync_service_name` varchar(32) NOT NULL DEFAULT '',
  `sync_service_id` varchar(32) NOT NULL DEFAULT '',
  `check_trade_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `check_trade_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `check_start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `check_end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `check_status` tinyint(4) NOT NULL DEFAULT '4',
  `check_lock` tinyint(4) unsigned NOT NULL DEFAULT '0',
  `check_service_name` varchar(32) NOT NULL DEFAULT '',
  `check_service_id` varchar(32) NOT NULL DEFAULT '',
  `add_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;




/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
