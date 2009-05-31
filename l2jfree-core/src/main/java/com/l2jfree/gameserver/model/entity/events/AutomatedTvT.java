package com.l2jfree.gameserver.model.entity.events;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ScheduledFuture;

import javolution.util.FastList;
import javolution.util.FastMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.Config;
import com.l2jfree.L2DatabaseFactory;
import com.l2jfree.gameserver.Announcements;
import com.l2jfree.gameserver.ThreadPoolManager;
import com.l2jfree.gameserver.model.L2ItemInstance;
import com.l2jfree.gameserver.model.L2Skill;
import com.l2jfree.gameserver.model.Location;
import com.l2jfree.gameserver.model.actor.instance.L2CubicInstance;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfree.gameserver.model.olympiad.Olympiad;
import com.l2jfree.gameserver.model.zone.L2Zone;
import com.l2jfree.gameserver.network.SystemChatChannelId;
import com.l2jfree.gameserver.network.SystemMessageId;
import com.l2jfree.gameserver.network.serverpackets.CreatureSay;
import com.l2jfree.gameserver.network.serverpackets.SystemMessage;
import com.l2jfree.gameserver.util.Broadcast;
import com.l2jfree.tools.random.Rnd;

/**
 * @author savormix
 */
public final class AutomatedTvT
{
	private static final Log _log = LogFactory.getLog(AutomatedTvT.class);
	private static final String REMOVE_DISCONNECTED_PLAYER = "UPDATE characters SET heading=?,x=?,y=?,z=?,title=? WHERE charId=?";
	private static final String evtName = "Team versus team";

	//when the event has ended and not yet started
	private static final int STATUS_NOT_IN_PROGRESS	= 0;
	//registration in progress
	private static final int STATUS_REGISTRATION	= 1;
	//registration ended, players frozen & teled to the place, waiting for them to appear
	private static final int STATUS_PREPARATION		= 2;
	//players are allowed to fight
	private static final int STATUS_COMBAT			= 3;
	//players are frozen, rewarded and teled back to where they were
	private static final int STATUS_REWARDS			= 4;

	private static AutomatedTvT instance = null;

	public static final AutomatedTvT getInstance()
	{
		if (instance == null)
			instance = new AutomatedTvT();
		return instance;
	}

	/**
	 * Called when configuration is reloaded and {@link Config#AUTO_TVT_ENABLED} = true<BR>
	 * <CODE>instance</CODE> will only be <CODE>null</CODE> when the config is loaded during
	 * server startup, and we don't want the event to start countdown THAT early.<BR>
	 * <I>Normally initialization is called when loading [static] extensions.</I>
	 */
	public static final void startIfNecessary()
	{
		if (instance != null && !instance.active)
			instance.tpm.scheduleGeneral(instance.task, Config.AUTO_TVT_DELAY_INITIAL_REGISTRATION);
	}

	private final ThreadPoolManager tpm;

	private final AutoEventTask task;
	private final AutoReviveTask taskDuring;
	private ScheduledFuture<?> reviver;
	private ScheduledFuture<?> event;

	private final FastList<Integer> registered;
	private final FastList<L2PcInstance> participants;
	private final FastMap<Integer, Participant> eventPlayers;
	private Team[] eventTeams;

	private volatile int status;
	private volatile boolean active;
	private int announced;

	private AutomatedTvT()
	{
		tpm = ThreadPoolManager.getInstance();
		status = STATUS_NOT_IN_PROGRESS;
		announced = 0;
		// This has no maximum bound, thus configuration changes will not crash anything
		participants = new FastList<L2PcInstance>(Config.AUTO_TVT_PARTICIPANTS_MAX);
		registered = new FastList<Integer>(Config.AUTO_TVT_PARTICIPANTS_MAX);
		eventPlayers = new FastMap<Integer, Participant>(Config.AUTO_TVT_PARTICIPANTS_MAX);
		eventTeams = null;
		task = new AutoEventTask();
		taskDuring = new AutoReviveTask();
		reviver = null;
		active = Config.AUTO_TVT_ENABLED;
		if (active)
			tpm.scheduleGeneral(task, Config.AUTO_TVT_DELAY_INITIAL_REGISTRATION);
		_log.info("AutomatedTvT: initialized.");
	}

