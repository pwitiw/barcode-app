SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
CREATE DATABASE IF NOT EXISTS frontwit;
USE frontwit;
CREATE TABLE IF NOT EXISTS `tdictionary`
(
    `id`   int(11)     NOT NULL,
    `name` varchar(45) NOT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 1369
  DEFAULT CHARSET = utf8;
INSERT INTO `tdictionary` (`id`, `name`)
VALUES (1, '18MM'),
       (2, 'PŁYTA'),
       (3, 'BIAŁY'),
       (4, 'CZARNY');
CREATE TABLE IF NOT EXISTS `tklienci`
(
    `id`    int(10) unsigned NOT NULL,
    `nazwa` varchar(45)      NOT NULL,
    `adres` varchar(45) DEFAULT NULL,
    `email` varchar(45) DEFAULT NULL
) ENGINE = MyISAM
  AUTO_INCREMENT = 1326
  DEFAULT CHARSET = utf8;

INSERT INTO `tklienci` (`id`, `nazwa`, `adres`, `email`)
VALUES (1, 'Jan Kowalski', 'Wroclaw', 'www@gmail.com');

CREATE TABLE IF NOT EXISTS `tzamowienia`
(
    `id`          int(10) unsigned NOT NULL,
    `tklienci_id` int(10) unsigned NOT NULL,
    `data_z`      date             NOT NULL,
    `numer`       varchar(20)      NOT NULL,
    `opis`        varchar(255) DEFAULT NULL,
    `cechy`       varchar(255) DEFAULT NULL,
    `pozycje`     mediumtext       NOT NULL,
    `nr_zam_kl`   varchar(20)  DEFAULT NULL
) ENGINE = MyISAM
  AUTO_INCREMENT = 14209
  DEFAULT CHARSET = utf8;

INSERT INTO `tzamowienia` (`id`, `tklienci_id`, `data_z`, `numer`, `opis`, `cechy`, `pozycje`, `nr_zam_kl`)
VALUES (1, 1, '2019-06-07', 'TW 100', 'express', '{"cu":"2","si":"1","co":"3","do":"1"}', '[
{"nr":"1","l":"1000","w":"2000","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"3","com":"komentarz 1"},
{"nr":"1","l":"800","w":"500","q":"2","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"3","com":"komentarz 2"},
{"nr":"1","l":"500","w":"300","q":"3","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"","com":""}
]', NULL),
       (2, 1, '2019-06-08', 'TW 100', 'express', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"10","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"komentarz 3"}
]', NULL);

