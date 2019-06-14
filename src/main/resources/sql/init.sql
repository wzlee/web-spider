-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.23 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 spider 的数据库结构
CREATE DATABASE IF NOT EXISTS `spider` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `spider`;

-- 导出  表 spider.t_crawl_data 结构
CREATE TABLE IF NOT EXISTS `t_crawl_data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(512) NOT NULL COMMENT '链接url',
  `title` varchar(512) DEFAULT NULL COMMENT '标题',
  `keyword` varchar(32) DEFAULT NULL COMMENT '关键字',
  `website` varchar(128) NOT NULL COMMENT '来源站点',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `crt_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=580 DEFAULT CHARSET=utf8 COMMENT='爬取数据';

-- 数据导出被取消选择。
-- 导出  表 spider.t_crawl_operation 结构
CREATE TABLE IF NOT EXISTS `t_crawl_operation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `website` varchar(256) NOT NULL COMMENT '站点',
  `last_craw_data_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `website` (`website`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='爬取操作表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
