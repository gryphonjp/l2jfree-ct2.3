DROP TABLE IF EXISTS `castle_door`;
CREATE TABLE `castle_door` (
  `castleId` TINYINT(1) NOT NULL,
  `id` INT NOT NULL,
  `name` VARCHAR(30) NOT NULL,
  `x` INT NOT NULL,
  `y` INT NOT NULL,
  `z` INT NOT NULL,
  `range_xmin` INT NOT NULL DEFAULT 0,
  `range_ymin` INT NOT NULL DEFAULT 0,
  `range_zmin` INT NOT NULL DEFAULT 0,
  `range_xmax` INT NOT NULL DEFAULT 0,
  `range_ymax` INT NOT NULL DEFAULT 0,
  `range_zmax` INT NOT NULL DEFAULT 0,
  `hp` INT NOT NULL,
  `pDef` INT NOT NULL,
  `mDef` INT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`castleId`)
) DEFAULT CHARSET=utf8;

INSERT INTO `castle_door` VALUES 
(1,19210001,'Gludio_outer_001',-18481,113065,-2774,-18481,113058,-2799,-18350,113072,-2479,316500,644,518),
(1,19210002,'Gludio_outer_002',-18219,113065,-2774,-18351,113058,-2799,-18220,113072,-2479,316500,644,518),
(1,19210003,'Gludio_wall_001',-19912,111381,-2861,-19977,111082,-2922,-19848,111682,-2462,678840,837,674),
(1,19210004,'Gludio_wall_002',-16586,111382,-2880,-16650,111082,-2922,-16522,111682,-2462,678840,837,674),
(1,19210005,'Gludio_inner_001',-18244,110520,-2518,-18244,110514,-2548,-18113,110526,-2293,158250,644,518),
(1,19210006,'Gludio_inner_002',-17981,110520,-2518,-18113,110514,-2548,-17982,110526,-2293,158250,644,518),
(1,19210007,'Gludio_small_002',-19235,108276,-2389,-19333,108276,-2408,-19235,108277,-2280,158250,644,518),
(1,19210008,'Gludio_station',-18997,108062,-2038,-19003,108057,-2069,-18991,108208,-1819,158250,644,518),
(1,19210009,'Gludio_small_001',-17939,107109,-2387,-17940,107011,-2408,-17939,107109,-2280,158250,644,518),
(2,20220001,'Dion_outer_001',22441,156684,-2968,22302,156678,-2994,22450,156692,-2674,316500,644,518),
(2,20220002,'Dion_outer_002',22179,156684,-2968,22171,156678,-2994,22318,156692,-2674,316500,644,518),
(2,20220003,'Dion_wall_001',23873,158368,-3055,23809,158060,-3116,23937,158676,-2656,678840,837,674),
(2,20220004,'Dion_wall_002',20546,158368,-3074,20483,158060,-3116,20611,158676,-2656,678840,837,674),
(2,20220005,'Dion_inner_001',22205,159230,-2712,22067,159224,-2735,22213,159236,-2480,158250,644,518),
(2,20220006,'Dion_inner_002',21943,159230,-2712,21935,159224,-2735,22082,159236,-2480,158250,644,518),
(2,20220007,'Dion_small_001',23194,161470,-2583,23187,161470,-2602,23300,161472,-2474,158250,644,518),
(2,20220008,'Dion_station',22905,161547,-2232,22900,161535,-2258,22912,161702,-2008,158250,644,518),
(2,20220009,'Dion_small_002',21899,162738,-2483,21899,162629,-2602,21899,162744,-2474,158250,644,518),
(3,23220001,'Giran_outer_001',112857,144729,-2841,112850,144720,-2864,112864,144867,-2544,316500,644,518),
(3,23220002,'Giran_outer_002',112857,144990,-2841,112850,144852,-2864,112864,144997,-2544,316500,644,518),
(3,23220003,'Giran_wall_001',114540,143296,-2928,114232,143232,-2991,114848,143360,-2531,678840,837,674),
(3,23220004,'Giran_wall_002',114540,146623,-2945,114232,146559,-2989,114848,146687,-2531,678840,837,674),
(3,23220005,'Giran_inner_001',115402,144965,-2585,115396,144956,-2607,115408,145104,-2352,158250,644,518),
(3,23220006,'Giran_inner_002',115402,145227,-2585,115396,145088,-2607,115408,145236,-2352,158250,644,518),
(3,23220007,'Giran_small_001',117643,143974,-2456,117643,143868,-2475,117643,143981,-2347,158250,644,518),
(3,23220008,'Giran_station',117720,144242,-2105,117708,144236,-2129,117873,144248,-1879,158250,644,518),
(3,23220009,'Giran_small_002',118811,145270,-2454,118804,145270,-2475,118917,145270,-2347,158250,644,518),
(4,22190001,'Oren_outer_001',78925,36824,-2568,78918,36816,-2591,78932,36959,-2271,316500,644,518),
(4,22190002,'Oren_outer_002',78925,37085,-2568,78918,36949,-2591,78932,37092,-2271,316500,644,518),
(4,22190003,'Oren_wall_001',80616,35391,-2655,80316,35328,-2716,80924,35456,-2256,678840,837,674),
(4,22190004,'Oren_wall_002',80616,38718,-2674,80308,38718,-2716,80616,38782,-2256,678840,837,674),
(4,22190005,'Oren_inner_001',81470,37060,-2312,81464,37060,-2335,81470,37201,-2180,158250,644,518),
(4,22190006,'Oren_inner_002',81470,37322,-2312,81464,37181,-2335,81476,37332,-2180,158250,644,518),
(4,22190007,'Oren_small_001',83719,36069,-2202,83719,35964,-2202,83719,36076,-2014,158250,644,518),
(4,22190008,'Oren_station',83796,36337,-1832,83784,36331,-1856,83950,36343,-1606,158250,644,518),
(4,22190009,'Oren_small_002',84887,37365,-2181,84880,37365,-2202,84992,37365,-2074,158250,644,518),
(5,24180001,'Aden_outer_001',147278,8483,-474,147277,8474,-496,147464,8492,-1,339420,837,674),
(5,24180002,'Aden_outer_002',147634,8483,-474,147448,8474,-496,147635,8492,-1,339420,837,674),
(5,24180003,'Aden_wall_in',146770,6977,-460,146440,6895,-496,147064,7105,-391,678840,837,674),
(5,24180004,'Aden_inner_002',147341,6210,-405,147341,6202,-430,147462,6220,-140,84855,837,674),
(5,24180005,'Aden_inner_001',147571,6210,-430,147450,6202,-430,147572,6220,-140,84855,837,674),
(5,24180006,'Aden_wall_left',145200,5504,-726,144863,5119,-752,145537,5889,-187,678840,837,674),
(5,24180007,'Aden_left_004',146607,5384,-20,146600,5318,-45,146614,5386,140,113151,837,674),
(5,24180008,'Aden_left_003',146606,5262,-20,146600,5262,-45,146614,5329,140,113151,837,674),
(5,24180009,'Aden_right_003',148324,5384,-20,148317,5319,-44,148331,5386,140,113151,837,674),
(5,24180010,'Aden_right_004',148323,5262,-20,148317,5261,-44,148317,5328,140,113151,837,674),
(5,24180011,'Aden_wall_right',149711,5503,-726,149887,5119,-752,150049,5889,-197,678840,837,674),
(5,24180012,'Aden_left_002',146001,4721,-402,145993,4601,-427,146011,4722,-98,113151,837,674),
(5,24180013,'Aden_left_001',146001,4493,-402,145993,4493,-427,146011,4613,-98,113151,837,674),
(5,24180014,'Aden_right_001',148908,4721,-402,148900,4602,-427,148918,4723,-98,113151,837,674),
(5,24180015,'Aden_right_002',148908,4493,-402,148900,4492,-427,148918,4613,-98,113151,837,674),
(5,24180016,'Aden_terrace',147511,2240,180,147398,2243,201,147514,2245,434,113151,837,674),
(5,24180018,'Aden_side_door2',150044,3783,-80,150040,3776,-102,150056,3904,145,678840,837,674),
(5,24180019,'Aden_side_door1',149208,3077,1260,149209,3072,1235,149217,3200,1478,678840,837,674),
(5,24180020,'Aden_side_door4',144859,3783,-80,144848,3775,-102,144881,3902,145,678840,837,674),
(5,24180021,'Aden_side_door3',145694,3076,1259,145688,3072,1234,145712,3200,1477,678840,837,674),
(6,23250001,'Innadril_outer_002',116392,245462,-1064,116254,245456,-1090,116400,245470,-770,316500,644,518),
(6,23250002,'Innadril_outer_001',116130,245462,-1064,116123,245456,-1090,116269,245470,-770,316500,644,518),
(6,23250003,'Innadril_wall_001',117824,247146,-1151,117745,246831,-1212,117911,247468,-752,678840,837,674),
(6,23250004,'Innadril_wall_002',114497,247146,-1170,114417,246834,-1212,114578,247462,-752,678840,837,674),
(6,23250005,'Innadril_inner_002',116155,248008,-808,116019,248002,-831,116164,248014,-576,158250,644,518),
(6,23250006,'Innadril_inner_001',115894,248008,-808,115886,248002,-831,116033,248014,-576,158250,644,518),
(6,23250007,'Innadril_small_001',117145,250249,-679,117139,250249,-698,117250,250251,-570,158250,644,518),
(6,23250008,'Innadril_station',116856,250326,-328,116850,250315,-354,116863,250479,-104,158250,644,518),
(6,23250009,'Innadril_small_002',115849,251516,-677,115850,251412,-698,115852,251524,570,158250,644,518),
(7,24160009,'Goddard_outer_002',147642,-45571,-1945,147448,-45584,-2116,147664,-45553,-1866,316500,644,518),
(7,24160010,'Goddard_outer_001',147290,-45571,-1945,147272,-45584,-2116,147480,-45552,-1866,316500,644,518),
(7,24160011,'Goddard_side_door_001',149144,-48368,-2223,149130,-48384,-2411,149160,-48098,-2161,158250,644,518),
(7,24160012,'Goddard_side_door_002',145793,-48368,-2223,145779,-48385,-2411,145811,-48097,-2161,158250,644,518),
(7,24160013,'Goddard_side_door_003',148998,-47795,-1511,148940,-47890,-1625,149056,-47698,-1375,316500,644,518),
(7,24160014,'Goddard_side_door_004',145931,-47794,-1511,145873,-47890,-1625,145989,-47698,-1375,316500,644,518),
(7,24160015,'Goddard_station_001',148750,-49120,-588,148650,-49143,-715,148866,-49092,-465,158250,644,518),
(7,24160016,'Goddard_station_002',146180,-49121,-588,146080,-49144,-715,146296,-49093,-465,158250,644,518),
(7,24160017,'Goddard_ctr_001',150248,-48577,-1659,150147,-48600,-1775,150364,-48549,-1525,39562.5,644,518),
(7,24160018,'Goddard_ctr_002',144682,-48577,-1659,144581,-48600,-1775,144798,-48549,-1525,39562.5,644,518),
(7,24160019,'Goddard_inner_001',148751,-48603,-2290,148735,-48680,-2414,148764,-48528,-2164,678840,644,518),
(7,24160020,'Goddard_inner_002',146180,-48603,-2290,146164,-48680,-2414,146193,-48528,-2164,678840,644,518),
(7,24160021,'Goddard_wall_001',150106,-47523,-2289,149636,-47958,-2573,150734,-46931,-2323,678840,837,674),
(7,24160022,'Goddard_wall_002',144824,-47522,-2289,144480,-47958,-2572,145286,-46922,-2322,678840,837,674),
(7,24160023,'Goddard_inner_003',147464,-46200,-1987,147366,-46218,-2113,147562,-46171,-1863,678840,644,518),
  -- C5 castles, Z-coordinates unchecked
