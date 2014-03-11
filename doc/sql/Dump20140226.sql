CREATE DATABASE  IF NOT EXISTS `fmc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fmc`;
-- MySQL dump 10.13  Distrib 5.6.13, for osx10.6 (i386)
--
-- Host: 192.168.55.52    Database: fmc
-- ------------------------------------------------------
-- Server version	5.6.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `accessory`
--

DROP TABLE IF EXISTS `accessory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accessory` (
  `order_id` int(11) NOT NULL,
  `accessory_name` varchar(250) NOT NULL,
  `accessory_query` varchar(250) NOT NULL COMMENT '辅料要求',
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accessory`
--

LOCK TABLES `accessory` WRITE;
/*!40000 ALTER TABLE `accessory` DISABLE KEYS */;
/*!40000 ALTER TABLE `accessory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `company_id` varchar(250) NOT NULL,
  `company_name` varchar(250) NOT NULL,
  `customer_name` varchar(250) NOT NULL,
  `province` varchar(250) NOT NULL,
  `city` varchar(250) DEFAULT NULL,
  `website_url` varchar(250) DEFAULT NULL,
  `website_type` varchar(250) DEFAULT 'taobao',
  `company_address` varchar(250) DEFAULT NULL,
  `company_fax` varchar(250) DEFAULT NULL,
  `company_phone` varchar(250) DEFAULT NULL,
  `buy_contact` varchar(250) DEFAULT NULL,
  `contact_phone_1` varchar(250) DEFAULT NULL,
  `contact_phone_2` varchar(250) DEFAULT NULL,
  `qq` varchar(250) DEFAULT NULL,
  `email` varchar(250) DEFAULT NULL,
  `customer_phone` varchar(250) DEFAULT NULL,
  `boss_name` varchar(250) DEFAULT NULL,
  `boss_phone` varchar(250) DEFAULT NULL,
  `register_date` datetime NOT NULL,
  `register_employee_id` int(11) NOT NULL,
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `customer_id_UNIQUE` (`customer_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `designcad`
--

DROP TABLE IF EXISTS `designcad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `designcad` (
  `order_id` int(11) NOT NULL,
  `cad_url` varchar(250) NOT NULL,
  `cad_version` tinyint(4) NOT NULL,
  `upload_time` datetime NOT NULL,
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designcad`
--

LOCK TABLES `designcad` WRITE;
/*!40000 ALTER TABLE `designcad` DISABLE KEYS */;
/*!40000 ALTER TABLE `designcad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_name` varchar(250) NOT NULL COMMENT '名字大小写',
  `sex` tinyint(4) NOT NULL,
  `age` tinyint(4) NOT NULL,
  `department` varchar(250) NOT NULL,
  `entry_time` datetime NOT NULL,
  `direct_leader` int(11) NOT NULL,
  `employee_level` varchar(250) NOT NULL,
  `phone_1` varchar(250) DEFAULT NULL,
  `phone_2` varchar(250) DEFAULT NULL,
  `employee_state` varchar(250) NOT NULL DEFAULT 'NORMAL',
  `leave_time` datetime DEFAULT NULL,
  `ex_company` varchar(250) DEFAULT NULL,
  `ex_business` varchar(250) DEFAULT NULL,
  `ex_job` varchar(250) DEFAULT NULL,
  `edu_background` varchar(250) DEFAULT NULL,
  `edu_school` varchar(250) DEFAULT NULL,
  `edu_field` varchar(250) DEFAULT NULL,
  `hometown` varchar(250) DEFAULT NULL,
  `address` varchar(250) DEFAULT NULL,
  `china_id` varchar(250) NOT NULL,
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `employee_id_UNIQUE` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fabric`
--

DROP TABLE IF EXISTS `fabric`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fabric` (
  `order_id` int(11) NOT NULL,
  `fabric_name` varchar(250) NOT NULL,
  `fabric_amount` varchar(250) NOT NULL COMMENT '面料克重。为了灵活，使用varchar。可以通过23g或 2kg指定不同的克重。',
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabric`
--

LOCK TABLES `fabric` WRITE;
/*!40000 ALTER TABLE `fabric` DISABLE KEYS */;
/*!40000 ALTER TABLE `fabric` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `order_state` varchar(250) NOT NULL DEFAULT 'xundan',
  `order_time` datetime NOT NULL,
  `customer_name` varchar(250) NOT NULL,
  `customer_company` varchar(250) DEFAULT NULL,
  `customer_company_fax` varchar(250) DEFAULT NULL,
  `customer_phone_1` varchar(250) DEFAULT NULL,
  `customer_phone_2` varchar(250) DEFAULT NULL,
  `customer_company_address` varchar(250) DEFAULT NULL,
  `style_name` varchar(250) DEFAULT NULL COMMENT '款式名称',
  `fabric_type` varchar(250) DEFAULT NULL COMMENT '面料类型：梭织/针织/编织/梭针混合/针编混合/梭编混合',
  `style_sex` varchar(250) DEFAULT NULL COMMENT '款式性别：男，女，儿童，孕妇',
  `style_season` varchar(250) DEFAULT NULL COMMENT '款式季节：春夏，秋冬',
  `special_process` varchar(250) DEFAULT NULL COMMENT '特殊工艺：水洗，激光，压皱。多选，并且不限于这三个预定的工艺。这采用使用“|”符号分隔。比如"diaopai|jiguang|其它",表明使用了水洗和激光的预定工艺，以及添加的“其它”工艺。',
  `other_requirements` varchar(250) DEFAULT NULL COMMENT '其它要求，有主标，有吊牌，可水洗。多选但不限于这三个。使用“|”符号分隔。比如“zhubiao|shuixi|其它”，表明有主标，可水洗这两个预定要求，以及额外的“其它”要求。',
  `sample_clothes_picture` varchar(250) DEFAULT NULL COMMENT '样衣图片，上传后这里保存url',
  `reference_picture` varchar(250) DEFAULT NULL COMMENT '参考图，上传图片后这里保存url',
  `ask_amount` int(11) DEFAULT NULL,
  `ask_produce_period` varchar(250) DEFAULT NULL COMMENT '生产工期，这里使用varchar为了灵活。比如 2mon可以代表两个月，16day可以代表16天',
  `ask_deliver_date` datetime DEFAULT NULL,
  `ask_code_number` varchar(250) DEFAULT NULL COMMENT '码数',
  `has_posted_sample_clothes` tinyint(4) NOT NULL COMMENT '客户是否邮寄了样衣',
  `in_post_sample_clothes_time` datetime DEFAULT NULL COMMENT '客户样衣邮寄时间',
  `in_post_sample_clothes_type` varchar(250) DEFAULT NULL COMMENT '客户样衣邮寄使用的快递类型',
  `in_post_sample_clothes_number` varchar(250) DEFAULT NULL COMMENT '客户样衣邮寄的快递单号',
  `is_need_sample_clothes` tinyint(4) NOT NULL COMMENT '是否需要制作样衣',
  `out_post_sample_clothes_type` varchar(250) DEFAULT NULL COMMENT '样衣的寄送快递类型',
  `out_post_sample_clothes_number` varchar(250) DEFAULT NULL COMMENT '样衣邮寄的快递单号',
  `out_post_sample_clothes_address` varchar(250) DEFAULT NULL COMMENT '样衣邮寄地址。',
  `simple_clothes_contact_name` varchar(250) DEFAULT NULL COMMENT '样衣邮寄联系人（通常就是customer_id对应的客户的名字）',
  `sample_clothes_contact_phone` varchar(250) DEFAULT NULL COMMENT '样衣邮寄电话',
  `sample_clothes_remark` varchar(250) DEFAULT NULL COMMENT '关于样衣的其它备注。',
  `order_source` varchar(250) DEFAULT NULL COMMENT '订单来源。电话\\拜访\\客户推荐（推荐人）。使用汉语拼音。dianhua、baifang、tuijian(王五)',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-02-26 17:25:10