	private class AutoEventTask implements Runnable
	{
		@Override
		public void run()
		{
			switch (status)
			{
			case STATUS_NOT_IN_PROGRESS:
				if (Config.AUTO_TVT_ENABLED)
					registrationStart();
				else
					active = false;
				break;
			case STATUS_REGISTRATION:
				if (announced < (Config.AUTO_TVT_REGISTRATION_ANNOUNCEMENT_COUNT + 2))
					registrationAnnounce();
				else
					registrationEnd();
				break;
			case STATUS_PREPARATION:
				eventStart();
				break;
			case STATUS_COMBAT:
				eventEnd();
				break;
			case STATUS_REWARDS:
				status = STATUS_NOT_IN_PROGRESS;
				tpm.scheduleGeneral(task, Config.AUTO_TVT_DELAY_BETWEEN_EVENTS);
				break;
			default:
				_log.fatal("Incorrect status set in Automated " + evtName + ", terminating the event!");
			}
		}
	}

	private class AutoReviveTask implements Runnable
	{
		@Override
		public void run()
		{
			L2PcInstance player;
			for (Participant p : eventPlayers.values())
			{
				player = p.getPlayer();
				if (player != null && player.isDead())
					revive(player, p.getTeam());
			}
		}
	}

	private final void registrationStart()
	{
		status = STATUS_REGISTRATION;
		Announcements.getInstance().announceToAll(SystemMessageId.REGISTRATION_PERIOD);
		SystemMessage time = new SystemMessage(SystemMessageId.REGISTRATION_TIME_S1_S2_S3);
		long timeLeft = Config.AUTO_TVT_PERIOD_LENGHT_REGISTRATION / 1000;
		time.addNumber(timeLeft / 3600);
		time.addNumber(timeLeft % 3600 / 60);
		time.addNumber(timeLeft % 3600 % 60);
		Broadcast.toAllOnlinePlayers(time);
		Announcements.getInstance().announceToAll("To join the " + evtName + " you must type .jointvt");
		tpm.scheduleGeneral(task, Config.AUTO_TVT_PERIOD_LENGHT_REGISTRATION / (Config.AUTO_TVT_REGISTRATION_ANNOUNCEMENT_COUNT + 2));
	}

	private final void registrationAnnounce()
	{
		SystemMessage time = new SystemMessage(SystemMessageId.REGISTRATION_TIME_S1_S2_S3);
		long timeLeft = Config.AUTO_TVT_PERIOD_LENGHT_REGISTRATION;
		long elapsed = timeLeft / (Config.AUTO_TVT_REGISTRATION_ANNOUNCEMENT_COUNT + 2) * announced;
		timeLeft -= elapsed;
		timeLeft /= 1000;
		time.addNumber(timeLeft / 3600);
		time.addNumber(timeLeft % 3600 / 60);
		time.addNumber(timeLeft % 3600 % 60);
		Broadcast.toAllOnlinePlayers(time);
		Announcements.getInstance().announceToAll("To join the " + evtName + " you must type .jointvt");
		announced++;
		tpm.scheduleGeneral(task, Config.AUTO_TVT_PERIOD_LENGHT_REGISTRATION / (Config.AUTO_TVT_REGISTRATION_ANNOUNCEMENT_COUNT + 2));
	}

