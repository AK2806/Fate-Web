-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.5.63-MariaDB - mariadb.org binary distribution
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 trpgfate 的数据库结构
CREATE DATABASE IF NOT EXISTS `trpgfate` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `trpgfate`;

-- 导出  表 trpgfate.account 结构
CREATE TABLE IF NOT EXISTS `account` (
  `user_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `avatar` binary(16) NOT NULL,
  `gender` tinyint(3) unsigned NOT NULL DEFAULT '2',
  `birthday` date DEFAULT NULL,
  `residence` varchar(100) DEFAULT NULL,
  `privacy` tinyint(3) unsigned zerofill NOT NULL DEFAULT '000',
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_account_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.announcement 结构
CREATE TABLE IF NOT EXISTS `announcement` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.character 结构
CREATE TABLE IF NOT EXISTS `character` (
  `guid` binary(16) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`guid`),
  KEY `FK_character_user` (`user_id`),
  CONSTRAINT `FK_character_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.follower 结构
CREATE TABLE IF NOT EXISTS `follower` (
  `user_id` int(11) NOT NULL,
  `follower_id` int(11) NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`follower_id`),
  KEY `FK_follower_user_2` (`follower_id`),
  KEY `Search_time` (`user_id`,`time`),
  CONSTRAINT `FK_follower_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_follower_user_2` FOREIGN KEY (`follower_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.game 结构
CREATE TABLE IF NOT EXISTS `game` (
  `guid` binary(16) NOT NULL,
  `user_id` int(11) NOT NULL,
  `mod_guid` binary(16) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` tinyint(3) unsigned zerofill NOT NULL DEFAULT '000',
  `title` varchar(50) NOT NULL,
  PRIMARY KEY (`guid`),
  KEY `FK_game_mod` (`mod_guid`),
  KEY `FK_game_user` (`user_id`),
  CONSTRAINT `FK_game_mod` FOREIGN KEY (`mod_guid`) REFERENCES `mod` (`guid`),
  CONSTRAINT `FK_game_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.game_player 结构
CREATE TABLE IF NOT EXISTS `game_player` (
  `game_guid` binary(16) NOT NULL,
  `user_id` int(11) NOT NULL,
  `character_guid` binary(16) DEFAULT NULL,
  PRIMARY KEY (`game_guid`,`user_id`),
  KEY `FK_game_player_record_user` (`user_id`),
  CONSTRAINT `FK_game_player_record_game` FOREIGN KEY (`game_guid`) REFERENCES `game` (`guid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_game_player_record_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.gaming_record 结构
CREATE TABLE IF NOT EXISTS `gaming_record` (
  `game_guid` binary(16) NOT NULL,
  `instance_guid` binary(16) NOT NULL,
  `begin_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`game_guid`,`instance_guid`),
  KEY `begin_time` (`begin_time`),
  CONSTRAINT `FK_gaming_record_game` FOREIGN KEY (`game_guid`) REFERENCES `game` (`guid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.mod 结构
CREATE TABLE IF NOT EXISTS `mod` (
  `guid` binary(16) NOT NULL,
  `user_id` int(11) NOT NULL,
  `author_id` int(11) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_publish_time` timestamp NULL DEFAULT NULL,
  `title` varchar(50) NOT NULL,
  `description` text NOT NULL,
  PRIMARY KEY (`guid`),
  KEY `FK_mod_user` (`user_id`),
  KEY `FK_mod_user_2` (`author_id`),
  CONSTRAINT `FK_mod_user_2` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_mod_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.notification 结构
CREATE TABLE IF NOT EXISTS `notification` (
  `user_id` int(11) NOT NULL,
  `last_view_follower_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `last_read_announcement_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `FK_notification_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
-- 导出  表 trpgfate.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `passwd_hash` binary(40) NOT NULL,
  `role` tinyint(3) unsigned zerofill NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `active` bit(1) NOT NULL DEFAULT b'1',
  `email` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
