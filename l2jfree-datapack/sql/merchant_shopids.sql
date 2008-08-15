-- ---------------------------
-- Table structure for table `merchant_shopids`
-- ---------------------------
DROP TABLE IF EXISTS `merchant_shopids`;
CREATE TABLE `merchant_shopids` (
  `shop_id` decimal(9,0) NOT NULL DEFAULT '0',
  `npc_id` varchar(9) DEFAULT NULL,
  PRIMARY KEY (`shop_id`)
) DEFAULT CHARSET=utf8;

-- General Shops
INSERT INTO merchant_shopids VALUES
(3000100,'30001'),
(3000101,'30001'),
(3000200,'30002'),
(3000201,'30002'),
(3000300,'30003'),
(3000301,'30003'),
(3000400,'30004'),
(3004700,'30047'),
(3006000,'30060'),
(3006001,'30060'),
(3006002,'30060'),
(3006003,'30060'),
(3006100,'30061'),
(3006101,'30061'),
(3006102,'30061'),
(3006103,'30061'),
(3006200,'30062'),
(3006201,'30062'),
(3006202,'30062'),
(3006300,'30063'),
(3006301,'30063'),
(3007800,'30078'),
(3008100,'30081'),
(3008101,'30081'),
(3008200,'30082'),
(3008201,'30082'),
(3008202,'30082'),
(3008400,'30084'),
(3008401,'30084'),
(3008500,'30085'),
(3008501,'30085'),
(3008700,'30087'),
(3008701,'30087'),
(3008800,'30088'),
(3008801,'30088'),
(3009000,'30090'),
(3009001,'30090'),
(3009100,'30091'),
(3009101,'30091'),
(3009300,'30093'),
(3009400,'30094'),
(3009401,'30094'),
(3009402,'30094'),
(3013500,'30135'),
(3013501,'30135'),
(3013600,'30136'),
(3013601,'30136'),
(3013700,'30137'),
(3013800,'30138'),
(3013801,'30138'),
(3014700,'30147'),
(3014701,'30147'),
(3014800,'30148'),
(3014801,'30148'),
(3014900,'30149'),
(3014901,'30149'),
(3015000,'30150'),
(3016300,'30163'),
(3016400,'30164'),
(3016500,'30165'),
(3016501,'30165'),
(3016600,'30166'),
(3016601,'30166'),
(3017800,'30178'),
(3017801,'30178'),
(3017802,'30178'),
(3017803,'30178'),
(3017900,'30179'),
(3017901,'30179'),
(3017902,'30179'),
(3017903,'30179'),
(3018000,'30180'),
(3018001,'30180'),
(3018100,'30181'),
(3018101,'30181'),
(3018102,'30181'),
(3020700,'30207'),
(3020701,'30207'),
(3020702,'30207'),
(3020703,'30207'),
(3020800,'30208'),
(3020801,'30208'),
(3020802,'30208'),
(3020803,'30208'),
(3020900,'30209'),
(3023000,'30230'),
(3023001,'30230'),
(3023100,'30231'),
(3023101,'30231'),
(3025300,'30253'),
(3025301,'30253'),
(3025302,'30253'),
(3025303,'30253'),
(3025400,'30254'),
(3025401,'30254'),
(3029400,'30294'),
(3029401,'30294'),
(3030100,'30301'),
(3030101,'30301'),
(3031300,'30313'),
(3031400,'30314'),
(3031401,'30314'),
(3031402,'30314'),
(3031500,'30315'),
(3032100,'30321'),
(3032101,'30321'),
(3032102,'30321'),
(3032103,'30321'),
(3038700,'30387'),
(3042000,'30420'),
(3043600,'30436'),
(3043700,'30437'),
(3051600,'30516'),
(3051601,'30516'),
(3051700,'30517'),
(3051701,'30517'),
(3051800,'30518'),
(3051900,'30519'),
(3051901,'30519'),
(3055800,'30558'),
(3055801,'30558'),
(3055900,'30559'),
(3055901,'30559'),
(3056000,'30560'),
(3056001,'30560'),
(3056100,'30561'),
(3068400,'30684'),
(3068401,'30684'),
(3073100,'30731'),
(3082700,'30827'),
(3082800,'30828'),
(3082900,'30829'),
(3083000,'30830'),
(3083100,'30831'),
(3083400,'30834'),
(3083401,'30834'),
(3083700,'30837'),
(3083701,'30837'),
(3083800,'30838'),
(3083801,'30838'),
(3083900,'30839'),
(3084000,'30840'),
(3084100,'30841'),
(3084101,'30841'),
(3084200,'30842'),
(3084201,'30842'),
(3086900,'30869'),
(3087900,'30879'),
(3089000,'30890'),
(3089001,'30890'),
(3089002,'30890'),
(3089003,'30890'),
(3089100,'30891'),
(3089101,'30891'),
(3089102,'30891'),
(3089103,'30891'),
(3089200,'30892'),
(3089201,'30892'),
(3089202,'30892'),
(3089300,'30893'),
(3089301,'30893'),
(3104400,'31044'),
(3104500,'31045'),
(3106700,'31067'),
(3125600,'31256'),
(3125601,'31256'),
(3125700,'31257'),
(3125701,'31257'),
(3125800,'31258'),
(3125801,'31258'),
(3125900,'31259'),
(3125901,'31259'),
(3126000,'31260'),
(3126100,'31261'),
(3126200,'31262'),
(3126300,'31263'),
(3126301,'31263'),
(3126500,'31265'),
(3127300,'31273'),
(3127400,'31274'),
(3128400,'31284'),
(3129100,'31291'),
(3130000,'31300'),
(3130001,'31300'),
(3130100,'31301'),
(3130101,'31301'),
(3130200,'31302'),
(3130201,'31302'),
(3130300,'31303'),
(3130301,'31303'),
(3130400,'31304'),
(3130500,'31305'),
(3130600,'31306'),
(3130700,'31307'),
(3130701,'31307'),
(3130900,'31309'),
(3131800,'31318'),
(3131900,'31319'),
(3133800,'31338'),
(3133900,'31339'),
(3135100,'31351'),
(3136600,'31366'),
(3138600,'31386'),
(3141300,'31413'),
(3141400,'31414'),
(3141500,'31415'),
(3141600,'31416'),
(3141700,'31417'),
(3141800,'31418'),
(3141900,'31419'),
(3142000,'31420'),
(3142100,'31421'),
(3142200,'31422'),
(3142300,'31423'),
(3142400,'31424'),
(3142500,'31425'),
(3142600,'31426'),
(3142700,'31427'),
(3142800,'31428'),
(3142900,'31429'),
(3143000,'31430'),
(3143100,'31431'),
(3143200,'31432'),
(3143300,'31433'),
(3143400,'31434'),
(3143500,'31435'),
(3143600,'31436'),
(3143700,'31437'),
(3143800,'31438'),
(3143900,'31439'),
(3144000,'31440'),
(3144100,'31441'),
(3144200,'31442'),
(3144300,'31443'),
(3144400,'31444'),
(3144500,'31445'),
(3166600,'31666'),
(3166700,'31667'),
(3166800,'31668'),
(3166900,'31669'),
(3167000,'31670'),
(3194500,'31945'),
(3194501,'31945'),
(3194502,'31945'),
(3194503,'31945'),
(3194600,'31946'),
(3194601,'31946'),
(3194602,'31946'),
(3194603,'31946'),
(3194700,'31947'),
(3194701,'31947'),
(3194702,'31947'),
(3194703,'31947'),
(3194800,'31948'),
(3194801,'31948'),
(3194802,'31948'),
(3194803,'31948'),
(3194900,'31949'),
(3194901,'31949'),
(3195000,'31950'),
(3195001,'31950'),
(3195100,'31951'),
(3195200,'31952'),
(3195201,'31952'),
(3195400,'31954'),
(3196200,'31962'),
(3196300,'31963'),
(3197300,'31973'),
(3198000,'31980'),
(3210500,'32105'),
(3210600,'32106'),
(3216400,'32164'),
(3216401,'32164'),
(3216500,'32165'),
(3216501,'32165'),
(3216600,'32166'),
(3216700,'32167'),
(3216800,'32168'),
(3216900,'32169'),
(382,'31380'),
(383,'31373'),