	private final void registrationEnd()
	{
		announced = 0;
		status = STATUS_PREPARATION;

		registered.clear();

		L2PcInstance player;
		for (FastList.Node<L2PcInstance> n = participants.head(), end = participants.tail(); (n = n.getNext()) != end;)
		{
			player = n.getValue();
			if (!canJoin(player))
			{
				player.sendMessage("You no longer meet the requirements to join " + evtName);
				participants.remove(player);
			}
		}

		if (participants.size() < Config.AUTO_TVT_PARTICIPANTS_MIN)
		{
			Announcements.getInstance().announceToAll(evtName + " will not start, not enough players!");
			participants.clear();
			status = STATUS_NOT_IN_PROGRESS;
			tpm.scheduleGeneral(task, Config.AUTO_TVT_DELAY_BETWEEN_EVENTS);
			return;
		}

		eventTeams = new Team[Config.AUTO_TVT_TEAM_LOCATIONS.length];
		for (int i = 0; i < eventTeams.length; i++)
			eventTeams[i] = new Team(correctColor(eventTeams, Rnd.get(256), Rnd.get(256), Rnd.get(256), i));

		int currTeam = 0;
		SystemMessage time = new SystemMessage(SystemMessageId.BATTLE_BEGINS_S1_S2_S3);
		long timeLeft = Config.AUTO_TVT_PERIOD_LENGHT_PREPARATION / 1000;
		time.addNumber(timeLeft / 3600);
		time.addNumber(timeLeft % 3600 / 60);
		time.addNumber(timeLeft % 3600 % 60);

		for (FastList.Node<L2PcInstance> n = participants.head(), end = participants.tail(); (n = n.getNext()) != end;)
		{
			player = n.getValue();
			eventPlayers.put(player.getObjectId(), new Participant(currTeam, player));
			player.getAppearance().setNameColor((eventTeams[currTeam].getColorRed() & 0xFF) +
					(eventTeams[currTeam].getColorGreen() << 8) +
					(eventTeams[currTeam].getColorBlue() << 16));
			player.setIsPetrified(true);
			player.sendPacket(time);
			checkEquipment(player);
			if (Config.AUTO_TVT_START_CANCEL_PARTY && player.getParty() != null)
				player.getParty().removePartyMember(player);
			if (Config.AUTO_TVT_START_CANCEL_BUFFS)
				player.stopAllEffects();
			if (Config.AUTO_TVT_START_CANCEL_CUBICS && !player.getCubics().isEmpty())
			{
				for (L2CubicInstance cubic : player.getCubics().values())
				{
					cubic.stopAction();
					cubic.cancelDisappear();
				}
				player.getCubics().clear();
			}
			if (Config.AUTO_TVT_START_CANCEL_SERVITORS && player.getPet() != null)
				player.getPet().unSummon();
			if (Config.AUTO_TVT_START_CANCEL_TRANSFORMATION && player.isTransformed())
				player.untransform();
			if (player.isDead())
				player.setIsPendingRevive(true);
			player.teleToLocation(Config.AUTO_TVT_TEAM_LOCATIONS[currTeam][0],
					Config.AUTO_TVT_TEAM_LOCATIONS[currTeam][1],
					Config.AUTO_TVT_TEAM_LOCATIONS[currTeam][2]);
			if (Config.AUTO_TVT_START_RECOVER)
			{
				player.getStatus().setCurrentCp(player.getMaxCp());
				player.getStatus().setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
			}
			currTeam++;
			if (currTeam == eventTeams.length)
				currTeam = 0;
		}
		participants.clear();
		tpm.scheduleGeneral(task, Config.AUTO_TVT_PERIOD_LENGHT_PREPARATION);
	}

	private final void eventStart()
	{
		status = STATUS_COMBAT;
		SystemMessage time = new SystemMessage(SystemMessageId.BATTLE_ENDS_S1_S2_S3);
		long timeLeft = Config.AUTO_TVT_PERIOD_LENGHT_EVENT / 1000;
		time.addNumber(timeLeft / 3600);
		time.addNumber(timeLeft % 3600 / 60);
		time.addNumber(timeLeft % 3600 % 60);
		L2PcInstance player;
		for (Participant p : eventPlayers.values())
		{
			player = p.getPlayer();
			if (player == null)
				continue;
			player.setIsPetrified(false);
			player.sendPacket(time);
			updatePlayerTitle(p);
		}
		reviver = tpm.scheduleAtFixedRate(taskDuring, Config.AUTO_TVT_REVIVE_DELAY, Config.AUTO_TVT_REVIVE_DELAY);
		event = tpm.scheduleGeneral(task, Config.AUTO_TVT_PERIOD_LENGHT_EVENT);
	}

