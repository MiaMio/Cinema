-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: cinema
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `cinema`
--

DROP TABLE IF EXISTS `cinema`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cinema` (
  `cinema_id` int(11) NOT NULL AUTO_INCREMENT,
  `cinema_name` varchar(20) NOT NULL,
  `location` varchar(100) NOT NULL,
  PRIMARY KEY (`cinema_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinema`
--

LOCK TABLES `cinema` WRITE;
/*!40000 ALTER TABLE `cinema` DISABLE KEYS */;
/*!40000 ALTER TABLE `cinema` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_film` varchar(30) NOT NULL,
  `comment_customer` varchar(20) NOT NULL,
  `comment_content` varchar(1000) DEFAULT NULL,
  `comment_time` varchar(100) NOT NULL,
  `comment_score` int(11) NOT NULL,
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'avatar','peter','good','2017-5-16',5),(2,'avatar','judy','suck','2017-5-16',5),(3,'avatar','alice','so so','2017-5-16',5),(4,'avatar','jack','yes, this is a suck film','2017-05-16',0),(5,'Chun Jiao jiu Zhi Ming','jack','this is a very moving film!','2017-05-21',5),(6,'Chun Jiao jiu Zhi Ming','jack','this is a very moving film!','2017-05-21',5),(7,'Chun Jiao jiu Zhi Ming','jack','This is a very moving film','2017-05-21',5),(8,'Chun Jiao jiu Zhi Ming','alice1','This is very bad','2017-05-23',1),(9,'Chun Jiao jiu Zhi Ming','cabbage','This is trash','2017-06-01',5);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_account` varchar(20) NOT NULL,
  `customer_pwd` varchar(20) NOT NULL,
  `customer_balance` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'jack','jack',1800),(2,'alice','alice123',0),(4,'administrator','administrator',0),(5,'alice1','alice123',0),(6,'cabbage','12345678',101);
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `film`
--

DROP TABLE IF EXISTS `film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `film` (
  `film_id` int(11) NOT NULL AUTO_INCREMENT,
  `film_name` varchar(100) NOT NULL,
  `film_abstract` varchar(1000) NOT NULL,
  PRIMARY KEY (`film_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film`
--

LOCK TABLES `film` WRITE;
/*!40000 ALTER TABLE `film` DISABLE KEYS */;
INSERT INTO `film` VALUES (1,'Chun Jiao jiu Zhi Ming','A couple who have been together for several years try to \r\n decide if they should stay together.'),(2,'Guardians of the Galaxy','Set to the backdrop of Awesome Mixtape #2, \'Guardians of the Galaxy \r\n Vol. 2\' continues the team\'s adventures as they unravel the mystery of \r\n Peter Quill\'s true parentage.'),(11,'Dangal','good');
/*!40000 ALTER TABLE `film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ticket` (
  `ticket_id` int(11) NOT NULL AUTO_INCREMENT,
  `ticket_film` varchar(50) NOT NULL,
  `ticket_value` int(11) NOT NULL,
  `ticket_customer` varchar(20) NOT NULL,
  `ticket_time` varchar(20) NOT NULL,
  `ticket_cinema` varchar(50) NOT NULL,
  PRIMARY KEY (`ticket_id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'avatar',50,'jack','2017-5-15','yonghua'),(2,'avatar',50,'jack','2017-5-15','yonghua'),(3,'Chun Jiao jiu Zhi Ming',50,'jack','05-24 11:45','UA Cinema'),(4,'Chun Jiao jiu Zhi Ming',50,'jack','05-24 11:45','UA Cinema'),(5,'Chun Jiao jiu Zhi Ming',50,'alice1','05-24 13:45','UA Cinema'),(6,'Chun Jiao jiu Zhi Ming',50,'administrator','05-24 11:00','SFC Cinema'),(7,'Chun Jiao jiu Zhi Ming',50,'administrator','05-29 11:00','SFC Cinema'),(8,'Chun Jiao jiu Zhi Ming',50,'administrator','05-29 11:00','SFC Cinema');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `timetable`
--

DROP TABLE IF EXISTS `timetable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `timetable` (
  `time_id` int(11) NOT NULL AUTO_INCREMENT,
  `time_value` varchar(20) NOT NULL,
  `time_film` varchar(30) NOT NULL,
  `seats` int(11) NOT NULL,
  `cinema` varchar(30) NOT NULL,
  `time_film_price` int(11) NOT NULL,
  PRIMARY KEY (`time_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
INSERT INTO `timetable` VALUES (1,'2017-5-15','avatar',49,'yonghua',50),(2,'05-24 11:35','Dangal',50,'SFC Cinema',50),(3,'05-24 14:10','Dangal',50,'SFC Cinema',50),(4,'05-24 16:45','Dangal',50,'SFC Cinema',50),(5,'05-24 19:20','Dangal',50,'SFC Cinema',50),(6,'05-24 22:00','Dangal',50,'SFC Cinema',50),(7,'05-24 11:35','Dangal',50,'UA Cinema',50),(8,'05-24 13:00','Dangal',50,'UA Cinema',50),(9,'05-24 14:10','Dangal',50,'UA Cinema',50),(10,'05-24 15:35','Dangal',50,'UA Cinema',50),(11,'05-24	16:45','Dangal',50,'UA Cinema',50),(14,'05-24 16:55','Guardians of the Galaxy',50,'SFC Cinema',50),(15,'05-24 19:30','Guardians of the Galaxy',50,'SFC Cinema',50),(17,'05-24 16:30','Guardians of the Galaxy',50,'UA Cinema',50),(20,'05-24 11:45','Power Rangers',50,'UA Cinema',50),(21,'05-24 13:45','Power Rangers',50,'UA Cinema',50),(22,'05-24 14:45','Power Rangers',50,'UA Cinema',50),(23,'05-24 15:45','Power Rangers',50,'UA Cinema',50),(24,'05-24 11:00','Power Rangers',50,'SFC Cinema',50),(25,'05-24 13:00','Power Rangers',50,'SFC Cinema',50),(26,'05-24 14:00','Power Rangers',50,'SFC Cinema',50),(27,'05-24 16:00','Power Rangers',50,'SFC Cinema',50),(29,'05-24 13:45','Chun Jiao Jiu Zhi Ming',49,'UA Cinema',50),(32,'05-24 11:00','Chun Jiao Jiu Zhi Ming',49,'SFC Cinema',50),(37,'05-29 11:00','Chun Jiao jiu Zhi Ming',48,'SFC Cinema',50),(39,'06-05 12:00','Chun Jiao jiu Zhi Ming',50,'SFC Cinema',50),(40,'06-10 12:00','Chun Jiao jiu Zhi Ming',50,'SFC Cinema',50),(41,'06-07','Guardians of the Galaxy',50,'SFC Cinema',50),(43,'06-08','Guardians of the Galaxy',2,'UA Cinema',100);
/*!40000 ALTER TABLE `timetable` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-01 21:45:52
