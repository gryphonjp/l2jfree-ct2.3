/*
* This program is free software; you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation; either version 2, or (at your option)
* any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program; if not, write to the Free Software
* Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
* 02111-1307, USA.
*
* http://www.gnu.org/copyleft/gpl.html
*/
package net.sf.l2j.gameserver.clientpackets;
 
import java.nio.ByteBuffer;

import net.sf.l2j.Config;
import net.sf.l2j.gameserver.ClientThread;
import net.sf.l2j.gameserver.datatables.SkillSpellbookTable;
import net.sf.l2j.gameserver.datatables.SkillTable;
import net.sf.l2j.gameserver.datatables.SkillTreeTable;
import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.L2PledgeSkillLearn;
import net.sf.l2j.gameserver.model.L2ShortCut;
import net.sf.l2j.gameserver.model.L2Skill;
import net.sf.l2j.gameserver.model.L2SkillLearn;
import net.sf.l2j.gameserver.model.actor.instance.L2FishermanInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2FolkInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2NpcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2VillageMasterInstance;
import net.sf.l2j.gameserver.serverpackets.ExStorageMaxCount;
import net.sf.l2j.gameserver.serverpackets.ShortCutRegister;
import net.sf.l2j.gameserver.serverpackets.StatusUpdate;
import net.sf.l2j.gameserver.serverpackets.SystemMessage;
import net.sf.l2j.gameserver.util.IllegalPlayerAction;
import net.sf.l2j.gameserver.util.Util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
 
/**
* This class ...
* 
* @version $Revision: 1.7.2.1.2.4 $ $Date: 2005/03/27 15:29:30 $
*/
public class RequestAquireSkill extends ClientBasePacket
{
    private static final String _C__6C_REQUESTAQUIRESKILL = "[C] 6C RequestAquireSkill";
    private final static Log _log = LogFactory.getLog(RequestAquireSkill.class.getName());
    
    private final int _id;
    private final int _level;
    private final int _skillType;
    
    /**
     * packet type id 0x6c
     * format rev650:       cddd
     * @param rawPacket
     */
    public RequestAquireSkill(ByteBuffer buf, ClientThread client)
    {
        super(buf, client);
        _id = readD();
        _level = readD();
        _skillType = readD();
    }
    