	private final void eventEnd()
	{
		if (status != STATUS_COMBAT)
			return;
		status = STATUS_REWARDS;
		reviver.cancel(true);
		if (!event.cancel(false))
			return;

		int winnerTeam = getWinnerTeam();
		if (winnerTeam != -1)
		{
			Announcements.getInstance().announceToAll(evtName + ": Team " +
					(winnerTeam + 1) + "wins!");
			Announcements.getInstance().announceToAll(evtName + ": Cumulative score: " +
					eventTeams[winnerTeam].getPoints());
		}
		else
			Announcements.getInstance().announceToAll(evtName + ": There is no winner team.");

		L2PcInstance player;
		for (Participant p : eventPlayers.values())
		{
			player = p.getPlayer();
			if (player == null)
			{
				removeDisconnected(p.getObjectID(), p.getLoc(), p.getTitle());
				continue;
			}
			if (p.getTeam() == winnerTeam)
				reward(player);
			player.setTitle(p.getTitle());
			player.getAppearance().setNameColor(p.getNameColor());
			if (!player.isDead())
			{
				player.getStatus().setCurrentCp(player.getMaxCp());
				player.getStatus().setCurrentHpMp(player.getMaxHp(), player.getMaxMp());
			}
			else
				player.setIsPendingRevive(true);
			if (Config.AUTO_TVT_OVERRIDE_TELE_BACK)
				player.teleToLocation(Config.AUTO_TVT_DEFAULT_TELE_BACK[0],
						Config.AUTO_TVT_DEFAULT_TELE_BACK[1],
						Config.AUTO_TVT_DEFAULT_TELE_BACK[2]);
			else
				player.teleToLocation(p.getLoc(), true);
		}
		tpm.scheduleGeneral(task, Config.AUTO_TVT_PERIOD_LENGHT_REWARDS);
	}

	public final void addDisconnected(L2PcInstance participant)
	{
		switch (status)
		{
		case STATUS_REGISTRATION:
			if (Config.AUTO_TVT_REGISTER_AFTER_RELOG &&
					registered.remove(participant.getObjectId()))
				registerPlayer(participant);
			break;
		case STATUS_COMBAT:
			Participant p = eventPlayers.get(participant.getObjectId());
			if (p == null) break;
			p.setPlayer(participant);
			checkEquipment(participant);
			updatePlayerTitle(p);
			int team = p.getTeam();
			participant.getAppearance().setNameColor((eventTeams[team].getColorRed() & 0xFF) +
					(eventTeams[team].getColorGreen() << 8) +
					(eventTeams[team].getColorBlue() << 16));
			participant.teleToLocation(Config.AUTO_TVT_TEAM_LOCATIONS[team][0],
					Config.AUTO_TVT_TEAM_LOCATIONS[team][1],
					Config.AUTO_TVT_TEAM_LOCATIONS[team][2]);
			break;
		}
	}

	private final void checkEquipment(L2PcInstance player)
	{
		L2ItemInstance item;
		for (int i = 0; i < 25; i++)
		{
			synchronized (player.getInventory())
			{
				item = player.getInventory().getPaperdollItem(i);
				if (item != null && !canUse(item.getItemId()))
					player.useEquippableItem(item, true);
			}
		}
	}

	public static final boolean canUse(int itemId)
	{
		for (int id : Config.AUTO_TVT_DISALLOWED_ITEMS)
			if (itemId == id)
				return false;
		return true;
	}

