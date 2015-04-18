CREATE DATABASE  IF NOT EXISTS `fmc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fmc`;
DROP TABLE IF EXISTS `order_source`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_source` (
  `source_id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `source` varchar(45) NOT NULL DEFAULT '自己下单',
  PRIMARY KEY (`source_id`),
  UNIQUE KEY `source_id_UNIQUE` (`source_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;