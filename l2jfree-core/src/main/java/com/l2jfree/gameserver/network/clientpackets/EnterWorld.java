/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree.gameserver.network.clientpackets;

import javolution.util.FastList;

import com.l2jfree.Config;
import com.l2jfree.gameserver.Announcements;
import com.l2jfree.gameserver.CoreInfo;
import com.l2jfree.gameserver.SevenSigns;
import com.l2jfree.gameserver.ThreadPoolManager;
import com.l2jfree.gameserver.cache.HtmCache;
import com.l2jfree.gameserver.communitybbs.Manager.RegionBBSManager;
import com.l2jfree.gameserver.communitybbs.Manager.RegionBBSManager.PlayerStateOnCommunity;
import com.l2jfree.gameserver.datatables.GmListTable;
import com.l2jfree.gameserver.datatables.SkillTable;
import com.l2jfree.gameserver.handler.AdminCommandHandler;
import com.l2jfree.gameserver.instancemanager.ClanHallManager;
import com.l2jfree.gameserver.instancemanager.CoupleManager;
import com.l2jfree.gameserver.instancemanager.CrownManager;
import com.l2jfree.gameserver.instancemanager.DimensionalRiftManager;
import com.l2jfree.gameserver.instancemanager.FortManager;
import com.l2jfree.gameserver.instancemanager.FortSiegeManager;
import com.l2jfree.gameserver.instancemanager.InstanceManager;
import com.l2jfree.gameserver.instancemanager.PetitionManager;
import com.l2jfree.gameserver.instancemanager.QuestManager;
import com.l2jfree.gameserver.instancemanager.SiegeManager;
import com.l2jfree.gameserver.model.L2Clan;
import com.l2jfree.gameserver.model.L2ClanMember;
import com.l2jfree.gameserver.model.L2ItemInstance;
import com.l2jfree.gameserver.model.L2ShortCut;
import com.l2jfree.gameserver.model.L2Skill;
import com.l2jfree.gameserver.model.L2World;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfree.gameserver.model.entity.ClanHall;
import com.l2jfree.gameserver.model.entity.Couple;
import com.l2jfree.gameserver.model.entity.Fort;
import com.l2jfree.gameserver.model.entity.FortSiege;
import com.l2jfree.gameserver.model.entity.Hero;
import com.l2jfree.gameserver.model.entity.Siege;
import com.l2jfree.gameserver.model.mapregion.TeleportWhereType;
import com.l2jfree.gameserver.model.olympiad.Olympiad;
import com.l2jfree.gameserver.model.quest.Quest;
import com.l2jfree.gameserver.model.quest.QuestState;
import com.l2jfree.gameserver.model.restriction.ObjectRestrictions;
import com.l2jfree.gameserver.model.restriction.global.GlobalRestrictions;
import com.l2jfree.gameserver.model.zone.L2Zone;
import com.l2jfree.gameserver.network.SystemMessageId;
import com.l2jfree.gameserver.network.serverpackets.Die;
import com.l2jfree.gameserver.network.serverpackets.ExBasicActionList;
import com.l2jfree.gameserver.network.serverpackets.ExGetBookMarkInfoPacket;
import com.l2jfree.gameserver.network.serverpackets.ExStorageMaxCount;
import com.l2jfree.gameserver.network.serverpackets.FriendList;
import com.l2jfree.gameserver.network.serverpackets.HennaInfo;
import com.l2jfree.gameserver.network.serverpackets.ItemList;
import com.l2jfree.gameserver.network.serverpackets.L2GameServerPacket;
import com.l2jfree.gameserver.network.serverpackets.NpcHtmlMessage;
import com.l2jfree.gameserver.network.serverpackets.PledgeShowMemberListAll;
import com.l2jfree.gameserver.network.serverpackets.PledgeShowMemberListUpdate;
import com.l2jfree.gameserver.network.serverpackets.PledgeSkillList;
import com.l2jfree.gameserver.network.serverpackets.PledgeStatusChanged;
import com.l2jfree.gameserver.network.serverpackets.QuestList;
import com.l2jfree.gameserver.network.serverpackets.SSQInfo;
import com.l2jfree.gameserver.network.serverpackets.ShortCutInit;
import com.l2jfree.gameserver.network.serverpackets.ShortCutRegister;
import com.l2jfree.gameserver.network.serverpackets.SkillCoolTime;
import com.l2jfree.gameserver.network.serverpackets.SystemMessage;
import com.l2jfree.gameserver.network.serverpackets.UserInfo;