    void runImpl()
    {
        L2PcInstance player = getClient().getActiveChar();
        if (player.isGM()) player.sendMessage("Resquest for skill received");
        if (player == null) return;
        
        L2FolkInstance trainer = player.getLastFolkNPC();
        if (trainer == null){if (player.isGM()) player.sendMessage("Resquest for skill termintated, cuz wrog Npc"); return;}
        
        int npcid = trainer.getNpcId();
        
        if (!player.isInsideRadius(trainer, L2NpcInstance.INTERACTION_DISTANCE, false, false) && !player.isGM())
            return;
        
        if (!Config.ALT_GAME_SKILL_LEARN) player.setSkillLearningClassId(player.getClassId());
        
        if (player.getSkillLevel(_id) >= _level) {
            // already knows the skill with this level
            return;
        }
        
        L2Skill skill = SkillTable.getInstance().getInfo(_id, _level);
        
        int counts = 0;
        int _requiredSp = 10000000;
        
        if (_skillType == 0) 
        {
            // Skill Learn bug Fix
            L2SkillLearn[] skills = SkillTreeTable.getInstance().getAvailableSkills(player, player.getSkillLearningClassId());
            
            for (L2SkillLearn s : skills)
            {
                L2Skill sk = SkillTable.getInstance().getInfo(s.getId(), s.getLevel());
                if (sk == null || sk != skill 
                        || !sk.getCanLearn(player.getSkillLearningClassId())
                        || !sk.canTeachBy(npcid)) continue;
                counts++;
                _requiredSp = SkillTreeTable.getInstance().getSkillCost(player, skill);
            }
            
            if (counts == 0 && !Config.ALT_GAME_SKILL_LEARN)
            {
                player.sendMessage("You are trying to learn skill that u can't..");
                Util.handleIllegalPlayerAction(player, "Player " + player.getName()
                                               + " tried to learn skill that he can't!!!", IllegalPlayerAction.PUNISH_KICK);
                return;
            }
            
            if (player.getSp() >= _requiredSp)
            {       
                if (Config.SP_BOOK_NEEDED)
                {
                    int spbId = SkillSpellbookTable.getInstance().getBookForSkill(skill);
                    
                    if (skill.getLevel() == 1 && spbId > -1)
                    {                       
                        L2ItemInstance spb = player.getInventory().getItemByItemId(spbId);
                        
                        if (spb == null)
                        {
                            // Haven't spellbook
                            player.sendPacket(new SystemMessage(SystemMessage.ITEM_MISSING_TO_LEARN_SKILL));
                            return;
                        }
                        
                        // ok
                        player.destroyItem("Consume", spb, trainer, true);
                    }                    
                }
            }
            else
            {
                SystemMessage sm = new SystemMessage(SystemMessage.NOT_ENOUGH_SP_TO_LEARN_SKILL);
                player.sendPacket(sm);
                sm = null;
                return;
            }
        }
        else if (_skillType == 1)
        {
            int costid = 0;
            int costcount = 0;
            //Skill Learn bug Fix
            L2SkillLearn[] skillsc = SkillTreeTable.getInstance().getAvailableSkills(player);
            
            for (L2SkillLearn s : skillsc)
            {
                L2Skill sk = SkillTable.getInstance().getInfo(s.getId(), s.getLevel());
                
                if (sk == null || sk != skill)
                    continue;
                
                counts++;
                costid = s.getIdCost();
                costcount = s.getCostCount();
                _requiredSp = s.getSpCost();                
            }
            
            if (counts == 0)
            {
                player.sendMessage("You are trying to learn skill that u can't..");
                Util.handleIllegalPlayerAction(player, "Player " + player.getName()
                                               + " tried to learn skill that he can't!!!", IllegalPlayerAction.PUNISH_KICK);
                return;
            }
            
            if (player.getSp() >= _requiredSp)
            {
                if (!player.destroyItemByItemId("Consume", costid, costcount, trainer, false))
                {
                    // Haven't spellbook
                    player.sendPacket(new SystemMessage(SystemMessage.ITEM_MISSING_TO_LEARN_SKILL));
                    return;
                }
                
                SystemMessage sm = new SystemMessage(SystemMessage.DISSAPEARED_ITEM);
                sm.addNumber(costcount);
                sm.addItemName(costid);
                sendPacket(sm);
                sm = null;
            }
            else
            {
                SystemMessage sm = new SystemMessage(SystemMessage.NOT_ENOUGH_SP_TO_LEARN_SKILL);
                player.sendPacket(sm);
                sm = null;
                return;
            }
        }
        else if (_skillType == 2) //pledgeskills TODO: Find appropriate system messages.
        {
            if (!player.isClanLeader()) 
            {
               // TODO: Find and add system msg
               player.sendMessage("This feature is available only for the clan leader");
               return;
            }
            
            int itemId = 0;
            int repCost = 100000000;
            // Skill Learn bug Fix
            L2PledgeSkillLearn[] skills = SkillTreeTable.getInstance().getAvailablePledgeSkills(player);
            
            for (L2PledgeSkillLearn s : skills)
            {
                L2Skill sk = SkillTable.getInstance().getInfo(s.getId(), s.getLevel());
                
                if (sk == null || sk != skill)
                    continue;
                
                counts++;
                itemId = s.getItemId();
                repCost = s.getRepCost(); 
            }
            
            if (counts == 0)
            {
                player.sendMessage("You are trying to learn skill that u can't..");
                Util.handleIllegalPlayerAction(player, "Player " + player.getName()
                                               + " tried to learn skill that he can't!!!", IllegalPlayerAction.PUNISH_KICK);
                return;
            }
            
            if (player.getClan().getReputationScore() >= repCost)
            {
                if (!player.destroyItemByItemId("Consume", itemId, 1, trainer, false))
                {
                    // Haven't spellbook
                    player.sendPacket(new SystemMessage(SystemMessage.ITEM_MISSING_TO_LEARN_SKILL));
                    return;
                }
                
                SystemMessage sm = new SystemMessage(SystemMessage.DISSAPEARED_ITEM);
                sm.addItemName(itemId);
                sm.addNumber(1);
                sendPacket(sm);
                sm = null;
            }
            else
            {
                SystemMessage sm = new SystemMessage(1852);
                player.sendPacket(sm);
                sm = null;
                return;
            }
            player.getClan().addNewSkill(skill);
            
            if (_log.isDebugEnabled()) 
                _log.debug("Learned pledge skill " + _id + " for " + _requiredSp + " SP.");
            
            player.getClan().setReputationScore(player.getClan().getReputationScore()-repCost, true);
            
            SystemMessage cr = new SystemMessage(1787);
            cr.addNumber(repCost);
            player.sendPacket(cr);
            SystemMessage sm = new SystemMessage(1788);
            sm.addSkillName(_id);
            player.sendPacket(sm);
            sm = null;
            
            ((L2VillageMasterInstance)trainer).showPledgeSkillList(player); //Maybe we shoud add a check here...
            
            return;
        }
        else
        {
            _log.warn("Recived Wrong Packet Data in Aquired Skill - unk1:" +_skillType);
            return;
        }
        
        player.addSkill(skill);
        
        if (_log.isDebugEnabled()) 
            _log.debug("Learned skill " + _id + " for " + _requiredSp + " SP.");
        
        player.setSp(player.getSp() - _requiredSp);
        player.updateStats();
        
        StatusUpdate su = new StatusUpdate(player.getObjectId());
        su.addAttribute(StatusUpdate.SP, player.getSp());
        player.sendPacket(su);

        SystemMessage sp = new SystemMessage(538);
        sp.addNumber(_requiredSp);
        sendPacket(sp);

        SystemMessage sm = new SystemMessage(SystemMessage.LEARNED_SKILL_S1);
        sm.addSkillName(_id);
        player.sendPacket(sm);
        sm = null;
        
        // update all the shortcuts to this skill
        if (_level > 1)
        {
            L2ShortCut[] allShortCuts = player.getAllShortCuts();
            
            for (L2ShortCut sc : allShortCuts)          
            {               
                if (sc.getId() == _id && sc.getType() == L2ShortCut.TYPE_SKILL)
                {
                    L2ShortCut newsc = new L2ShortCut(sc.getSlot(), sc.getPage(), sc.getType(), sc.getId(), _level, 1);
                    player.sendPacket(new ShortCutRegister(newsc));                 
                    player.registerShortCut(newsc);                 
                }
            }
        }
        
        if (trainer instanceof L2FishermanInstance)
            ((L2FishermanInstance) trainer).showSkillList(player);          
        else
            trainer.showSkillList(player, player.getSkillLearningClassId());

        if (_id >= 1368 && _id <= 1372) //if skill is expand sendpacket :)
        {
            ExStorageMaxCount esmc = new ExStorageMaxCount(player);
            player.sendPacket(esmc);
        }
    }
    
    /* (non-Javadoc)
     * @see net.sf.l2j.gameserver.clientpackets.ClientBasePacket#getType()
     */
    public String getType()
    {
        return _C__6C_REQUESTAQUIRESKILL;
    }
}