-- Mercenary Managers
(351021,'35102'),
(351441,'35144'),
(351861,'35186'),
(352281,'35228'),
(352761,'35276'),
(353181,'35318'),
(353651,'35365'),

-- Fishermens
(3156200,'31562'),
(3156300,'31563'),
(3156400,'31564'),
(3156500,'31565'),
(3156600,'31566'),
(3156700,'31567'),
(3156800,'31568'),
(3156900,'31569'),
(3157000,'31570'),
(3157100,'31571'),
(3157200,'31572'),
(3157300,'31573'),
(3157400,'31574'),
(3157500,'31575'),
(3157600,'31576'),
(3157700,'31577'),
(3157800,'31578'),
(3157900,'31579'),
(3169600,'31696'),
(3169700,'31697'),
(3198900,'31989'),
(3234800,'32348'),

-- GM Shops
(1001,'gm'),
(1002,'gm'),
(1003,'gm'),
(1004,'gm'),
(1005,'gm'),
(1006,'gm'),
(1007,'gm'),
(1008,'gm'),
(1009,'gm'),
(1010,'gm'),
(1011,'gm'),
(1012,'gm'),
(1013,'gm'),
(1014,'gm'),
(1015,'gm'),
(1020,'gm'),
(2011,'gm'),
(2012,'gm'),
(2013,'gm'),
(2014,'gm'),
(2015,'gm'),
(3001,'gm'),
(3002,'gm'),
(3003,'gm'),
(9001,'gm'),
(9002,'gm'),
(9003,'gm'),
(9004,'gm'),
(9005,'gm'),
(9006,'gm'),
(9007,'gm'),
(9008,'gm'),
(9009,'gm'),
(9010,'gm'),
(9011,'gm'),
(9012,'gm'),
(9013,'gm'),
(9014,'gm'),
(9015,'gm'),
(9016,'gm'),
(9017,'gm'),
(9018,'gm'),
(9019,'gm'),
(9020,'gm'),
(9021,'gm'),
(9022,'gm'),
(9023,'gm'),
(9024,'gm'),
(9025,'gm'),
(9026,'gm'),
(9027,'gm'),
(9028,'gm'),
(9029,'gm'),
(9030,'gm'),
(9031,'gm'),
(9032,'gm'),
(9033,'gm'),
(9034,'gm'),
(9035,'gm'),
(9036,'gm'),
(9037,'gm'),
(9038,'gm'),
(9039,'gm'),
(9040,'gm'),
(9041,'gm'),
(9042,'gm'),
(9043,'gm'),
(9044,'gm'),
(9045,'gm'),
(9046,'gm'),
(9047,'gm'),
(9048,'gm'),
(9049,'gm'),
(9050,'gm'),
(9051,'gm'),
(9052,'gm'),
(9053,'gm'),
(9054,'gm'),
(9055,'gm'),
(9056,'gm'),
(9057,'gm'),
(9058,'gm'),
(9059,'gm'),
(9060,'gm'),
(9061,'gm'),
(9062,'gm'),
(9063,'gm'),
(9064,'gm'),
(9065,'gm'),
(9066,'gm'),
(9067,'gm'),
(9068,'gm'),
(9069,'gm'),
(9070,'gm'),
(9071,'gm'),
(9072,'gm'),
(9073,'gm'),
(9074,'gm'),
(9075,'gm'),
(9076,'gm'),
(9077,'gm'),
(9078,'gm'),
(9079,'gm'),
(9080,'gm'),
(9081,'gm'),
(9082,'gm'),
(9083,'gm'),
(9084,'gm'),
(9085,'gm'),
(9086,'gm'),
(9087,'gm'),
(9088,'gm'),
(9089,'gm'),
(9090,'gm'),
(9091,'gm'),
(9092,'gm'),
(9093,'gm'),
(9094,'gm'),
(9095,'gm'),
(9096,'gm'),
(9097,'gm'),
(9098,'gm'),
(9099,'gm'),
(9100,'gm'),
(9101,'gm'),
(9102,'gm'),
(9103,'gm'),
(9104,'gm'),
(9105,'gm'),
(9106,'gm'),
(9107,'gm'),
(9108,'gm'),
(9109,'gm'),
(9110,'gm'),
(9111,'gm'),
(9112,'gm'),
(9113,'gm'),
(9114,'gm'),
(9115,'gm'),
(9116,'gm'),
(9117,'gm'),
(9118,'gm'),
(9119,'gm'),
(9120,'gm'),
(9121,'gm'),
(9122,'gm'),
(9123,'gm'),
(9124,'gm'),
(9125,'gm'),
(9126,'gm'),
(9127,'gm'),
(9128,'gm'),
(9148,'gm'),
(9149,'gm'),
(9150,'gm'),
(30040,'gm'),
(30041,'gm'),
(30042,'gm'),
(30043,'gm'),
(30044,'gm'),
(30045,'gm'),
(30046,'gm'),
(30047,'gm'),
(30048,'gm'),
(30049,'gm'),
(30050,'gm'),
(30051,'gm'),
(30052,'gm'),
(30053,'gm'),
(30054,'gm'),
(30055,'gm'),
(30056,'gm'),
(30057,'gm'),
(30058,'gm'),
(30059,'gm'),
(71021,'gm'),
(71022,'gm'),
(71023,'gm'),
(71024,'gm'),
(71025,'gm'),
(71026,'gm'),
(71027,'gm'),
(71028,'gm'),
(71029,'gm'),
(71030,'gm'),
(300523,'gm'),
(300524,'gm'),
(300525,'gm'),
(300526,'gm'),
(300527,'gm'),
(300528,'gm'),
(300529,'gm'),
(300530,'gm'),
(300531,'gm'),
(300532,'gm'),
(300533,'gm'),
(300534,'gm'),
(300535,'gm'),
(300536,'gm'),
(300537,'gm'),
(300538,'gm'),
(300539,'gm'),
(300540,'gm'),
(300541,'gm'),
(300522,'gm'),
(300511,'gm'),
(300510,'gm'),
(300410,'gm'),
(300542,'gm'),
(71031,'gm'),
(71032,'gm'),
(9151,'gm'),
(9152,'gm'),
(9153,'gm'),
(9154,'gm'),
(9155,'gm'),
(9156,'gm'),
(9157,'gm'),
(9158,'gm'),
(9159,'gm'),
(9160,'gm'),
(9161,'gm'),