/**
 * Enter World Packet Handler
 * <p>
 * 0000: 03
 * <p>
 * packet format rev656 cbdddd
 * <p>
 *
 * @version $Revision: 1.16.2.1.2.7 $ $Date: 2005/03/29 23:15:33 $
 */
public class EnterWorld extends L2GameClientPacket
{
	private static final String	_C__03_ENTERWORLD	= "[C] 03 EnterWorld";

	private GameDataQueue		gdq					= null;

	@Override
	protected void readImpl()
	{
		// this is just a trigger packet. it has no content
	}

	@Override
	protected void runImpl()
	{
		L2PcInstance activeChar = getActiveChar();
		if (activeChar == null)
		{
			_log.warn("EnterWorld failed! activeChar is null...");
			getClient().closeNow();
			return;
		}

		// sad that sendPacket is declared as final, so forget about any
		// compatibility with l2j; also do NOT use sendPacket() in this void!!!
		gdq = new GameDataQueue();

		if (Config.GM_EVERYBODY_HAS_ADMIN_RIGHTS && !(activeChar.isGM()))
			activeChar.setAccessLevel(200);

		// restore instance
		if (Config.RESTORE_PLAYER_INSTANCE)
			activeChar.setInstanceId(InstanceManager.getInstance().getPlayerInstance(activeChar.getObjectId()));
		else
		{
			int instanceId = InstanceManager.getInstance().getPlayerInstance(activeChar.getObjectId());
			if (instanceId > 0)
				InstanceManager.getInstance().getInstance(instanceId).removePlayer(activeChar.getObjectId());
		}

		// Restore Vitality
		if (Config.RECOVER_VITALITY_ON_RECONNECT)
			activeChar.restoreVitality();

		if (Config.PLAYER_SPAWN_PROTECTION > 0)
			activeChar.setProtection(true);
		activeChar.spawnMe(activeChar.getX(), activeChar.getY(), activeChar.getZ());

		activeChar.getKnownList().updateKnownObjects();

		gdq.add(new SSQInfo());
		gdq.add(new UserInfo(activeChar));
		gdq.add(new ItemList(activeChar, false));
		activeChar.getMacroses().sendUpdate(gdq);
		gdq.add(new ShortCutInit(activeChar));
		activeChar.sendSkillList(gdq);
		gdq.add(SystemMessageId.WELCOME_TO_LINEAGE);
		if (Config.SERVER_AGE_LIM >= 18 || Config.SERVER_PVP)
			gdq.add(SystemMessageId.ENTERED_ADULTS_ONLY_SERVER);
		else if (Config.SERVER_AGE_LIM >= 15)
			gdq.add(SystemMessageId.ENTERED_COMMON_SERVER);
		else
			gdq.add(SystemMessageId.ENTERED_JUVENILES_SERVER);
		gdq.add(new HennaInfo(activeChar));

		Announcements.getInstance().showAnnouncements(activeChar, gdq);
		SevenSigns.getInstance().sendCurrentPeriodMsg(activeChar, gdq);

		if (activeChar.isGM())
		{
			if (Config.SHOW_GM_LOGIN)
			{
				Announcements.getInstance().announceToAll("GM " + activeChar.getName() + " has logged on.");
			}
			else
			{
				if (Config.GM_STARTUP_INVISIBLE)
					AdminCommandHandler.getInstance().useAdminCommand(activeChar, "admin_invisible");

				if (Config.GM_STARTUP_SILENCE)
					AdminCommandHandler.getInstance().useAdminCommand(activeChar, "admin_silence");
			}

			if (Config.GM_STARTUP_INVULNERABLE)
				AdminCommandHandler.getInstance().useAdminCommand(activeChar, "admin_invul");

			if (Config.GM_NAME_COLOR_ENABLED)
			{
				if (activeChar.getAccessLevel() >= 100)
					activeChar.getAppearance().setNameColor(Config.ADMIN_NAME_COLOR);
				else if (activeChar.getAccessLevel() >= 75)
					activeChar.getAppearance().setNameColor(Config.GM_NAME_COLOR);
			}
			if (Config.GM_TITLE_COLOR_ENABLED)
			{
				if (activeChar.getAccessLevel() >= 100)
					activeChar.getAppearance().setTitleColor(Config.ADMIN_TITLE_COLOR);
				else if (activeChar.getAccessLevel() >= 75)
					activeChar.getAppearance().setTitleColor(Config.GM_TITLE_COLOR);
			}

			if (Config.GM_STARTUP_AUTO_LIST)
				GmListTable.addGm(activeChar, false);
			else
				GmListTable.addGm(activeChar, true);
		}
		else if (activeChar.getClan() != null && activeChar.isClanLeader() && Config.CLAN_LEADER_COLOR_ENABLED
				&& activeChar.getClan().getLevel() >= Config.CLAN_LEADER_COLOR_CLAN_LEVEL)
		{
			if (Config.CLAN_LEADER_COLORED == Config.ClanLeaderColored.name)
				activeChar.getAppearance().setNameColor(Config.CLAN_LEADER_COLOR);
			else
				activeChar.getAppearance().setTitleColor(Config.CLAN_LEADER_COLOR);
		}
		if (activeChar.isCharViP())
		{
			if (Config.CHAR_VIP_COLOR_ENABLED)
				activeChar.getAppearance().setNameColor(Config.CHAR_VIP_COLOR);
		}

		// send user info again .. just like the real client
		gdq.add(new UserInfo(activeChar));

		if (activeChar.getClanId() != 0 && activeChar.getClan() != null)
		{
			gdq.add(new PledgeShowMemberListAll(activeChar.getClan()));
			gdq.add(new PledgeStatusChanged(activeChar.getClan()));

			// Residential skills support
			activeChar.enableResidentialSkills(true);
		}

		if (activeChar.getStatus().getCurrentHp() < 0.5) // is dead
			activeChar.setIsDead(true);
		if (activeChar.isAlikeDead()) // dead or fake dead
			// no broadcast needed since the player will already spawn dead to others
			gdq.add(new Die(activeChar));

		// Init packet sending
		gdq.flush();

		// engage and notify Partner
		if (Config.ALLOW_WEDDING)
		{
			engage(activeChar);
			notifyPartner(activeChar);

			// Check if player is married and remove if necessary Cupid's Bow
			if (!activeChar.isMaried())
			{
				L2ItemInstance item = activeChar.getInventory().getItemByItemId(9140);
				// Remove Cupid's Bow
				if (item != null)
				{
					activeChar.destroyItem("Removing Cupid's Bow", item, activeChar, true);

					// No need to update every item in the inventory
					//activeChar.getInventory().updateDatabase();

					// Log it
					if (_log.isDebugEnabled())
						_log.debug("Character " + activeChar.getName() + " of account " + activeChar.getAccountName() + " got Cupid's Bow removed.");
				}
			}
		}

		activeChar.updateEffectIcons();
		gdq.add(new SkillCoolTime(activeChar));

		Quest.playerEnter(activeChar);
		loadTutorial(activeChar);
		for (Quest quest : QuestManager.getInstance().getAllManagedScripts())
		{
			if (quest != null && quest.getOnEnterWorld())
				quest.notifyEnterWorld(activeChar);
		}

		notifyFriends(activeChar);
		notifyClanMembers(activeChar);
		notifySponsorOrApprentice(activeChar);

		L2Clan clan = activeChar.getClan();
		if (clan != null)
		{
			PledgeSkillList response = new PledgeSkillList(clan);
			L2Skill[] skills = clan.getAllSkills();
			for (L2Skill s : skills)
			{
				if (s == null)
					continue;
				response.addSkill(s.getId(), s.getLevel());
			}
			gdq.add(response);
		}

		gdq.add(new ExStorageMaxCount(activeChar));
		gdq.add(new QuestList(activeChar));

		activeChar.broadcastUserInfo();

		if (Olympiad.getInstance().playerInStadia(activeChar))
		{
			activeChar.doRevive();
			activeChar.teleToLocation(TeleportWhereType.Town);
			activeChar.sendMessage("You have been teleported to the nearest town due to you being in an Olympiad Stadium.");
		}

		activeChar.revalidateZone(true);
		activeChar.sendEtcStatusUpdate();

		if (DimensionalRiftManager.getInstance().checkIfInRiftZone(activeChar.getX(), activeChar.getY(), activeChar.getZ(), true)) // Exclude waiting room
			DimensionalRiftManager.getInstance().teleportToWaitingRoom(activeChar);

		// Wherever these should be?
		gdq.add(new ShortCutInit(activeChar));

		if (Hero.getInstance().getHeroes() != null && Hero.getInstance().getHeroes().containsKey(activeChar.getObjectId()))
			activeChar.setHero(true);

		// Restore character's siege state
		if (activeChar.getClan() != null)
		{
			for (Siege siege : SiegeManager.getInstance().getSieges())
			{
				if (!siege.getIsInProgress())
					continue;
				if (siege.checkIsAttacker(activeChar.getClan()))
					activeChar.setSiegeState((byte) 1);
				else if (siege.checkIsDefender(activeChar.getClan()))
					activeChar.setSiegeState((byte) 2);
			}

			for (FortSiege fsiege : FortSiegeManager.getInstance().getSieges())
			{
				if (!fsiege.getIsInProgress())
					continue;
				if (fsiege.checkIsAttacker(activeChar.getClan()))
					activeChar.setSiegeState((byte) 1);
				else if (fsiege.checkIsDefender(activeChar.getClan()))
					activeChar.setSiegeState((byte) 2);
			}
		}

		//Updating Seal of Strife Buff/Debuff
		if (SevenSigns.getInstance().isSealValidationPeriod())
		{
			int owner = SevenSigns.getInstance().getSealOwner(SevenSigns.SEAL_STRIFE);
			if (owner != SevenSigns.CABAL_NULL)
			{
				int cabal = SevenSigns.getInstance().getPlayerCabal(activeChar);
				if (cabal == owner)
					activeChar.addSkill(SkillTable.getInstance().getInfo(5074, 1), false);
				else if (cabal != SevenSigns.CABAL_NULL)
					activeChar.addSkill(SkillTable.getInstance().getInfo(5075, 1), false);
			}
		}

		for (L2ItemInstance i : activeChar.getInventory().getItems())
			if (i.isTimeLimitedItem())
				i.scheduleLifeTimeTask();

		activeChar.queryGameGuard();

		gdq.add(new FriendList(activeChar));

		if (Config.SHOW_LICENSE)
			CoreInfo.versionInfo(gdq);

		if (Config.SHOW_HTML_NEWBIE && activeChar.getLevel() < Config.LEVEL_HTML_NEWBIE)
		{
			String Newbie_Path = "data/html/newbie.htm";
			if (HtmCache.getInstance().pathExists(Newbie_Path))
			{
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(Newbie_Path);
				html.replace("%name%", activeChar.getName()); // replaces %name%, so you can say like "welcome to the server %name%"
				gdq.add(html);
			}
		}
		else if (Config.SHOW_HTML_GM && activeChar.isGM())
		{
			String Gm_Path = "data/html/gm.htm";
			if (HtmCache.getInstance().pathExists(Gm_Path))
			{
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(Gm_Path);
				html.replace("%name%", activeChar.getName()); // replaces %name%, so you can say like "welcome to the server %name%"
				gdq.add(html);
			}
		}
		else if (Config.SHOW_HTML_WELCOME)
		{
			String Welcome_Path = "data/html/welcome.htm";
			if (HtmCache.getInstance().pathExists(Welcome_Path))
			{
				NpcHtmlMessage html = new NpcHtmlMessage(1);
				html.setFile(Welcome_Path);
				html.replace("%name%", activeChar.getName()); // replaces %name%, so you can say like "welcome to the server %name%"
				gdq.add(html);
			}
		}

		// Resume paused restrictions
		ObjectRestrictions.getInstance().resumeTasks(activeChar.getObjectId());

		// check player skills
		if (Config.CHECK_SKILLS_ON_ENTER && !Config.ALT_GAME_SKILL_LEARN)
			activeChar.checkAllowedSkills(gdq);

		// check for academy
		activeChar.academyCheck(activeChar.getClassId().getId());

		// check for crowns
		CrownManager.checkCrowns(activeChar);

		if (Config.ONLINE_PLAYERS_AT_STARTUP)
		{
			SystemMessage sm = new SystemMessage(SystemMessageId.S1);
			if (L2World.getInstance().getAllPlayers().size() == 1)
				sm.addString("Player online: " + L2World.getInstance().getAllPlayers().size());
			else
				sm.addString("Players online: " + L2World.getInstance().getAllPlayers().size());
			gdq.add(sm);
		}

		PetitionManager.getInstance().checkPetitionMessages(activeChar);

		activeChar.onPlayerEnter(gdq);

		if (activeChar.getClanJoinExpiryTime() > System.currentTimeMillis())
			gdq.add(SystemMessageId.CLAN_MEMBERSHIP_TERMINATED);

		if (activeChar.getClan() != null)
		{
			// Add message if clanHall not paid. Possibly this is custom...
			ClanHall clanHall = ClanHallManager.getInstance().getClanHallByOwner(activeChar.getClan());
			if (clanHall != null && !clanHall.getPaid())
				gdq.add(SystemMessageId.PAYMENT_FOR_YOUR_CLAN_HALL_HAS_NOT_BEEN_MADE_PLEASE_MAKE_PAYMENT_TO_YOUR_CLAN_WAREHOUSE_BY_TOMORROW);
		}

		//Sets the appropriate Pledge Class for the clannie (e.g. Viscount, Count, Baron, Marquiz)
		activeChar.setPledgeClass(L2ClanMember.getCurrentPledgeClass(activeChar));

		L2ShortCut[] allShortCuts = activeChar.getAllShortCuts();
		for (L2ShortCut sc : allShortCuts)
			gdq.add(new ShortCutRegister(sc));

		// remove combat flag before teleporting
		L2ItemInstance flag = activeChar.getInventory().getItemByItemId(9819);
		if (flag != null)
		{
			Fort fort = FortManager.getInstance().getFort(activeChar);
			if (fort != null)
			{
				FortSiegeManager.getInstance().dropCombatFlag(activeChar);
			}
			else
			{
				int slot = flag.getItem().getBodyPart();
				activeChar.getInventory().unEquipItemInBodySlotAndRecord(slot);
				activeChar.destroyItem("CombatFlag", flag, null, true);
			}
		}
		if (!activeChar.isGM()
		// inside siege zone
				&& activeChar.isInsideZone(L2Zone.FLAG_SIEGE)
				// but non-participant or attacker
				&& (!activeChar.isInSiege() || activeChar.getSiegeState() < 2))
		{
			// Attacker or spectator logging in to a siege zone. Actually should be checked for inside castle only?
			activeChar.teleToLocation(TeleportWhereType.Town);
			//activeChar.sendMessage("You have been teleported to the nearest town due to you being in siege zone"); - custom
		}

		RegionBBSManager.changeCommunityBoard(activeChar, PlayerStateOnCommunity.NONE);

		if (!activeChar.isTransformed())
			activeChar.regiveTemporarySkills();

		// Send Teleport Bookmark List
		gdq.add(new ExGetBookMarkInfoPacket(activeChar));

		// Flush any left-over packets
		gdq.flush();

		ExBasicActionList.sendTo(activeChar);

		GlobalRestrictions.playerLoggedIn(activeChar);
	}

