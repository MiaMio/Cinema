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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'avatar','peter','good','2017-5-16',5),(2,'avatar','judy','suck','2017-5-16',5),(3,'avatar','alice','so so','2017-5-16',5),(4,'avatar','jack','yes, this is a suck film','2017-05-16',0),(5,'Chun Jiao jiu Zhi Ming','jack','this is a very moving film!','2017-05-21',5),(6,'Chun Jiao jiu Zhi Ming','jack','this is a very moving film!','2017-05-21',5),(7,'Chun Jiao jiu Zhi Ming','jack','This is a very moving film','2017-05-21',5);
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'jack','jack123'),(2,'alice','alice123'),(3,'jack','');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `film`
--

LOCK TABLES `film` WRITE;
/*!40000 ALTER TABLE `film` DISABLE KEYS */;
INSERT INTO `film` VALUES (1,'Chun Jiao jiu Zhi Ming','A couple who have been together for several years try to \r\n decide if they should stay together.'),(2,'Guardians of the Galaxy','Set to the backdrop of Awesome Mixtape #2, \'Guardians of the Galaxy \r\n Vol. 2\' continues the team\'s adventures as they unravel the mystery of \r\n Peter Quill\'s true parentage.'),(3,'Power Rangers','A group of high-school students, who are infused with unique superpowers, \r\n harness their abilities in order to save the world.'),(4,'Dangal','Former wrestler Mahavir Singh Phogat and his two wrestler daughters struggle \r\n towards glory at the Commonwealth Games in the face of societal oppression.');
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'avatar',50,'jack','2017-5-15','yonghua'),(2,'avatar',50,'jack','2017-5-15','yonghua'),(3,'Chun Jiao jiu Zhi Ming',50,'jack','05-24 11:45','UA Cinema'),(4,'Chun Jiao jiu Zhi Ming',50,'jack','05-24 11:45','UA Cinema');
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
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `timetable`
--

LOCK TABLES `timetable` WRITE;
/*!40000 ALTER TABLE `timetable` DISABLE KEYS */;
INSERT INTO `timetable` VALUES (1,'2017-5-15','avatar',49,'yonghua',50),(2,'05-24 11:35','Dangal',50,'SFC Cinema',50),(3,'05-24 14:10','Dangal',50,'SFC Cinema',50),(4,'05-24 16:45','Dangal',50,'SFC Cinema',50),(5,'05-24 19:20','Dangal',50,'SFC Cinema',50),(6,'05-24 22:00','Dangal',50,'SFC Cinema',50),(7,'05-24 11:35','Dangal',50,'UA Cinema',50),(8,'05-24 13:00','Dangal',50,'UA Cinema',50),(9,'05-24 14:10','Dangal',50,'UA Cinema',50),(10,'05-24 15:35','Dangal',50,'UA Cinema',50),(11,'05-24	16:45','Dangal',50,'UA Cinema',50),(12,'05-24 11:45','Guardians of the Galaxy',50,'SFC Cinema',50),(13,'05-24 14:20','Guardians of the Galaxy',50,'SFC Cinema',50),(14,'05-24 16:55','Guardians of the Galaxy',50,'SFC Cinema',50),(15,'05-24 19:30','Guardians of the Galaxy',50,'SFC Cinema',50),(16,'05-24 22:05','Guardians of the Galaxy',50,'SFC Cinema',50),(17,'05-24 16:30','Guardians of the Galaxy',50,'UA Cinema',50),(18,'05-24 19:00','Guardians of the Galaxy',50,'UA Cinema',50),(19,'05-24 21:30','Guardians of the Galaxy',50,'UA Cinema',50),(20,'05-24 11:45','Power Rangers',50,'UA Cinema',50),(21,'05-24 13:45','Power Rangers',50,'UA Cinema',50),(22,'05-24 14:45','Power Rangers',50,'UA Cinema',50),(23,'05-24 15:45','Power Rangers',50,'UA Cinema',50),(24,'05-24 11:00','Power Rangers',50,'SFC Cinema',50),(25,'05-24 13:00','Power Rangers',50,'SFC Cinema',50),(26,'05-24 14:00','Power Rangers',50,'SFC Cinema',50),(27,'05-24 16:00','Power Rangers',50,'SFC Cinema',50),(28,'05-24 11:45','Chun Jiao Jiu Zhi Ming',49,'UA Cinema',50),(29,'05-24 13:45','Chun Jiao Jiu Zhi Ming',50,'UA Cinema',50),(30,'05-24 14:45','Chun Jiao Jiu Zhi Ming',50,'UA Cinema',50),(31,'05-24 15:45','Chun Jiao Jiu Zhi Ming',50,'UA Cinema',50),(32,'05-24 11:00','Chun Jiao Jiu Zhi Ming',50,'SFC Cinema',50),(33,'05-24 13:00','Chun Jiao Jiu Zhi Ming',50,'SFC Cinema',50),(34,'05-24 14:00','Chun Jiao Jiu Zhi Ming',50,'SFC Cinema',50),(35,'05-24 16:00','Chun Jiao Jiu Zhi Ming',50,'SFC Cinema',50);
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

-- Dump completed on 2017-05-21 21:02:33
