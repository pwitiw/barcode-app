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
    `adres` varchar(45)      DEFAULT NULL,
    `email` varchar(45)      DEFAULT NULL,
    `trasa` varchar(45)      DEFAULT NULL,
    `telefon` varchar(45)    DEFAULT NULL
) ENGINE = MyISAM
  AUTO_INCREMENT = 1326
  DEFAULT CHARSET = utf8;

INSERT INTO `tklienci` (`id`, `nazwa`, `adres`,`trasa`, `email`, `telefon`)
VALUES (1, 'Jan Kowalski', 'Wroclaw', 'wroclaw', 'www@gmail.com', '456123789'),
       (2, 'Adam Batat', 'Warszawa', 'warszawa', 'www@gmail.com', NULL),
       (3, 'Grzegorz Gaj', NULL, 'lublin', 'www@gmail.com', '258741369');

CREATE TABLE IF NOT EXISTS `tzamowienia`
(
    `id`          int(10) unsigned NOT NULL,
    `tklienci_id` int(10) unsigned NOT NULL,
    `data_z`      date             NOT NULL,
    `data_r`      date             DEFAULT NULL,
    `numer`       varchar(20)      NOT NULL,
    `opis`        varchar(255) DEFAULT NULL,
    `cechy`       varchar(255) DEFAULT NULL,
    `pozycje`     mediumtext       NOT NULL,
    `nr_zam_kl`   varchar(20)  DEFAULT NULL,
    `valuation`   float        DEFAULT NULL,
    `rodzaj`      char(1)      DEFAULT NULL
) ENGINE = MyISAM
  AUTO_INCREMENT = 14209
  DEFAULT CHARSET = utf8;

INSERT INTO `tzamowienia` (`id`, `tklienci_id`, `data_z`, `data_r`, `numer`, `opis`, `cechy`, `pozycje`, `nr_zam_kl`, `valuation`, `rodzaj`)
VALUES (17, 1, '2020-11-27', '2020-06-09', 'TW 100', 'express', '{"cu":"2","si":"1","co":"3","do":"1"}', '[
{"nr":"1","l":"1000","w":"2000","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"3","com":"komentarz 1"},
{"nr":"2","l":"800","w":"5000","q":"2","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"3","com":"dociac"},
{"nr":"3","l":"500","w":"300","q":"3","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"","com":""}
]', NULL, '9002.40', 'z'),
       (18, 1, '2020-11-28','2020-06-23', 'TW 101', 'express', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"3000","w":"2000","q":"2","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"casadas"}
]', NULL, '12300', 'z'),
       (19, 1, '2020-10-09', '2020-06-28', 'TW 102', 'nic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"123"}
]', NULL, '452', 'z'),
       (4, 1, '2020-09-10', '2020-06-15', 'TW 103', '', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"aadas"}
]', NULL, '4523', 'z'),
       (5, 1, '2020-06-11', '2020-07-09', 'TW 104', 'nicasd', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"sasdasd"}
]', NULL, '742.80', 'r'),
       (6, 1, '2020-11-12', '2020-08-09', 'TW 105', 'naaic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"komentarz yk"}
]', NULL, '300.5', 'r'),
       (7, 1, '2020-10-13', '2020-06-23', 'TW 106', 'nic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"2"}
]', NULL, '9000', 'z'),
       (8, 1, '2020-11-14', '2020-09-09', 'TW 107', 'nic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"123"}
]', NULL, '4100', 'r'),
       (9, 1, '2020-11-30', '2020-10-30', 'TW 108', 'nic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"sad 3"}
]', NULL, '74500', 'z'),
       (10, 1, '2020-10-15', '2020-11-09', 'TW 109', 'nic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"3"}
]', NULL, '1478.78', 'z'),
       (11, 1, '2020-09-16', '2020-09-09', 'TW 110', 'nic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"1000","w":"2000","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":""}
]', NULL, '635', 'z'),
       (12, 1, '2020-06-16', '2020-09-09', 'TW 111', 'nic', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"1000","w":"2000","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"nic"}
]', NULL, '85.89', 'z'),
       (13, 1, '2020-01-13', '2020-02-05', 'TW 112', 'express', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"niee"}
]', 'dodatkowe info', '12364', 'r'),
       (14, 1, '2020-06-13', '2020-07-05', 'TW 113', 'express', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"wcale"}
]', NULL, '7485.85', 'z'),
       (15, 1, '2020-07-27', '2020-07-28', 'TW 114', 'express', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"1","l":"100","w":"200","q":"1","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"grubo"}
]', 'jakis klient', '4589.4', 'r'),
       (25486, 1, '2020-11-27', '2020-07-30', 'TW 115', 'express', '{"cu":"2","si":"1","co":"4","do":"1"}', '[
{"nr":"7","l":"100","w":"200","q":"2","a":"0.484","el":"","cu":"2","si":"1","do":1,"co":"4","com":"po skosie"}
]', 'ta jasne', '7895.50', 'z');