	/**
	 * @param activeChar
	 */
	private void engage(L2PcInstance cha)
	{
		int _chaid = cha.getObjectId();

		for (Couple cl : CoupleManager.getInstance().getCouples())
		{
			if (cl.getPlayer1Id() == _chaid || cl.getPlayer2Id() == _chaid)
			{
				if (cl.getMaried())
					cha.setMaried(true);

				cha.setCoupleId(cl.getId());

				if (cl.getPlayer1Id() == _chaid)
					cha.setPartnerId(cl.getPlayer2Id());
				else
					cha.setPartnerId(cl.getPlayer1Id());
			}
		}
	}

	/**
	 * @param activeChar partnerid
	 */
	private void notifyPartner(L2PcInstance cha)
	{
		if (cha.getPartnerId() != 0)
		{
			L2PcInstance partner = L2World.getInstance().getPlayer(cha.getPartnerId());
			if (partner != null)
				partner.sendMessage("Your Partner " + cha.getName() + " has logged in.");
		}
	}

	/**
	 * @param activeChar
	 */
	private void notifyFriends(L2PcInstance cha)
	{
		SystemMessage sm = new SystemMessage(SystemMessageId.FRIEND_S1_HAS_LOGGED_IN);
		sm.addPcName(cha);

		for (Integer objId : cha.getFriendList().getFriendIds())
		{
			L2PcInstance friend = L2World.getInstance().findPlayer(objId);
			if (friend != null)
			{
				friend.sendPacket(new FriendList(friend));
				friend.sendPacket(sm);
			}
		}
	}

