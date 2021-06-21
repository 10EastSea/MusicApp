-- MariaDB dump 10.17  Distrib 10.5.6-MariaDB, for osx10.15 (x86_64)
--
-- Host: localhost    Database: music_service
-- ------------------------------------------------------
-- Server version	10.5.6-MariaDB

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
-- Current Database: `music_service`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `music_service` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `music_service`;

--
-- Table structure for table `ALBUM`
--

DROP TABLE IF EXISTS `ALBUM`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ALBUM` (
  `Album_id` int(11) NOT NULL,
  `Album_title` varchar(30) NOT NULL,
  `Album_type` varchar(15) DEFAULT NULL,
  `Agency` varchar(15) DEFAULT NULL,
  `Release_company` varchar(15) DEFAULT NULL,
  `Release_date` date DEFAULT NULL,
  PRIMARY KEY (`Album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ALBUM`
--

LOCK TABLES `ALBUM` WRITE;
/*!40000 ALTER TABLE `ALBUM` DISABLE KEYS */;
INSERT INTO `ALBUM` VALUES (1,'Love poem','Single/EP','Kakao M','Kakao M','2019-11-18'),(2,'Palette','Studio album','Kakao M','Kakao M','2017-04-21'),(3,'Dynamite','Single/EP','Dreamus','Big Hit','2020-08-21'),(4,'ITz ME','Single/EP','Dreamus','JYP','2020-03-09'),(5,'Boyhood','Studio album','Genie Music','Ambition Musik','2019-11-29'),(6,'쇼미더머니 9 Episode 1','Album','Genie Music','Stone Music','2020-11-21'),(7,'I am','Single/EP','Kakao M','Cube','2018-05-02'),(8,'YESTERDAY','Single/EP','Genie Music','세븐시즌스','2017-02-06'),(9,'Brother Act.','Studio album','Kakao M','Cube','2017-10-16'),(10,'우연히 봄','Album','Kakao M','도너츠컬쳐','2015-04-08'),(11,'THE ALBUM','Studio album','YG PLUS','YG','2020-10-02');
/*!40000 ALTER TABLE `ALBUM` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ALBUM_GENRE`
--

