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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `accessory_name` varchar(250) NOT NULL,
  `accessory_query` varchar(250) NOT NULL COMMENT '辅料要求',
  PRIMARY KEY (`id`),
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
-- Table structure for table `accessory_cost`
--

DROP TABLE IF EXISTS `accessory_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `accessory_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `accessory_name` varchar(250) NOT NULL COMMENT '辅料名',
  `tear_per_piece` float NOT NULL COMMENT '单件损耗',
  `price` float NOT NULL COMMENT '单价',
  `cost_per_piece` float NOT NULL COMMENT '单件成本',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accessory_cost`
--

LOCK TABLES `accessory_cost` WRITE;
/*!40000 ALTER TABLE `accessory_cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `accessory_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_type` varchar(250) NOT NULL DEFAULT '0' COMMENT '用户类型，EMPLOYEE, CUSTOMER, ADMIN',
  `user_password` varchar(250) NOT NULL COMMENT '用户密码，加密后存储。',
  `user_name` varchar(250) NOT NULL COMMENT '用户登录账号名。',
  `user_role` varchar(250) NOT NULL COMMENT '用户角色，如果user_type是CUMTOMER或ADMIN，则user_role是CUSTOMER或ADMIN. 否则，代表在公司的角色。这个角色涉及权限。包括 SHICHANGZHUANYUAN,  SHICHANGZHUGUAN, CAIGOUZHUGUAN, SHEJIZHUGUAN, CAIWUZHUGUAN, SHENGCHANZHUGUAN, ZHIJIANZHUGUAN, WULIUZHUGUAN',
  `nick_name` varchar(250) NOT NULL COMMENT '用户姓名，冗余字段。对应customer_name和employee_name',
  `big_avatar` varchar(250) DEFAULT NULL COMMENT '大头像',
  `small_avatar` varchar(250) DEFAULT NULL COMMENT '小头像',
  `samll_avatar` varchar(250) DEFAULT NULL,
  `password_cookie_value` varchar(250) DEFAULT NULL,
  `password_cookie_time` datetime DEFAULT NULL,
  `user_cookie_time` datetime DEFAULT NULL,
  `user_cookie_value` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
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
-- Table structure for table `description`
--

