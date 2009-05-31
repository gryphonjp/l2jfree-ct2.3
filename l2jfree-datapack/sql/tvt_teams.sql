DROP TABLE IF EXISTS `tvt_teams`;
CREATE TABLE `tvt_teams` (
  `teamId` INT(4) NOT NULL DEFAULT 0,
  `teamName` VARCHAR(255) NOT NULL DEFAULT '',
  `teamX` INT(11) NOT NULL DEFAULT 0,
  `teamY` INT(11) NOT NULL DEFAULT 0,
  `teamZ` INT(11) NOT NULL DEFAULT 0,
  `teamColor` INT(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`teamId`)
) DEFAULT CHARSET=utf8;