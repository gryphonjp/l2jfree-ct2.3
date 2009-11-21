CREATE TABLE IF NOT EXISTS `characters` (
  `account_name` VARCHAR(45) DEFAULT NULL,
  `charId` INT UNSIGNED NOT NULL DEFAULT 0,
  `char_name` VARCHAR(35) NOT NULL,
  `level` TINYINT UNSIGNED DEFAULT NULL,
  `maxHp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `curHp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `maxCp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `curCp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `maxMp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `curMp` MEDIUMINT UNSIGNED DEFAULT NULL,
  `face` TINYINT UNSIGNED DEFAULT NULL,
  `hairStyle` TINYINT UNSIGNED DEFAULT NULL,
  `hairColor` TINYINT UNSIGNED DEFAULT NULL,
  `sex` TINYINT UNSIGNED DEFAULT NULL,
  `heading` MEDIUMINT DEFAULT NULL,
  `x` MEDIUMINT DEFAULT NULL,
  `y` MEDIUMINT DEFAULT NULL,
  `z` MEDIUMINT DEFAULT NULL,
  `exp` BIGINT UNSIGNED DEFAULT 0,
  `expBeforeDeath` BIGINT UNSIGNED DEFAULT 0,
  `sp` INT UNSIGNED NOT NULL DEFAULT 0,
  `karma` INT UNSIGNED DEFAULT NULL,
  `fame` MEDIUMINT UNSIGNED NOT NULL DEFAULT 0,
  `pvpkills` SMALLINT UNSIGNED DEFAULT NULL,
  `pkkills` SMALLINT UNSIGNED DEFAULT NULL,
  `clanid` INT UNSIGNED DEFAULT NULL,
  `race` TINYINT UNSIGNED DEFAULT NULL,
  `classid` TINYINT UNSIGNED DEFAULT NULL,
  `base_class` TINYINT UNSIGNED DEFAULT NULL,
  `transform_id` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  `deletetime` BIGINT DEFAULT NULL,
  `cancraft` TINYINT UNSIGNED DEFAULT NULL,
  `title` VARCHAR(16) DEFAULT NULL,
  `accesslevel` MEDIUMINT DEFAULT 0,
  `online` TINYINT UNSIGNED DEFAULT NULL,
  `onlinetime` INT DEFAULT NULL,
  `char_slot` TINYINT UNSIGNED DEFAULT NULL,
  `newbie` MEDIUMINT UNSIGNED DEFAULT 1,
  `lastAccess` BIGINT UNSIGNED DEFAULT NULL,
  `clan_privs` MEDIUMINT UNSIGNED DEFAULT 0,
  `wantspeace` TINYINT UNSIGNED DEFAULT 0,
  `isin7sdungeon` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `in_jail` TINYINT UNSIGNED DEFAULT 0,
  `jail_timer` INT UNSIGNED DEFAULT 0,
  `banchat_timer` INT UNSIGNED DEFAULT 0,
  `nobless` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `subpledge` SMALLINT NOT NULL DEFAULT 0,
  `pledge_rank` MEDIUMINT NOT NULL DEFAULT 0,
  `lvl_joined_academy` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `apprentice` INT UNSIGNED NOT NULL DEFAULT 0,
  `sponsor` INT UNSIGNED NOT NULL DEFAULT 0,
  `varka_ketra_ally` TINYINT NOT NULL DEFAULT 0,
  `clan_join_expiry_time` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `clan_create_expiry_time` BIGINT UNSIGNED NOT NULL DEFAULT 0,
  `charViP` TINYINT UNSIGNED NOT NULL DEFAULT 0,
  `death_penalty_level` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  `trust_level` INT UNSIGNED NOT NULL DEFAULT 0,
  `bookmarkslot` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  `vitality_points` SMALLINT UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`charId`),
  KEY `clanid` (`clanid`)
) DEFAULT CHARSET=utf8;