DROP TABLE IF EXISTS `description`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `description` (
  `order_id` int(11) NOT NULL,
  `sample_clothes_customer_description` text COMMENT '样衣客户要求',
  `sample_clothes_design_description` text COMMENT '样衣设计要求',
  `product_clothes_customer_description` text COMMENT '生产单客户要求',
  `product_clothes_design_description` text COMMENT '生产单设计要求',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `description`
--

LOCK TABLES `description` WRITE;
/*!40000 ALTER TABLE `description` DISABLE KEYS */;
/*!40000 ALTER TABLE `description` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `design_cad`
--

DROP TABLE IF EXISTS `design_cad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `design_cad` (
  `cad_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `cad_url` varchar(250) NOT NULL,
  `cad_version` tinyint(4) NOT NULL,
  `upload_time` datetime NOT NULL,
  PRIMARY KEY (`cad_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `design_cad`
--

LOCK TABLES `design_cad` WRITE;
/*!40000 ALTER TABLE `design_cad` DISABLE KEYS */;
/*!40000 ALTER TABLE `design_cad` ENABLE KEYS */;
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `fabric_name` varchar(250) NOT NULL,
  `fabric_amount` varchar(250) NOT NULL COMMENT '面料克重。为了灵活，使用varchar。可以通过23g或 2kg指定不同的克重。',
  PRIMARY KEY (`id`),
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
-- Table structure for table `fabric_cost`
--

DROP TABLE IF EXISTS `fabric_cost`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fabric_cost` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `fabric_name` varchar(250) NOT NULL,
  `tear_per_meter` float NOT NULL,
  `price` float NOT NULL,
  `cost_per_meter` float NOT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabric_cost`
--

LOCK TABLES `fabric_cost` WRITE;
/*!40000 ALTER TABLE `fabric_cost` DISABLE KEYS */;
/*!40000 ALTER TABLE `fabric_cost` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `logistics`
--

DROP TABLE IF EXISTS `logistics`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `logistics` (
  `order_id` int(11) NOT NULL,
  `in_post_sample_clothes_time` datetime DEFAULT NULL COMMENT '客户样衣邮寄时间',
  `in_post_sample_clothes_type` varchar(250) DEFAULT NULL COMMENT '客户样衣邮寄使用的快递类型',
  `in_post_sample_clothes_number` varchar(250) DEFAULT NULL COMMENT '客户样衣邮寄的快递单号',
  `sample_clothes_type` varchar(250) DEFAULT NULL COMMENT '样衣邮寄类型',
  `sample_clothes_address` varchar(250) DEFAULT NULL COMMENT '样衣邮寄地址',
  `sample_clothes_name` varchar(250) DEFAULT NULL COMMENT '样衣邮寄人',
  `sample_clothes_phone` varchar(250) DEFAULT NULL COMMENT '样衣邮寄手机',
  `sample_clothes_time` datetime DEFAULT NULL COMMENT '样衣邮寄时间',
  `sample_clothes_remark` varchar(500) DEFAULT NULL COMMENT '样衣邮寄备注',
  `product_clothes_type` varchar(250) DEFAULT NULL COMMENT '快递类型',
  `product_clothes_address` varchar(250) DEFAULT NULL COMMENT '快递地址',
  `product_clothes_price` varchar(250) DEFAULT NULL COMMENT '快递费用',
  `product_clothes_number` varchar(250) DEFAULT NULL COMMENT '快递单号',
  `product_clothes_name` varchar(250) DEFAULT NULL COMMENT '收货人',
  `product_clothes_phone` varchar(250) DEFAULT NULL COMMENT '收货人手机',
  `product_clothes_time` datetime DEFAULT NULL COMMENT '产品邮寄时间',
  `product_clothes_remark` varchar(500) DEFAULT NULL COMMENT '产品邮寄备注',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `logistics`
--

LOCK TABLES `logistics` WRITE;
/*!40000 ALTER TABLE `logistics` DISABLE KEYS */;
/*!40000 ALTER TABLE `logistics` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `money`
--

DROP TABLE IF EXISTS `money`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `money` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `money_type` varchar(250) NOT NULL COMMENT '资金类型，包括 yangyi, dingjin, shengchan, weijin，分别表示样衣金额，30%定金，60%生产金，10%尾金 ',
  `money_amount` double NOT NULL DEFAULT '0',
  `money_state` varchar(45) NOT NULL DEFAULT 'topay' COMMENT '资金状态，包括  daifu, yifu, queren，分别表示等待付款，已经付款和确认付款。客户和市场专员都可以将资金状态改为yifu，从而优先让财务看到。而只有财务才可以把状态改为queren，同时录入确认的相关信息。比如银行，汇款单号，备注',
  `money_name` varchar(45) DEFAULT NULL COMMENT '汇款人，也可以是支付宝账号',
  `money_bank` varchar(250) DEFAULT NULL COMMENT '该笔资金汇款银行，也可以是支付宝等',
  `money_number` varchar(250) DEFAULT NULL COMMENT '汇款号，也可以是支付宝交易号',
  `money_remark` varchar(250) DEFAULT NULL COMMENT '备注。',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `money`
--

LOCK TABLES `money` WRITE;
/*!40000 ALTER TABLE `money` DISABLE KEYS */;
/*!40000 ALTER TABLE `money` ENABLE KEYS */;
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
  `is_need_sample_clothes` tinyint(4) NOT NULL COMMENT '是否需要制作样衣',
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

--
-- Table structure for table `package`
--

DROP TABLE IF EXISTS `package`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package` (
  `package_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `package_time` datetime NOT NULL COMMENT '装包日期',
  `house_name` varchar(250) NOT NULL COMMENT '仓库名称',
  `position` varchar(250) NOT NULL COMMENT '所在仓库里面的哪个位置',
  PRIMARY KEY (`package_id`),
  KEY `order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package`
--

LOCK TABLES `package` WRITE;
/*!40000 ALTER TABLE `package` DISABLE KEYS */;
/*!40000 ALTER TABLE `package` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `package_detail`
--

DROP TABLE IF EXISTS `package_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `package_detail` (
  `package_id` int(11) NOT NULL,
  `clothes_style_name` varchar(250) NOT NULL COMMENT '款式，XS, S, M, L, XL, XLL',
  `clothes_style_color` varchar(250) NOT NULL COMMENT '颜色',
  `clothes_amount` int(11) NOT NULL COMMENT '数量',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `package_id` (`package_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `package_detail`
--

LOCK TABLES `package_detail` WRITE;
/*!40000 ALTER TABLE `package_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `package_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `order_id` int(11) NOT NULL,
  `style` varchar(250) NOT NULL,
  `color` varchar(250) NOT NULL,
  `ask_amount` int(11) NOT NULL DEFAULT '0' COMMENT '客户下单件数',
  `produce_amount` int(11) NOT NULL DEFAULT '0' COMMENT '生产件数',
  `qualified_amount` int(11) NOT NULL DEFAULT '0' COMMENT '合格件数',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quote`
--

DROP TABLE IF EXISTS `quote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `quote` (
  `order_id` int(11) NOT NULL,
  `design_cost` float NOT NULL DEFAULT '0' COMMENT '设计成本',
  `cut_cost` float NOT NULL DEFAULT '0' COMMENT '裁剪成本',
  `manage_cost` float NOT NULL DEFAULT '0' COMMENT '管理成本',
  `swing_cost` float NOT NULL DEFAULT '0' COMMENT '缝制成本',
  `ironing_cost` float NOT NULL DEFAULT '0' COMMENT '整烫成本',
  `nail_cost` float NOT NULL DEFAULT '0' COMMENT '锁钉成本',
  `package_cost` float NOT NULL DEFAULT '0' COMMENT '包装成本',
  `other_cost` float NOT NULL DEFAULT '0' COMMENT '其它成本',
  `profit_per_piece` float NOT NULL DEFAULT '0' COMMENT '单件利润',
  `inner_price` float NOT NULL DEFAULT '0' COMMENT '内部生产报价',
  `outer_price` float NOT NULL DEFAULT '0' COMMENT '给客户的外部报价，有可能会被修改',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quote`
--

LOCK TABLES `quote` WRITE;
/*!40000 ALTER TABLE `quote` DISABLE KEYS */;
/*!40000 ALTER TABLE `quote` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-03-01 17:22:35
