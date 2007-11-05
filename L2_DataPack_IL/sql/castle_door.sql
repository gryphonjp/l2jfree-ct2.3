-- ---------------------------
-- Table structure for `castle_door`
-- ---------------------------
DROP TABLE IF EXISTS `castle_door`;
CREATE TABLE `castle_door` (
  `castleId` INT NOT NULL default 0,
  `id` INT NOT NULL default 0,
  `name` varchar(30) NOT NULL,
  `x` INT NOT NULL default 0,
  `y` INT NOT NULL default 0,
  `z` INT NOT NULL default 0,
  `range_xmin` INT NOT NULL default 0,
  `range_ymin` INT NOT NULL default 0,
  `range_zmin` INT NOT NULL default 0,
  `range_xmax` INT NOT NULL default 0,
  `range_ymax` INT NOT NULL default 0,
  `range_zmax` INT NOT NULL default 0,
  `hp` INT NOT NULL default 0,
  `pDef` INT NOT NULL default 0,
  `mDef` INT NOT NULL default 0,
  PRIMARY KEY (`id`),
  KEY `id` (`castleId`)
) DEFAULT CHARSET=utf8;

INSERT INTO `castle_door` (`castleId`, `id`, `name`, `x`, `y`, `z`, `range_xmin`, `range_ymin`, `range_zmin`, `range_xmax`, `range_ymax`, `range_zmax`, `hp`, `pDef`, `mDef`) VALUES 
  (1,19210001,'Gludio_outer_001',-18481,113065,-2774,-18481,113058,-2799,-18350,113072,-2479,316500,644,518),
  (1,19210002,'Gludio_outer_002',-18219,113065,-2774,-18351,113058,-2799,-18220,113072,-2479,316500,644,518),
  (1,19210003,'Gludio_wall_001',-19912,111381,-2861,-19977,111082,-2922,-19848,111682,-2462,678840,837,674),
  (1,19210004,'Gludio_wall_002',-16586,111382,-2880,-16650,111082,-2922,-16522,111682,-2462,678840,837,674),
  (1,19210005,'Gludio_inner_001',-18244,110520,-2518,-18244,110514,-2548,-18113,110526,-2293,158250,644,518),
  (1,19210006,'Gludio_inner_002',-17981,110520,-2518,-18113,110514,-2548,-17982,110526,-2293,158250,644,518),
  (1,19210007,'Gludio_small_002',-19235,108276,-2389,-19333,108276,-2408,-19235,108277,-2280,158250,644,518),
  (1,19210008,'Gludio_station',-18997,108062,-2038,-19003,108057,-2069,-18991,108208,-1819,158250,644,518),
  (1,19210009,'Gludio_small_001',-17939,107109,-2387,-17940,107011,-2408,-17939,107109,-2280,158250,644,518),
  (2,20220001,'Dion_outer_001',22441,156684,-2968,0,0,0,0,0,0,316500,644,518),
  (2,20220002,'Dion_outer_002',22179,156684,-2968,0,0,0,0,0,0,316500,644,518),
  (2,20220003,'Dion_wall_001',23873,158368,-3055,0,0,0,0,0,0,678840,837,674),
  (2,20220004,'Dion_wall_002',20546,158368,-3074,0,0,0,0,0,0,678840,837,674),
  (2,20220005,'Dion_inner_001',22205,159230,-2712,0,0,0,0,0,0,158250,644,518),
  (2,20220006,'Dion_inner_002',21943,159230,-2712,0,0,0,0,0,0,158250,644,518),
  (2,20220007,'Dion_small_001',23194,161470,-2583,0,0,0,0,0,0,158250,644,518),
  (2,20220008,'Dion_station',22905,161547,-2232,0,0,0,0,0,0,158250,644,518),
  (2,20220009,'Dion_small_002',21899,162737,-2581,0,0,0,0,0,0,158250,644,518),
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
  (6,23250001,'Innadril_outer_002',116392,245462,-1064,0,0,0,0,0,0,316500,644,518),
  (6,23250002,'Innadril_outer_001',116130,245462,-1064,0,0,0,0,0,0,316500,644,518),
  (6,23250003,'Innadril_wall_001',117824,247146,-1151,0,0,0,0,0,0,678840,837,674),
  (6,23250004,'Innadril_wall_002',114497,247146,-1170,0,0,0,0,0,0,678840,837,674),
  (6,23250005,'Innadril_inner_002',116155,248008,-808,0,0,0,0,0,0,158250,644,518),
  (6,23250006,'Innadril_inner_001',115894,248008,-808,0,0,0,0,0,0,158250,644,518),
  (6,23250007,'Innadril_small_001',117145,250249,-679,0,0,0,0,0,0,158250,644,518),
  (6,23250008,'Innadril_station',116856,250326,-328,0,0,0,0,0,0,158250,644,518),
  (6,23250009,'Innadril_small_002',115849,251516,-677,0,0,0,0,0,0,158250,644,518),
  -- Goddard Z-coordinates still unchecked
  (7,24160009,'Goddard_outer_002',147642,-45571,-2116,0,0,0,0,0,0,316500,644,518),
  (7,24160010,'Goddard_outer_001',147290,-45571,-2116,0,0,0,0,0,0,316500,644,518),
  (7,24160011,'Goddard_side_door_001',149143,-48368,-2411,0,0,0,0,0,0,158250,644,518),
  (7,24160012,'Goddard_side_door_002',145792,-48368,-2410,0,0,0,0,0,0,158250,644,518),
  (7,24160013,'Goddard_side_door_003',148997,-47794,-1625,0,0,0,0,0,0,316500,644,518),
  (7,24160014,'Goddard_side_door_004',145930,-47793,-1625,0,0,0,0,0,0,316500,644,518),
  (7,24160015,'Goddard_station_001',148750,-49120,-715,0,0,0,0,0,0,158250,644,518),
  (7,24160016,'Goddard_station_002',146180,-49121,-715,0,0,0,0,0,0,158250,644,518),
  (7,24160017,'Goddard_ctr_001',150248,-48577,-1775,0,0,0,0,0,0,39562.5,644,518),
  (7,24160018,'Goddard_ctr_002',144682,-48577,-1775,0,0,0,0,0,0,39562.5,644,518),
  (7,24160019,'Goddard_inner_001',148751,-48603,-2415,0,0,0,0,0,0,678840,644,518),
  (7,24160020,'Goddard_inner_002',146180,-48603,-2414,0,0,0,0,0,0,678840,644,518),
  (7,24160021,'Goddard_wall_001',150106,-47522,-2572,0,0,0,0,0,0,678840,837,674),
  (7,24160022,'Goddard_wall_002',144824,-47522,-2572,0,0,0,0,0,0,678840,837,674),
  (7,24160023,'Goddard_inner_003',147464,-46200,-2112,0,0,0,0,0,0,678840,644,518),
  -- C5 castles, Z-coordinates unchecked
  (8,20160001,'out_gate',18492,-49243,-1215,0,0,0,0,0,0,316500,837,674),
  (8,20160002,'out_gate',18491,-49069,-1214,0,0,0,0,0,0,316500,837,674),
  (8,20160003,'inner_gate',15775,-49202,-1059,0,0,0,0,0,0,253200,837,674),
  (8,20160004,'inner_gate',15776,-49101,-1059,0,0,0,0,0,0,253200,837,674),
  (8,20160005,'clos_gate',15352,-49149,-1057,0,0,0,0,0,0,253200,837,674),
  (8,20160006,'inner_gate',12866,-51123,-1087,0,0,0,0,0,0,253200,837,674),
  (8,20160007,'cast_wall',17162,-51449,-1084,0,0,0,0,0,0,678840,837,674),
  (8,20160008,'cast_wall',17163,-46854,-1089,0,0,0,0,0,0,678840,837,674),
  (8,20160009,'cast_wall',13793,-51445,-1072,0,0,0,0,0,0,678840,837,674),
  (9,22130001,'out_gate',77638,-149643,-353,0,0,0,0,0,0,316500,837,674),
  (9,22130002,'out_gate',77476,-149643,-353,0,0,0,0,0,0,316500,837,674),
  (9,22130003,'clos_gate',77550,-150256,-347,0,0,0,0,0,0,253200,837,674),
  (9,22130004,'cast_wall',79860,-151728,-652,0,0,0,0,0,0,678840,837,674),
  (9,22130005,'cast_wall',75262,-151692,-652,0,0,0,0,0,0,678840,837,674),
  (9,22130006,'inner_gate',79240,-152301,-648,0,0,0,0,0,0,253200,837,674),
  (9,22130007,'inner_gate',75889,-152297,-648,0,0,0,0,0,0,253200,837,674),
  (9,22130008,'inner_gate',79088,-151856,126,0,0,0,0,0,0,253200,837,674),
  (9,22130009,'inner_gate',76020,-151856,126,0,0,0,0,0,0,253200,837,674),
  (9,22130010,'door',80334,-152640,-12,0,0,0,0,0,0,253200,837,674),
  (9,22130011,'door',74768,-152639,-12,0,0,0,0,0,0,253200,837,674),
  (9,22130012,'clos_gate',78849,-152663,-652,0,0,0,0,0,0,253200,837,674),
  (9,22130013,'clos_gate',76262,-152667,-652,0,0,0,0,0,0,253200,837,674),
  (9,22130014,'door',78844,-153186,1055,0,0,0,0,0,0,253200,837,674),
  (9,22130015,'door',76267,-153181,1055,0,0,0,0,0,0,253200,837,674);