--
-- Create schema test
--

#CREATE DATABASE IF NOT EXISTS HL8sVJOtq3;
USE IVQmsxnr5H;

--
-- Definition of table `flights`
--

DROP TABLE IF EXISTS `flights`;
CREATE TABLE `flights` (
  `num` int(10) unsigned NOT NULL auto_increment,
  `origin` varchar(45) NOT NULL,
  `destination` varchar(45) NOT NULL,
  `distance` int(10) unsigned NOT NULL,
  `price` float NOT NULL,
  PRIMARY KEY  (`num`)
) ENGINE=InnoDB AUTO_INCREMENT=7790 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `flights`
--

INSERT INTO `flights` (`num`,`origin`,`destination`,`distance`,`price`) VALUES 
 (2,'Los Angeles\"','\"Tokyo\"',5478,780),
 (7,'Los Angeles\"','\"Sydney\"',7487,1278),
 (13,'\"Los Angeles\"','\"Chicago\"',1749,220),
 (33,'\"Los Angeles\"','\"Honolulu\"',2551,375),
 (34,'\"Los Angeles\"','\"Honolulu\"',2551,425),
 (68,'\"Chicago\"','\"New York\"',802,202),
 (76,'\"Chicago\"','\"Los Angeles\"',1749,220),
 (99,'\"Los Angeles\"','\"Washington D.C.\"',2308,235),
 (149,'\"Pittsburgh\"','\"New York\"',303,116),
 (304,'\"Minneapolis\"','\"New York\"',991,101),
 (346,'\"Los Angeles\"','\"Dallas\"',1251,225),
 (387,'\"Los Angeles\"','\"Boston\"',2606,261),
 (701,'\"Detroit\"','\"New York\"',470,180),
 (702,'\"Madison\"','\"New York\"',789,202),
 (2223,'\"Madison\"','\"Pittsburgh\"',517,189),
 (4884,'\"Madison\"','\"Chicago\"',84,112),
 (5694,'\"Madison\"','\"Minneapolis\"',247,120),
 (7789,'\"Madison\"','\"Detroit\"',319,120);
