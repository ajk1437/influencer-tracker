/*
SQLyog Ultimate v12.14 (64 bit)
MySQL - 5.7.19 : Database - movies
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`influencers` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `influencers`;

/*Table structure for table `influencer` */

DROP TABLE IF EXISTS `influencer`;

CREATE TABLE `influencer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` TINYTEXT,
  `game` TINYTEXT,
  `views` INT,
  `language` TINYTEXT,
  `timestamp` TIMESTAMP, 
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

/*Data for the table `influencer` */

insert  into `influencer`(`id`,`username`,`game`,`views`,`language`,`timestamp`) values 
(1,'summit1g','Grand Theft Auto V', 34869,'en', '2021-03-01T13:48:59.10');

insert  into `influencer`(`id`,`username`,`game`,`views`,`language`,`timestamp`) values 
(2,'Gaules','Counter-Strike: Global Offensive', 26856,'en', '2021-04-01T14:19:03.71');

insert  into `influencer`(`id`,`username`,`game`,`views`,`language`,`timestamp`) values 
(3,'Faker','League of Legends', 29005, 'ko', '2021-05-01T16:04:18.28');

insert  into `influencer`(`id`,`username`,`game`,`views`,`language`,`timestamp`) values 
(4,'auronplay','Just Chatting', 88130,'en', '2020-03-01T17:04:26.59');

insert  into `influencer`(`id`,`username`,`game`,`views`,`language`,`timestamp`) values 
(5,'tommyinnit','Minecraft', 172712, 'en', '2020-06-01T19:49:49.78');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