--
-- Castle 
--
(335103, '35103'),
(335145, '35145'),
(335187, '35187'),
(335229, '35229'),
(335230, '35230'),
(335231, '35231'),
(335277, '35277'),
(335319, '35319'),
(335366, '35366'),
(335512, '35512'),
(335558, '35558'),
(335644, '35644'),
(335645, '35645'),

--
-- Castles Item creation
--
(351001,'35100'),
(351421,'35142'),
(351841,'35184'),
(352261,'35226'),
(352741,'35274'),
(353161,'35316'),
(353631,'35363'),
(355091,'35509'),
(355551,'35555'),

--
-- Clan Halls Item creation
--
(135445, '35445'),
(235445, '35445'),
(335445, '35445'),
(135453, '35453'),
(235453, '35453'),
(335453, '35453'),
(135455, '35455'),
(235455, '35455'),
(335455, '35455'),
(135451, '35451'),
(235451, '35451'),
(335451, '35451'),
(135457, '35457'),
(235457, '35457'),
(335457, '35457'),
(135459, '35459'),
(235459, '35459'),
(335459, '35459'),
(135383, '35383'),
(235383, '35383'),
(335383, '35383'),
(135398, '35398'),
(235398, '35398'),
(335398, '35398'),
(135400, '35400'),
(235400, '35400'),
(335400, '35400'),
(135392, '35392'),
(235392, '35392'),
(335392, '35392'),
(135394, '35394'),
(235394, '35394'),
(335394, '35394'),
(135396, '35396'),
(235396, '35396'),
(335396, '35396'),
(135384, '35384'),
(235384, '35384'),
(335384, '35384'),
(135390, '35390'),
(235390, '35390'),
(335390, '35390'),
(135386, '35386'),
(235386, '35386'),
(335386, '35386'),
(135388, '35388'),
(235388, '35388'),
(335388, '35388'),
(135407, '35407'),
(235407, '35407'),
(335407, '35407'),
(135403, '35403'),
(235403, '35403'),
(335403, '35403'),
(135405, '35405'),
(235405, '35405'),
(335405, '35405'),
(135421, '35421'),
(235421, '35421'),
(335421, '35421'),
(135439, '35439'),
(235439, '35439'),
(335439, '35439'),
(135441, '35441'),
(235441, '35441'),
(335441, '35441'),
(135443, '35443'),
(235443, '35443'),
(335443, '35443'),
(135447, '35447'),
(235447, '35447'),
(335447, '35447'),
(135449, '35449'),
(235449, '35449'),
(335449, '35449'),
(135467, '35467'),
(235467, '35467'),
(335467, '35467'),
(135465, '35465'),
(235465, '35465'),
(335465, '35465'),
(135463, '35463'),
(235463, '35463'),
(335463, '35463'),
(135461, '35461'),
(235461, '35461'),
(335461, '35461'),
(335566, '35566'),
(235566, '35566'),
(135566, '35566'),
(335568, '35568'),
(235568, '35568'),
(135568, '35568'),
(335570, '35570'),
(235570, '35570'),
(135570, '35570'),
(335572, '35572'),
(235572, '35572'),
(135572, '35572'),
(335574, '35574'),
(235574, '35574'),
(135574, '35574'),
(335576, '35576'),
(235576, '35576'),
(135576, '35576'),
(335578, '35578'),
(235578, '35578'),
(135578, '35578'),
(235580, '35580'),
(135580, '35580'),
(335580, '35580'),
(335582, '35582'),
(235582, '35582'),
(135582, '35582'),
(135584, '35584'),
(235584, '35584'),
(335584, '35584'),
(335586, '35586'),
(135586, '35586'),
(235586, '35586'),
(355111, '35511'),
(355571, '35557');

-- L2J-Free Add-ons

-- GM Shop Addition bySkatershi
INSERT INTO merchant_shopids VALUES
(71040, 'gm'), -- Interlude Spellbooks
(71041, 'gm'), -- Interlude Recipes
(71042, 'gm'), -- CT1/CT1.5 Battle Manuals
(71043, 'gm'), -- CT1 Spellbooks
(71044, 'gm'), -- CT1/CT1.5 Recipes
(71045, 'gm'), -- CT1/CT1.5 Transformation Scrolls
(71046, 'gm'), -- CT1/CT1.5 Shadow Items
(71047, 'gm'), -- CT1/CT1.5 Bracelet
(71048, 'gm'), -- CT1/CT1.5 Special Jewels
(71049, 'gm'), -- CT1/CT1.5 Talisman
(71050, 'gm'), -- CT1/CT1.5 Hair Accessories
(71051, 'gm'), -- CT1.5 Weapons (Monster Only)
(71052, 'gm'), -- CT1.5 Shirt
(71053, 'gm'), -- CT1.5 Forgotten Scrolls
(71054, 'gm'); -- CT1.5 Others