	private final int getWinnerTeam()
	{
		int maxPts = 0, winTeam = -1, temp;
		for (int i = 0; i < eventTeams.length; i++)
		{
			temp = eventTeams[i].getPoints();
			if (temp > maxPts)
			{
				maxPts = temp;
				winTeam = i;
			}
		}
		return winTeam;
	}

	private final void reward(L2PcInstance player)
	{
		for (int i = 0; i < Config.AUTO_TVT_REWARD_IDS.length; i++)
		{
			player.addItem("TvT Reward", Config.AUTO_TVT_REWARD_IDS[i],
					Config.AUTO_TVT_REWARD_COUNT[i], null, false, true);
			player.sendPacket(new SystemMessage(SystemMessageId.CONGRATULATIONS_RECEIVED_S1).addItemName(Config.AUTO_TVT_REWARD_IDS[i]));
		}
	}

	public static final boolean isInProgress()
	{
		switch (getInstance().status)
		{
		case STATUS_PREPARATION:
		case STATUS_COMBAT:
			return true;
		default:
			return false;
		}
	}

	public static final boolean isReged(L2PcInstance player)
	{
		return getInstance().isMember(player);
	}

	public static final boolean isPlaying(L2PcInstance player)
	{
		return isInProgress() && isReged(player);
	}

	public final boolean isMember(L2PcInstance player)
	{
		if (player == null)
			return false;

		switch (status)
		{
		case STATUS_NOT_IN_PROGRESS:
			return false;
		case STATUS_REGISTRATION:
			return participants.contains(player);
		case STATUS_PREPARATION:
			return participants.contains(player) || isMember(player.getObjectId());
		case STATUS_COMBAT:
		case STATUS_REWARDS:
			return isMember(player.getObjectId());
		default:
			return false;
		}
	}

	private final boolean isMember(int oID)
	{
		return eventPlayers.get(oID) != null;
	}

	private final boolean canJoin(L2PcInstance player)
	{
		// Level restrictions
		boolean can = player.getLevel() <= Config.AUTO_TVT_LEVEL_MAX;
		can &= player.getLevel() >= Config.AUTO_TVT_LEVEL_MIN;
		// Cannot mess with Olympiad
		can &= !(player.isInOlympiadMode() || Olympiad.getInstance().isRegistered(player));
		// Cannot mess with raids or sieges
		can &= !player.isInsideZone(L2Zone.FLAG_NOESCAPE);
		can &= !(player.getMountType() == 2 && player.isInsideZone(L2Zone.FLAG_NOLANDING));
		// Hero restriction
		if (!Config.AUTO_TVT_REGISTER_HERO)
			can &= !player.isHero();
		// Cursed weapon owner restriction
		if (!Config.AUTO_TVT_REGISTER_CURSED)
			can &= !player.isCursedWeaponEquipped();
		return can;
	}

	public final void registerPlayer(L2PcInstance player)
	{
		if (!active) return;

		if (status != STATUS_REGISTRATION ||
				participants.size() >= Config.AUTO_TVT_PARTICIPANTS_MAX)
			player.sendPacket(SystemMessageId.REGISTRATION_PERIOD_OVER);
		else if (!participants.contains(player))
		{
			if (!canJoin(player))
			{
				player.sendMessage("You do not meet the requirements to join " + evtName);
				return;
			}
			participants.add(player);
			registered.add(player.getObjectId());
			player.sendMessage("You have been registered to " + evtName);
			if (Config.AUTO_TVT_REGISTER_CANCEL)
				player.sendMessage("If you decide to cancel your registration, type .leavetvt");
		}
		else
			player.sendMessage("Already registered!");
	}

	public final void cancelRegistration(L2PcInstance player)
	{
		if (!active) return;

		if (status != STATUS_REGISTRATION)
			player.sendPacket(SystemMessageId.REGISTRATION_PERIOD_OVER);
		else if (participants.contains(player))
		{
			participants.remove(player);
			registered.remove(player.getObjectId());
			player.sendMessage("You have cancelled your registration in " + evtName);
		}
		else
			player.sendMessage("You have not registered in " + evtName);
	}