(8,20160001,'Rune_outer_001',18492,-49243,-1215,18458,-49337,-1251,18507,-49136,-700,339420,837,674),
(8,20160002,'Rune_outer_002',18491,-49069,-1214,18458,-49167,-1251,18506,-48968,-700,339420,837,674),
(8,20160003,'Rune_inner_001',15775,-49202,-1059,15752,-49264,-1088,15785,-49136,-700,253200,837,674),
(8,20160004,'Rune_inner_002',15776,-49101,-1059,15752,-49168,-1088,15785,-49039,-700,253200,837,674),
(8,20160005,'Rune_inner_003',15352,-49149,-1057,15320,-49268,-1093,15361,-49027,-700,253200,837,674),
(8,20160006,'Rune_inner_004',12866,-51123,-1087,12746,-51140,-1115,12984,-51096,-800,253200,837,674),
(8,20160007,'Rune_wall_001',17162,-51449,-1084,16996,-51780,-1121,17521,-51277,-641,253200,837,674),
(8,20160008,'Rune_wall_002',17163,-46854,-1089,16983,-47020,-1121,17522,-46522,-641,253200,837,674),
(8,20160009,'Rune_wall_003',13793,-51445,-1072,13526,-52034,-1121,14216,-51354,-353,253200,837,674),
(9,22130001,'Schutt_outer_001',77638,-149643,-353,77539,-149648,-381,77754,-149616,-131,316500,837,674),
(9,22130002,'Schutt_outer_002',77476,-149643,-353,77362,-149649,-381,77571,-149616,-131,316500,837,674),
(9,22130003,'Schutt_inner_001',77550,-150256,-347,77456,-150282,-376,77653,-150235,-126,253200,837,674),
(9,22130004,'Schutt_wall_001',79860,-151728,-652,79730,-152022,-840,80861,-150985,-590,678840,837,674),
(9,22130005,'Schutt_wall_002',75262,-151692,-652,74229,-152016,-839,75377,-150961,-589,678840,837,674),
(9,22130006,'Schutt_side_door_001',79240,-152301,-648,79220,-152449,-676,79250,-152162,-426,253200,837,674),
(9,22130007,'Schutt_side_door_002',75889,-152297,-648,75869,-152449,-676,75902,-152162,-426,253200,837,674),
(9,22130008,'Schutt_side_door_003',79088,-151856,126,79065,-151955,97,79147,-151762,347,253200,837,674),
(9,22130009,'Schutt_side_door_004',76020,-151856,126,75964,-151954,97,76079,-151763,347,253200,837,674),
(9,22130010,'Schutt_ctr_001',80334,-152640,-12,80238,-152664,-43,80454,-152613,207,253200,837,674),
(9,22130011,'Schutt_ctr_002',74768,-152639,-12,74672,-152664,-43,74888,-152613,207,253200,837,674),
(9,22130012,'Schutt_inner_001',78849,-152663,-652,78826,-152745,-682,78855,-152592,-432,253200,837,674),
(9,22130013,'Schutt_inner_002',76262,-152667,-652,76255,-152744,-682,76284,-152592,-432,253200,837,674),
(9,22130014,'Schutt_station_001',78844,-153186,1055,78740,-153207,1017,78957,-153156,1267,253200,837,674),
(9,22130015,'Schutt_station_002',76267,-153181,1055,76170,-153208,1017,76387,-153157,1267,253200,837,674);
