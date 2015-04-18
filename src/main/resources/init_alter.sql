CREATE DATABASE  IF NOT EXISTS `fmc` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `fmc`;
DROP TABLE IF EXISTS `marketstaff_alter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marketstaff_alter` (
  `alter_id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `next_employee_id` int(11) DEFAULT NULL,
  `apply_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `reason` varchar(250) DEFAULT NULL,
  `process_id` varchar(100) DEFAULT NULL,
  `verify_state` varchar(250) DEFAULT NULL,
  `current_order_task` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`alter_id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;