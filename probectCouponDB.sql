CREATE DATABASE  IF NOT EXISTS `coupon_project` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `coupon_project`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: coupon_project
-- ------------------------------------------------------
-- Server version	5.7.16-log

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
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `COMP_ID` bigint(9) NOT NULL AUTO_INCREMENT,
  `COMP_NAME` varchar(45) NOT NULL,
  `PASSWORD` varchar(45) NOT NULL,
  `EMAIL` varchar(45) NOT NULL,
  PRIMARY KEY (`COMP_ID`,`COMP_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (2,'DailyPlanet','123456','superman@wall.com'),(3,'Haduplex','74123','wow@gmail.com'),(4,'BurgersINC','yami','takeabite@gmail.com'),(9,'BEZEQ','hara','bezeq@bezeq.co.il'),(37,'recycableCompany','11991199','recycable@fun.com');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_coupon`
--

DROP TABLE IF EXISTS `company_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company_coupon` (
  `COMP_ID` bigint(9) NOT NULL,
  `COUP_ID` bigint(9) NOT NULL,
  PRIMARY KEY (`COMP_ID`,`COUP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_coupon`
--

LOCK TABLES `company_coupon` WRITE;
/*!40000 ALTER TABLE `company_coupon` DISABLE KEYS */;
INSERT INTO `company_coupon` VALUES (2,30),(3,65),(4,5);
/*!40000 ALTER TABLE `company_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `coupon` (
  `COUPON_ID` bigint(9) NOT NULL AUTO_INCREMENT,
  `TITLE` varchar(45) NOT NULL,
  `START_DATE` date NOT NULL,
  `END_DATE` date NOT NULL,
  `AMOUNT` int(11) NOT NULL,
  `TYPE` varchar(45) NOT NULL,
  `MESSAGE` varchar(45) NOT NULL,
  `PRICE` double NOT NULL,
  `IMAGE` varchar(45) NOT NULL,
  PRIMARY KEY (`COUPON_ID`,`TITLE`),
  UNIQUE KEY `TITLE_UNIQUE` (`TITLE`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES (5,'newStolenShip','2020-02-02','2020-03-03',5,'Sports','dfssdg',100,'bamba'),(8,'2for1 Rom','2020-02-02','2020-03-03',5,'Food','dfssdg',42.2,'bamba'),(9,'tarDiscount','2020-02-02','2020-03-03',5,'Food','dfssdg',42.2,'bamba'),(30,'FlightDiscount','2012-01-01','2018-03-03',14,'Sports','50% Off',1515.22,'waff.png'),(45,'FreeFoxRide','2015-05-05','2020-02-02',2,'Sports','free couple fox ride',0,'yay.png'),(65,'TentFor4','2019-01-01','2020-03-03',5,'Camping','soCamping',79.99,'leaf.png');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `CUST_ID` bigint(9) NOT NULL AUTO_INCREMENT,
  `cust_name` varchar(45) NOT NULL,
  `password` mediumtext NOT NULL,
  PRIMARY KEY (`CUST_ID`,`cust_name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES (1,'jack_sparrow','151689'),(2,'tovtov_hagamad','815625'),(3,'niels_holgerson','874123'),(4,'ash_catchem','151');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_coupon`
--

DROP TABLE IF EXISTS `customer_coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_coupon` (
  `CUST_ID` bigint(9) NOT NULL,
  `COUPON_ID` bigint(9) NOT NULL,
  PRIMARY KEY (`CUST_ID`,`COUPON_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_coupon`
--

LOCK TABLES `customer_coupon` WRITE;
/*!40000 ALTER TABLE `customer_coupon` DISABLE KEYS */;
INSERT INTO `customer_coupon` VALUES (1,0),(1,5),(1,8),(1,65),(2,45);
/*!40000 ALTER TABLE `customer_coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'coupon_project'
--

--
-- Dumping routines for database 'coupon_project'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-23 17:40:23