	/**
	 * @param activeChar
	 */
	private void notifyClanMembers(L2PcInstance activeChar)
	{
		L2Clan clan = activeChar.getClan();
		if (clan != null)
		{
			L2ClanMember clanmember = clan.getClanMember(activeChar.getObjectId());
			if (clanmember != null)
			{
				clanmember.setPlayerInstance(activeChar);
				SystemMessage msg = new SystemMessage(SystemMessageId.CLAN_MEMBER_S1_LOGGED_IN);
				msg.addString(activeChar.getName());
				clan.broadcastToOtherOnlineMembers(msg, activeChar);
				msg = null;
				clan.broadcastToOtherOnlineMembers(new PledgeShowMemberListUpdate(activeChar), activeChar);
				if (clan.isNoticeEnabled() && !clan.getNotice().isEmpty())
					gdq.add(new NpcHtmlMessage(1, "<html><title>Clan Announcements</title><body><br><center><font color=\"CCAA00\">"
							+ activeChar.getClan().getName() + "</font> <font color=\"6655FF\">Clan Alert Message</font></center><br>"
							+ "<img src=\"L2UI.SquareWhite\" width=270 height=1><br>" + activeChar.getClan().getNotice() + "</body></html>"));
			}
		}
	}

	/**
	 * @param activeChar
	 */
	private void notifySponsorOrApprentice(L2PcInstance activeChar)
	{
		if (activeChar.getSponsor() != 0)
		{
			L2PcInstance sponsor = L2World.getInstance().getPlayer(activeChar.getSponsor());

			if (sponsor != null)
			{
				SystemMessage msg = new SystemMessage(SystemMessageId.YOUR_APPRENTICE_S1_HAS_LOGGED_IN);
				msg.addString(activeChar.getName());
				sponsor.sendPacket(msg);
			}
		}
		else if (activeChar.getApprentice() != 0)
		{
			L2PcInstance apprentice = L2World.getInstance().getPlayer(activeChar.getApprentice());

			if (apprentice != null)
			{
				SystemMessage msg = new SystemMessage(SystemMessageId.YOUR_SPONSOR_C1_HAS_LOGGED_IN);
				msg.addString(activeChar.getName());
				apprentice.sendPacket(msg);
			}
		}
	}

