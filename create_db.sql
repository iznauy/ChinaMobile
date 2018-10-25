create database chinamobile;
use chinamobile;

DROP TABLE IF EXISTS `current_packages`;
CREATE TABLE `current_packages` (
  `package_id` bigint(20) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  PRIMARY KEY (`package_id`,`phone_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `package_content`;
CREATE TABLE `package_content` (
  `package_id` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`package_id`,`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `packages`;
CREATE TABLE `packages` (
  `date` datetime NOT NULL,
  `package_id` bigint(20) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `type` int(11) NOT NULL,
  `amount` double NOT NULL,
  PRIMARY KEY (`date`,`package_id`,`phone_number`,`type`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `packages_order`;
CREATE TABLE `packages_order` (
  `package_id` bigint(20) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `in_force_type` int(11) NOT NULL,
  `order_type` int(11) NOT NULL,
  PRIMARY KEY (`package_id`,`phone_number`,`time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `phone_data`;
CREATE TABLE `phone_data` (
  `phone_number` varchar(255) NOT NULL,
  `start_date` datetime NOT NULL,
  `amount` double NOT NULL,
  `end_date` datetime NOT NULL,
  `fee` double NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`phone_number`,`start_date`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `positive_phone_call`;
CREATE TABLE `positive_phone_call` (
  `sender` varchar(255) NOT NULL,
  `start_time` datetime NOT NULL,
  `end_time` datetime NOT NULL,
  `fee` double NOT NULL,
  `receiver` varchar(255) NOT NULL,
  PRIMARY KEY (`sender`,`start_time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `sent_message`;
CREATE TABLE `sent_message` (
  `receiver` varchar(255) NOT NULL,
  `sender` varchar(255) NOT NULL,
  `time` datetime NOT NULL,
  `fee` double NOT NULL,
  `message` varchar(255) NOT NULL,
  PRIMARY KEY (`receiver`,`sender`,`time`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `supported_packages`;
CREATE TABLE `supported_packages` (
  `id` bigint(20) NOT NULL,
  `fee` double NOT NULL,
  `package_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `phone_number` varchar(255) NOT NULL,
  `balance` double NOT NULL,
  `extra_domestic_data` double NOT NULL,
  `extra_message_count` int(11) NOT NULL,
  `extra_native_data` double NOT NULL,
  `extra_phone_call_time` int(11) NOT NULL,
  PRIMARY KEY (`phone_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_history_bill`;
CREATE TABLE `user_history_bill` (
  `date` date NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `extra_domestic_data` double NOT NULL,
  `extra_message_count` int(11) NOT NULL,
  `extra_native_data` double NOT NULL,
  `extra_phone_call_time` int(11) NOT NULL,
  PRIMARY KEY (`date`,`phone_number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;