DROP TABLE IF EXISTS `ALBUM_GENRE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ALBUM_GENRE` (
  `AIndex` int(11) NOT NULL,
  `AGenre` varchar(15) NOT NULL,
  PRIMARY KEY (`AIndex`,`AGenre`),
  CONSTRAINT `album_genre_ibfk_1` FOREIGN KEY (`AIndex`) REFERENCES `ALBUM` (`Album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ALBUM_GENRE`
--

LOCK TABLES `ALBUM_GENRE` WRITE;
/*!40000 ALTER TABLE `ALBUM_GENRE` DISABLE KEYS */;
INSERT INTO `ALBUM_GENRE` VALUES (1,'Rock'),(1,'Song'),(2,'Ballad'),(2,'Song'),(3,'Dance'),(3,'Song'),(4,'Dance'),(4,'Song'),(5,'Hip Hop'),(5,'Rap'),(5,'Song'),(6,'Hip Hop'),(6,'Rap'),(6,'Song'),(7,'Dance'),(7,'Song'),(8,'Dance'),(8,'Song'),(9,'Ballad'),(9,'Song'),(10,'Drama'),(10,'OST'),(11,'Dance'),(11,'Song');
/*!40000 ALTER TABLE `ALBUM_GENRE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ALBUM_MAKE`
--

DROP TABLE IF EXISTS `ALBUM_MAKE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ALBUM_MAKE` (
  `AM_Artist_idx` int(11) NOT NULL,
  `AM_Album_idx` int(11) NOT NULL,
  PRIMARY KEY (`AM_Artist_idx`,`AM_Album_idx`),
  KEY `AM_Album_idx` (`AM_Album_idx`),
  CONSTRAINT `album_make_ibfk_1` FOREIGN KEY (`AM_Artist_idx`) REFERENCES `ARTIST` (`Artist_id`),
  CONSTRAINT `album_make_ibfk_2` FOREIGN KEY (`AM_Album_idx`) REFERENCES `ALBUM` (`Album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ALBUM_MAKE`
--

LOCK TABLES `ALBUM_MAKE` WRITE;
/*!40000 ALTER TABLE `ALBUM_MAKE` DISABLE KEYS */;
INSERT INTO `ALBUM_MAKE` VALUES (1,1),(1,2),(2,5),(5,4),(6,7),(7,3),(16,9),(20,8),(22,6),(39,10),(40,10),(41,11);
/*!40000 ALTER TABLE `ALBUM_MAKE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ARTIST`
--

DROP TABLE IF EXISTS `ARTIST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ARTIST` (
  `Artist_id` int(11) NOT NULL,
  `Artist_name` varchar(30) NOT NULL,
  PRIMARY KEY (`Artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ARTIST`
--

LOCK TABLES `ARTIST` WRITE;
/*!40000 ALTER TABLE `ARTIST` DISABLE KEYS */;
INSERT INTO `ARTIST` VALUES (1,'IU'),(2,'CHANGMO'),(3,'GFRIEND'),(4,'Red Velvet'),(5,'ITZY'),(6,'(G)-IDLE'),(7,'BTS'),(8,'ZICO'),(9,'Tymee'),(10,'Apink'),(11,'OH MY GIRL'),(12,'CIKI'),(13,'yourbeagle'),(14,'NO:EL'),(15,'VEKOEL'),(16,'BTOB'),(17,'DIA'),(18,'N.Flying'),(19,'GRAY'),(20,'Block B'),(21,'Sojin'),(22,'Various Artists'),(23,'Swings'),(24,'Mckdaddy'),(25,'Khakii'),(26,'Layone'),(27,'허성현'),(28,'dsel'),(29,'kaogaii'),(30,'Untell'),(31,'미란이'),(32,'먼치맨'),(33,'Khundi Panda'),(34,'MUSHVENOM'),(35,'릴보이'),(36,'원슈타인'),(37,'Chillin Homie'),(38,'스카이민혁'),(39,'LOCO'),(40,'유주'),(41,'BLACKPICK');
/*!40000 ALTER TABLE `ARTIST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MUSIC`
--

DROP TABLE IF EXISTS `MUSIC`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MUSIC` (
  `Music_id` int(11) NOT NULL,
  `Music_title` varchar(30) NOT NULL,
  `Lyricist` varchar(30) DEFAULT NULL,
  `Composer` varchar(30) DEFAULT NULL,
  `Arranger` varchar(30) DEFAULT NULL,
  `Hits_num` int(11) DEFAULT 0,
  `Album_idx` int(11) NOT NULL,
  PRIMARY KEY (`Music_id`),
  KEY `Album_idx` (`Album_idx`),
  CONSTRAINT `music_ibfk_1` FOREIGN KEY (`Album_idx`) REFERENCES `ALBUM` (`Album_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MUSIC`
--

LOCK TABLES `MUSIC` WRITE;
/*!40000 ALTER TABLE `MUSIC` DISABLE KEYS */;
INSERT INTO `MUSIC` VALUES (1,'WANNABE','별들의전쟁','별들의전쟁','GALACTIKA',24,4),(2,'THATs A NO NO','심은지','심은지','심은지',11,4),(3,'TING TING TING','PENOMECO','Warren','Oak',6,4),(4,'원해','Swings','CODE KUNST','CODE KUNST',10,6),(5,'윈윈','허성현','BewhY','BewhY',7,6),(6,'VVS','JUSTHIS','GroovyRoom','GroovyRoom',42,6),(7,'Freak','Zion.T','Slom','Slom',9,6),(8,'그 사람','IU','IU','적재',0,1),(9,'Blueming','IU','이종훈','이종훈',0,1),(10,'시간의 바깥','IU','이민수','이민수',0,1),(11,'Love poem','IU','이종훈','적재',11,1),(12,'이 지금','IU','제휘','제휘',1,2),(13,'팔레트','IU','IU','이종훈',1,2),(14,'잼잼','선우정아','선우정아','선우정아',0,2),(15,'Black Out','IU','이종훈','이종훈',3,2),(16,'이름에게','IU','이종훈','홍소진',0,2),(17,'Dynamite','David','David','David',65,3),(18,'빌었어','CHANGMO','CHANGMO','CHANGMO',27,5),(19,'METEOR','CHANGMO','CHANGMO','CHANGMO',36,5),(20,'REMEDY','CHANGMO','CHANGMO','CHANGMO',10,5),(21,'LATATA','소연','소연','Yummy Tone',34,7),(22,'달라 ($$$)','Le mon','MonoTree','MonoTree',4,7),(23,'MAZE','MosPick','MosPick','MosPick',25,7),(24,'DONT TEXT ME','Yummy Tone','Yummy Tone','Yummy Tone',0,7),(25,'알고싶어','ARIN','ARIN','Vincenzo',0,7),(26,'들어줘요','MosPick','MosPick','MosPick',22,7),(27,'YESTERDAY','박경','박경','박경',0,8),(28,'그리워하다','임현식','임현식','임현식',3,9),(29,'My Lady','정일훈','정일훈','IL',1,9),(30,'우연히 봄','최재우','똘아이박','똘아이박',23,10),(31,'Pretty Savage','TEDDY','TEDDY','TEDDY',0,11),(32,'Lovesick Girls','TEDDY','TEDDY','24',12,11);
/*!40000 ALTER TABLE `MUSIC` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MUSIC_GENRE`
--

DROP TABLE IF EXISTS `MUSIC_GENRE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MUSIC_GENRE` (
  `MIndex` int(11) NOT NULL,
  `MGenre` varchar(15) NOT NULL,
  PRIMARY KEY (`MIndex`,`MGenre`),
  CONSTRAINT `music_genre_ibfk_1` FOREIGN KEY (`MIndex`) REFERENCES `MUSIC` (`Music_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MUSIC_GENRE`
--

LOCK TABLES `MUSIC_GENRE` WRITE;
/*!40000 ALTER TABLE `MUSIC_GENRE` DISABLE KEYS */;
INSERT INTO `MUSIC_GENRE` VALUES (1,'Dance'),(2,'Dance'),(3,'Dance'),(4,'Hip Hop'),(4,'Rap'),(5,'Hip Hop'),(5,'Rap'),(6,'Hip Hop'),(6,'Rap'),(7,'Hip Hop'),(7,'Rap'),(8,'Ballad'),(9,'Rock'),(10,'Ballad'),(11,'Ballad'),(12,'Ballad'),(13,'Ballad'),(14,'Ballad'),(15,'Ballad'),(16,'Ballad'),(17,'Dance'),(18,'Hip Hop'),(18,'Rap'),(19,'Hip Hop'),(19,'Rap'),(20,'Hip Hop'),(20,'Rap'),(21,'Dance'),(22,'Dance'),(23,'Dance'),(24,'Dance'),(25,'Dance'),(26,'Dance'),(27,'Dance'),(28,'Ballad'),(29,'Ballad'),(30,'Drama'),(31,'Hip Hop'),(31,'Rap'),(32,'Dance');
/*!40000 ALTER TABLE `MUSIC_GENRE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MUSIC_IN_LIST`
--

DROP TABLE IF EXISTS `MUSIC_IN_LIST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MUSIC_IN_LIST` (
  `MIL_Playlist_idx` int(11) NOT NULL,
  `MIL_Music_idx` int(11) NOT NULL,
  PRIMARY KEY (`MIL_Playlist_idx`,`MIL_Music_idx`),
  KEY `MIL_Music_idx` (`MIL_Music_idx`),
  CONSTRAINT `music_in_list_ibfk_1` FOREIGN KEY (`MIL_Playlist_idx`) REFERENCES `PLAYLIST` (`Playlist_id`),
  CONSTRAINT `music_in_list_ibfk_2` FOREIGN KEY (`MIL_Music_idx`) REFERENCES `Music` (`Music_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MUSIC_IN_LIST`
--

LOCK TABLES `MUSIC_IN_LIST` WRITE;
/*!40000 ALTER TABLE `MUSIC_IN_LIST` DISABLE KEYS */;
INSERT INTO `MUSIC_IN_LIST` VALUES (1,1),(1,2),(1,6),(1,7),(1,17),(1,18),(1,19),(1,21),(1,23),(1,26),(2,1),(2,2),(2,3),(3,4),(3,5),(3,6),(3,7),(4,4),(4,5),(4,6),(4,7),(5,22);
/*!40000 ALTER TABLE `MUSIC_IN_LIST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MUSIC_MAKE`
--

DROP TABLE IF EXISTS `MUSIC_MAKE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MUSIC_MAKE` (
  `MM_Artist_idx` int(11) NOT NULL,
  `MM_Music_idx` int(11) NOT NULL,
  PRIMARY KEY (`MM_Artist_idx`,`MM_Music_idx`),
  KEY `MM_Music_idx` (`MM_Music_idx`),
  CONSTRAINT `music_make_ibfk_1` FOREIGN KEY (`MM_Music_idx`) REFERENCES `MUSIC` (`Music_id`),
  CONSTRAINT `music_make_ibfk_2` FOREIGN KEY (`MM_Artist_idx`) REFERENCES `ARTIST` (`Artist_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MUSIC_MAKE`
--

LOCK TABLES `MUSIC_MAKE` WRITE;
/*!40000 ALTER TABLE `MUSIC_MAKE` DISABLE KEYS */;
INSERT INTO `MUSIC_MAKE` VALUES (1,8),(1,9),(1,10),(1,11),(1,12),(1,13),(1,14),(1,15),(1,16),(2,18),(2,19),(2,20),(5,1),(5,2),(5,3),(6,21),(6,22),(6,23),(6,24),(6,25),(6,26),(7,17),(16,28),(16,29),(20,27),(23,4),(24,4),(25,4),(26,4),(27,5),(28,5),(29,5),(30,5),(31,6),(32,6),(33,6),(34,6),(35,7),(36,7),(37,7),(38,7),(39,30),(40,30),(41,31),(41,32);
/*!40000 ALTER TABLE `MUSIC_MAKE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PLAYLIST`
--

DROP TABLE IF EXISTS `PLAYLIST`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PLAYLIST` (
  `Playlist_id` int(11) NOT NULL,
  `Playlist_title` varchar(30) NOT NULL,
  `Creation_date` date DEFAULT NULL,
  `User_idx` int(11) NOT NULL,
  PRIMARY KEY (`Playlist_id`),
  KEY `User_idx` (`User_idx`),
  CONSTRAINT `playlist_ibfk_1` FOREIGN KEY (`User_idx`) REFERENCES `User` (`User_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PLAYLIST`
--

LOCK TABLES `PLAYLIST` WRITE;
/*!40000 ALTER TABLE `PLAYLIST` DISABLE KEYS */;
INSERT INTO `PLAYLIST` VALUES (1,'EastSea Recommand!','2020-11-24',19980522),(2,'ITZY Love','2020-11-25',20101000),(3,'Test Playlist2','2020-11-25',20101000),(4,'Show Me The Money','2020-11-25',20101000),(5,'HOT SUMMER','2020-11-26',19980522);
/*!40000 ALTER TABLE `PLAYLIST` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER`
--

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `User_id` int(11) NOT NULL,
  `User_pw` varchar(15) NOT NULL,
  `User_name` varchar(30) NOT NULL,
  `Phone` varchar(15) DEFAULT NULL,
  `Ssn` varchar(15) NOT NULL,
  `Address` varchar(30) DEFAULT NULL,
  `Is_admin` int(11) NOT NULL,
  PRIMARY KEY (`User_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `USER` DISABLE KEYS */;
INSERT INTO `USER` VALUES (19980522,'admin','EastSea_Admin','010-9360-2458','980522-1111111','4678, Seongnam',1),(20101000,'test1','EastSea_test1','010-1111-1111','101010-0000001','1, Seongnam',0),(20101001,'test2','EastSea_test2','010-1111-1112','101010-0000002','2, Seongnam',0),(20101003,'test3','EastSea_test3','010-1111-1113','101010-0000003','3, Seongnam',0),(20101004,'test4','Java_in_test4','010-1111-1114','101010-0000004','4, Seoul',0),(20101005,'test5','Java_in_test5','010-1111-0005','101010-0000005','5, Seoul',0);
/*!40000 ALTER TABLE `USER` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-02  2:07:43
