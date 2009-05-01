-- ---------------------------
-- Table structure for `castle_zoneupgrade`
-- ---------------------------
CREATE TABLE IF NOT EXISTS `castle_zoneupgrade` (
  `castleId` TINYINT(1) UNSIGNED NOT NULL,
  `side` TINYINT(1) UNSIGNED NOT NULL,
  `level` TINYINT(1) UNSIGNED NOT NULL,
  PRIMARY KEY (`castleId`,`side`)
) DEFAULT CHARSET=utf8;