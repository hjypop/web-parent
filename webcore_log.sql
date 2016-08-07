/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.28 : Database - db_matrix
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db_matrix` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `db_matrix`;

/*Table structure for table `lc_open_api_operation` */

DROP TABLE IF EXISTS `lc_open_api_operation`;

CREATE TABLE `lc_open_api_operation` (
  `zid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(45) DEFAULT NULL,
  `seller_code` varchar(25) DEFAULT NULL,
  `api_name` varchar(45) DEFAULT NULL COMMENT '请求Api的名称',
  `class_url` varchar(100) DEFAULT NULL COMMENT 'api方法路径',
  `request_json` longtext COMMENT '请求数据报文',
  `response_json` longtext COMMENT '相应数据报文',
  `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
  `remark` text COMMENT '备注',
  PRIMARY KEY (`zid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `lc_open_api_operation` */

/*Table structure for table `lc_open_api_shipment_status` */

DROP TABLE IF EXISTS `lc_open_api_shipment_status`;

CREATE TABLE `lc_open_api_shipment_status` (
  `zid` int(11) NOT NULL AUTO_INCREMENT,
  `shipment_uid` varchar(64) DEFAULT NULL COMMENT '物流号uuid',
  `seller_code` varchar(25) DEFAULT NULL COMMENT '商家编号',
  `order_code` varchar(25) DEFAULT NULL COMMENT '订单编号',
  `logisticse_name` varchar(25) DEFAULT NULL COMMENT '物流公司 名称',
  `way_bill` varchar(25) DEFAULT NULL COMMENT '运单号码',
  `flag` int(2) DEFAULT '1' COMMENT '物流信息插入是否成功1 成功 2失败',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(512) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`zid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `lc_open_api_shipment_status` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