	private void loadTutorial(L2PcInstance player)
	{
		QuestState qs = player.getQuestState("255_Tutorial");
		if (qs != null)
			qs.getQuest().notifyEvent("UC", null, player);
	}

	@Override
	public String getType()
	{
		return _C__03_ENTERWORLD;
	}

	public class GameDataQueue implements Runnable
	{
		private final FastList<L2GameServerPacket>	packets;
		private volatile boolean					active;

		private GameDataQueue()
		{
			if (Config.ENTERWORLD_QUEUING)
				packets = new FastList<L2GameServerPacket>();
			else
				packets = null;
			active = false;
		}

		public final void add(L2GameServerPacket packet)
		{
			if (packets != null)
				packets.add(packet);
			else
				sendPacket(packet);
		}

		public final void add(SystemMessageId msg)
		{
			add(msg.getSystemMessage());
		}

		private void flush()
		{
			if (active || !valid())
				return;

			active = true;
			ThreadPoolManager.getInstance().scheduleGeneral(this, 0);
		}

		private final boolean valid()
		{
			return getActiveChar() != null && packets != null && !packets.isEmpty();
		}

		@Override
		public void run()
		{
			if (!valid())
			{
				active = false;
				return;
			}
			int pn = packets.size();
			if (pn > Config.ENTERWORLD_PPT)
				pn = Config.ENTERWORLD_PPT;
			for (int i = 0; i < pn; i++) {
				try {
				sendPacket(packets.removeFirst());
				} catch (Exception e) {
					_log.error("GDQ noob error (report in forum!) i=" + i + ", pn=" + pn + ", active=" + active, e);
				}
			}
			if (packets.isEmpty())
				active = false;
			else
				ThreadPoolManager.getInstance().scheduleGeneral(this, Config.ENTERWORLD_TICK);
		}
	}
}
