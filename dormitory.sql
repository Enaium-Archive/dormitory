-- MariaDB dump 10.19  Distrib 10.9.4-MariaDB, for Linux (x86_64)
--
-- Host: localhost    Database: dormitory
-- ------------------------------------------------------
-- Server version	10.9.4-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `absent`
--

DROP TABLE IF EXISTS `absent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `absent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `building_id` int(11) NOT NULL,
  `dormitory_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `dormitory_admin_id` int(11) NOT NULL,
  `create_date` datetime NOT NULL,
  `reason` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `absent`
--

LOCK TABLES `absent` WRITE;
/*!40000 ALTER TABLE `absent` DISABLE KEYS */;
INSERT INTO `absent` VALUES
(13,1,2,5,2,'2022-04-16 00:00:00','请假'),
(14,1,1,1,1,'2022-04-26 00:00:00','请假'),
(15,2,5,63,1,'2022-04-26 00:00:00','请假'),
(16,1,2,5,2,'2022-04-16 00:00:00','请假'),
(17,1,1,1,1,'2022-04-26 00:00:00','请假'),
(18,2,5,63,1,'2022-04-26 00:00:00','请假'),
(19,1,2,5,2,'2022-04-16 00:00:00','请假'),
(20,1,1,1,1,'2022-04-26 00:00:00','请假'),
(21,2,5,63,1,'2022-04-26 00:00:00','请假'),
(22,1,2,5,2,'2022-04-16 00:00:00','请假'),
(23,1,1,1,1,'2022-04-26 00:00:00','请假'),
(24,2,5,63,1,'2022-04-26 00:00:00','请假'),
(25,1,2,5,2,'2022-04-16 00:00:00','请假'),
(26,1,1,1,1,'2022-04-26 00:00:00','请假'),
(27,2,5,63,1,'2022-04-26 00:00:00','请假'),
(28,1,2,5,2,'2022-04-16 00:00:00','请假'),
(29,1,1,1,1,'2022-04-26 00:00:00','请假'),
(30,2,5,63,1,'2022-04-26 00:00:00','请假'),
(31,1,2,5,2,'2022-04-16 00:00:00','请假'),
(32,1,1,1,1,'2022-04-26 00:00:00','请假'),
(33,2,5,63,1,'2022-04-26 00:00:00','请假'),
(34,1,2,5,2,'2022-04-16 00:00:00','请假'),
(35,1,1,1,1,'2022-04-26 00:00:00','请假'),
(36,2,5,63,1,'2022-04-26 00:00:00','请假'),
(37,1,2,5,2,'2022-04-16 00:00:00','请假'),
(38,1,1,1,1,'2022-04-26 00:00:00','请假'),
(39,2,5,63,1,'2022-04-26 00:00:00','请假'),
(40,1,1,1,4,'2023-01-18 11:17:17','123');
/*!40000 ALTER TABLE `absent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(18) NOT NULL,
  `password` varchar(18) NOT NULL,
  `name` varchar(8) NOT NULL,
  `gender` tinyint(1) NOT NULL,
  `phone` bigint(11) NOT NULL,
  `role` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES
(1,'ll','123123','宋玉',0,13312345678,'admin'),
(2,'ww','123123','王力',1,13612345678,'admin'),
(4,'admin1','123123','管理员1',1,88132001,'system'),
(5,'admin2','123123','管理员2',1,88132002,'system'),
(10,'123','12414124124','123',0,12312312311,'admin');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `building` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `introduction` varchar(1000) DEFAULT NULL,
  `admin_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES
(1,'1号楼','计算机学院宿舍楼',2),
(16,'5号楼','电信学院宿舍楼',2);
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dormitory`
--

DROP TABLE IF EXISTS `dormitory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dormitory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `building_id` int(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `available` int(11) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dormitory`
--

LOCK TABLES `dormitory` WRITE;
/*!40000 ALTER TABLE `dormitory` DISABLE KEYS */;
INSERT INTO `dormitory` VALUES
(1,1,'101',4,0,'88230001'),
(2,1,'102',4,0,'88230002'),
(3,1,'211',4,0,'88230007'),
(4,16,'212',6,6,'88230008'),
(5,16,'321',8,8,'88230013'),
(6,16,'322',10,10,'88230016'),
(36,1,'666',6,6,'88136666');
/*!40000 ALTER TABLE `dormitory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `moveout`
--

DROP TABLE IF EXISTS `moveout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `moveout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `dormitory_id` int(11) DEFAULT NULL,
  `reason` varchar(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `moveout`
--

LOCK TABLES `moveout` WRITE;
/*!40000 ALTER TABLE `moveout` DISABLE KEYS */;
INSERT INTO `moveout` VALUES
(15,63,6,'毕业','2022-04-17 00:00:00'),
(17,18,4,'毕业','2022-04-27 00:00:00'),
(18,11,3,'毕业','2023-01-15 10:14:18'),
(19,1,1,'毕业','2023-01-15 10:15:50'),
(20,13,4,'毕业','2023-01-15 10:18:24'),
(21,1,1,'毕业','2023-01-18 11:19:46');
/*!40000 ALTER TABLE `moveout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(11) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `gender` tinyint(1) DEFAULT NULL,
  `dormitory_id` int(11) DEFAULT NULL,
  `state` varchar(20) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES
(1,'001','王伟',0,1,'迁出','2022-04-14 00:00:00'),
(2,'002','曹海',1,1,'入住','2022-04-14 00:00:00'),
(3,'003','李力',1,1,'入住','2022-04-14 00:00:00'),
(4,'004','赵昭',1,1,'入住','2022-04-14 00:00:00'),
(5,'005','王维利',1,2,'入住','2022-04-14 00:00:00'),
(6,'006','李双',0,2,'入住','2022-04-14 00:00:00'),
(7,'007','李小峰',1,2,'入住','2022-04-14 00:00:00'),
(8,'008','孙奇',1,2,'入住','2022-04-14 00:00:00'),
(9,'009','李立',0,3,'入住','2022-04-14 00:00:00'),
(10,'010','周华发',1,3,'入住','2022-04-14 00:00:00'),
(11,'011','赵正义',1,3,'迁出','2022-04-14 00:00:00'),
(12,'012','李明',1,3,'入住','2022-04-14 00:00:00'),
(13,'013','许鹏飞',1,4,'迁出','2022-04-14 00:00:00'),
(14,'014','朱海',1,4,'入住','2022-04-14 00:00:00'),
(15,'015','苏苏苏',1,4,'入住','2022-04-14 00:00:00'),
(16,'016','李雪',0,4,'入住','2022-04-14 00:00:00'),
(17,'017','李爽',0,4,'入住','2022-04-14 00:00:00'),
(18,'018','王纯',0,4,'迁出','2022-04-14 00:00:00'),
(63,'019','小明',1,5,'迁出','2022-04-17 00:00:00');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-18 11:20:50