	public final void onKill(L2PcInstance killer, L2PcInstance victim)
	{
		if (status != STATUS_COMBAT || !isMember(killer) || !isMember(victim))
			return;
		Participant kp = eventPlayers.get(killer.getObjectId());
		Participant vp = eventPlayers.get(victim.getObjectId());
		if (kp.getTeam() != vp.getTeam())
		{
			kp.increaseScore();
			eventTeams[kp.getTeam()].addPoint();
			if (kp.isGodlike() && Config.AUTO_TVT_GODLIKE_ANNOUNCE)
			{
				CreatureSay cs = new CreatureSay(0, SystemChatChannelId.Chat_Shout,
						evtName, killer.getName() + ": God-like!");
				for (Participant p : eventPlayers.values())
					if (p.getPlayer() != null)
						p.getPlayer().sendPacket(cs);
			}
		}
		else if (Config.AUTO_TVT_TK_PUNISH)
		{
			kp.decreaseScore(true);
			if (Config.AUTO_TVT_TK_PUNISH_CANCEL)
				killer.stopAllEffects();
			if (Config.AUTO_TVT_TK_PUNISH_EFFECTS != null)
			{
				for (L2Skill s : Config.AUTO_TVT_TK_PUNISH_EFFECTS)
				{
					if (s == null) continue;
					if (killer.getFirstEffect(s) != null)
						killer.getFirstEffect(s).exit();
					s.getEffects(killer, killer);
				}
			}
		}
		vp.decreaseScore(false);
		updatePlayerTitle(kp);
		updatePlayerTitle(vp);
	}

	public final void revive(L2PcInstance participant, int team)
	{
		participant.setIsPendingRevive(true);
		participant.teleToLocation(Config.AUTO_TVT_TEAM_LOCATIONS[team][0],
				Config.AUTO_TVT_TEAM_LOCATIONS[team][1],
				Config.AUTO_TVT_TEAM_LOCATIONS[team][2]);
	}

	public final void recover(L2PcInstance revived)
	{
		if (Config.AUTO_TVT_REVIVE_RECOVER && isPlaying(revived))
		{
			revived.getStatus().setCurrentCp(revived.getMaxCp());
			revived.getStatus().setCurrentHpMp(revived.getMaxHp(), revived.getMaxMp());
		}
	}

	private final void updatePlayerTitle(Participant p)
	{
		L2PcInstance player = p.getPlayer();
		if (player == null) return;
		if (p.isGodlike())
			player.setTitle(Config.AUTO_TVT_GODLIKE_TITLE);
		else
			player.setTitle("Score: " + p.getScore());
		player.broadcastTitleInfo();
	}

	public final void onDisconnection(L2PcInstance player)
	{
		if (!isReged(player))
			return;
		switch (status)
		{
		case STATUS_REGISTRATION:
			participants.remove(player);
			break;
		case STATUS_COMBAT:
		case STATUS_REWARDS:
			Participant p = eventPlayers.get(player.getObjectId());
			p.setPlayer(null);
			if (countTeamMembers(p.getTeam()) == 0)
				eventEnd();
			break;
		}
	}

	private final int countTeamMembers(int team)
	{
		int result = 0;
		for (Participant p : eventPlayers.values())
			if (p.getTeam() == team && p.getPlayer() != null)
				result++;
		return result;
	}

	private final void removeDisconnected(int objID, Location loc, String title)
	{
		Connection con = null;
		try
		{
			con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement(REMOVE_DISCONNECTED_PLAYER);
			ps.setInt(1, loc.getHeading());
			if (Config.AUTO_TVT_OVERRIDE_TELE_BACK)
			{
				ps.setInt(2, Config.AUTO_TVT_DEFAULT_TELE_BACK[0]);
				ps.setInt(3, Config.AUTO_TVT_DEFAULT_TELE_BACK[1]);
				ps.setInt(4, Config.AUTO_TVT_DEFAULT_TELE_BACK[2]);
			}
			else
			{
				ps.setInt(2, loc.getX());
				ps.setInt(3, loc.getY());
				ps.setInt(4, loc.getZ());
			}
			ps.setString(5, title);
			ps.setInt(6, objID);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException e)
		{
			_log.error("Could not remove a disconnected TvT player!", e);
		}
		finally
		{
			L2DatabaseFactory.close(con);
		}
	}

