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
package com.l2jfree.gameserver.handler.admincommandhandlers;

import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.l2jfree.Config;
import com.l2jfree.gameserver.handler.IAdminCommandHandler;
import com.l2jfree.gameserver.model.L2Object;
import com.l2jfree.gameserver.model.L2World;
import com.l2jfree.gameserver.model.actor.L2Character;
import com.l2jfree.gameserver.model.actor.instance.L2ControllableMobInstance;
import com.l2jfree.gameserver.model.actor.instance.L2PcInstance;
import com.l2jfree.gameserver.network.SystemMessageId;

/**
 * This class handles following admin commands:
 * - kill = kills target L2Character
 * - kill_monster = kills target non-player
 * 
 * - kill <radius> = If radius is specified, then ALL players only in that radius will be killed.
 * - kill_monster <radius> = If radius is specified, then ALL non-players only in that radius will be killed.
 * 
 * @version $Revision: 1.2.4.5 $ $Date: 2007/07/31 10:06:06 $
 */
public class AdminKill implements IAdminCommandHandler
{
	private final static Log _log = LogFactory.getLog(AdminKill.class);
	private static final String[] ADMIN_COMMANDS = { "admin_kill", "admin_kill_monster" };
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		if (command.startsWith("admin_kill"))
		{
			StringTokenizer st = new StringTokenizer(command, " ");
			st.nextToken(); // skip command
			
			if (st.hasMoreTokens())
			{
				String firstParam = st.nextToken();
				L2PcInstance plyr = L2World.getInstance().getPlayer(firstParam);
				if (plyr != null)
				{
					if (st.hasMoreTokens())
					{
						try
						{
							int radius = Integer.parseInt(st.nextToken());
							for (L2Character knownChar : plyr.getKnownList().getKnownCharactersInRadius(radius))
							{
								if (knownChar instanceof L2ControllableMobInstance || knownChar == activeChar)
									continue;
								
								kill(activeChar, knownChar);
							}
							
							activeChar.sendMessage("Killed all characters within a " + radius + " unit radius.");
							return true;
						}
						catch (NumberFormatException e)
						{
							activeChar.sendMessage("Invalid radius.");
							return false;
						}
					}
					
					kill(activeChar, plyr);
				}
				else
				{
					try
					{
						int radius = Integer.parseInt(firstParam);
						
						for (L2Character knownChar : activeChar.getKnownList().getKnownCharactersInRadius(radius))
						{
							if (knownChar instanceof L2ControllableMobInstance || knownChar == activeChar)
								continue;
							
							kill(activeChar, knownChar);
						}
						
						activeChar.sendMessage("Killed all characters within a " + radius + " unit radius.");
						return true;
					}
					catch (NumberFormatException e)
					{
						activeChar.sendMessage("Enter a valid player name or radius.");
						return false;
					}
				}
			}
			else
			{
				L2Object obj = activeChar.getTarget();
				
				if (obj instanceof L2ControllableMobInstance || !(obj instanceof L2Character))
				{
					activeChar.sendPacket(SystemMessageId.INCORRECT_TARGET);
				}
				else
				{
					kill(activeChar, (L2Character)obj);
				}
			}
		}
		
		return true;
	}
	
	private void kill(L2PcInstance activeChar, L2Character target)
	{
		if (target instanceof L2PcInstance)
		{
			if (!((L2PcInstance)target).isGM())
				target.stopAllEffects(); // e.g. invincibility effect
			target.reduceCurrentHp(target.getMaxHp() + target.getMaxCp() + 1, activeChar);
		}
		else
		{
			if (target.isInvul())
				target.setIsInvul(false);
			if (target.isChampion())
				target.reduceCurrentHp(target.getMaxHp() * Config.CHAMPION_HP + 1, activeChar);
			else
				target.reduceCurrentHp(target.getMaxHp() + 1, activeChar);
		}
		
		if (_log.isDebugEnabled())
			_log.debug("GM: " + activeChar.getName() + "(" + activeChar.getObjectId() + ")" + " killed character "
					+ target.getObjectId());
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return ADMIN_COMMANDS;
	}
}
