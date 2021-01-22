CREATE TABLE IF NOT EXISTS `tdictionary`
(
    `id`   int(11)     NOT NULL,
    `name` varchar(45) NOT NULL
) ENGINE = InnoDB
  AUTO_INCREMENT = 1369
  DEFAULT CHARSET = utf8;

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

CREATE TABLE IF NOT EXISTS `tzamowienia`
(
    `id`          int(10) unsigned NOT NULL,
    `tklienci_id` int(10) unsigned NOT NULL,
    `data_z`      date             NOT NULL,
    `data_r`      date             NULL,
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