	private final int[] correctColor(Team[] teams, int rn, int gn, int bn, int current) {
		int[] result = { rn, gn, bn };
		// Possible to do fast enough even if there are 32+ teams,
		// But I don't think this "blind shot" idea is suited for that
		if (Config.AUTO_TVT_TEAM_LOCATIONS.length > 32 || current == 0)
			return result;

		// TODO: calibrate the multiplier
		int totalDiff, noticeable = (256 * 2) / Config.AUTO_TVT_TEAM_LOCATIONS.length;
		while (true)
		{
			for (int i = 0; i < current; i++)
			{
				totalDiff = (Math.abs(result[0] - teams[i].getColorRed()) +
						Math.abs(result[1] - teams[i].getColorGreen()) +
						Math.abs(result[2] - teams[i].getColorBlue()));
				if (totalDiff < noticeable)
				{
					result[0] = Rnd.get(256);
					result[1] = Rnd.get(256);
					result[2] = Rnd.get(256);
				}
				else
					return result;
			}
		}
	}

	private class Participant
	{
		private final int team;
		private final int objectID;
		private final Location loc;
		private final String title;
		private final int nameColor;
		private volatile L2PcInstance player;
		private int points;
		private int killsNoDeath;

		private Participant(int team, L2PcInstance player)
		{
			this.team = team;
			this.objectID = player.getObjectId();
			this.loc = player.getLoc();
			this.title = player.getTitle();
			this.nameColor = player.getAppearance().getNameColor();
			this.player = player;
			this.points = 0;
			this.killsNoDeath = 0;
		}

		public final L2PcInstance getPlayer()
		{
			return player;
		}

		public final void setPlayer(L2PcInstance player)
		{
			this.player = player;
		}

		public final int getObjectID()
		{
			return objectID;
		}

		public final int getTeam()
		{
			return team;
		}

		public final Location getLoc()
		{
			return loc;
		}

		public final String getTitle()
		{
			return title;
		}

		public final int getNameColor()
		{
			return nameColor;
		}

		public final int getScore()
		{
			return points;
		}

		public final void increaseScore()
		{
			if (isGodlike())
				points += Config.AUTO_TVT_GODLIKE_POINT_MULTIPLIER;
			else
				points++;
			killsNoDeath++;
		}

		public final void decreaseScore(boolean tk)
		{
			if (tk)
				points -= Config.AUTO_TVT_TK_PUNISH_POINTS_LOST;
			else
				points--;
			if (!tk)
				killsNoDeath = 0;
			else if (Config.AUTO_TVT_TK_RESET_GODLIKE)
				killsNoDeath = 0;
		}

		public final boolean isGodlike()
		{
			return (Config.AUTO_TVT_GODLIKE_SYSTEM
					&& killsNoDeath >= Config.AUTO_TVT_GODLIKE_MIN_KILLS);
		}
	}

	private class Team
	{
		// Array index serves better
		//private final int id;
		private final int r;
		private final int g;
		private final int b;
		private int points;

		//private Team(int id, int[] rgb)
		private Team(int[] rgb)
		{
			//this.id = id;
			this.r = rgb[0];
			this.g = rgb[1];
			this.b = rgb[2];
			this.points = 0;
		}

		public final int getPoints()
		{
			return points;
		}

		public final void addPoint()
		{
			points++;
		}
/*
		public final int getId()
		{
			return id;
		}
*/
		public final int getColorRed()
		{
			return r;
		}

		public final int getColorGreen()
		{
			return g;
		}

		public final int getColorBlue()
		{
			return b;
		}
	}
}