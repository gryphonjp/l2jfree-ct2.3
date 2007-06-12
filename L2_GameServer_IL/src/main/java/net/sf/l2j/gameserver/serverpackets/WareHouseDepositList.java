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
package net.sf.l2j.gameserver.serverpackets;

import javolution.util.FastList;

import net.sf.l2j.gameserver.model.L2ItemInstance;
import net.sf.l2j.gameserver.model.actor.instance.L2PcInstance;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 0x53 WareHouseDepositList  dh (h dddhh dhhh d)
 * 
 * @version $Revision: 1.4.2.1.2.4 $ $Date: 2005/03/27 15:29:39 $
 */
public class WareHouseDepositList extends L2GameServerPacket
{
	public static final int Private = 1;
	public static final int Clan = 2;
	public static final int Castle = 3; //not sure
	public static final int Freight = 4; //not sure
	private static Log _log = LogFactory.getLog(WareHouseDepositList.class.getName());
	private static final String _S__53_WAREHOUSEDEPOSITLIST = "[S] 41 WareHouseDepositList";
	private L2PcInstance _player;
	private int _playerAdena;
	private FastList<L2ItemInstance> _items;
	private int _whtype;

	public WareHouseDepositList(L2PcInstance player, int type)
	{
        _player = player;
		_whtype = type;
		_playerAdena = _player.getAdena();
		_items = new FastList<L2ItemInstance>();
		
		for (L2ItemInstance temp : _player.getInventory().getAvailableItems(true))
			_items.add(temp);
		
		// augmented and shadow items can be stored in private wh
		if (_whtype == Private)
		{
			for (L2ItemInstance temp :player.getInventory().getItems())
			{
				if (temp != null && !temp.isEquipped() && (temp.isShadowItem() || temp.isAugmented()))
					_items.add(temp);
			}
		}
	}
	
	protected final void writeImpl()
	{
		writeC(0x41);
		/* 0x01-Private Warehouse  
        * 0x02-Clan Warehouse  
        * 0x03-Castle Warehouse  
        * 0x04-Warehouse */  
        writeH(_whtype);        
		writeD(_playerAdena); 
		int count = _items.size();
		if (_log.isDebugEnabled()) _log.debug("count:"+count);
		writeH(count);
		
		for (L2ItemInstance item : _items)
		{
			writeH(item.getItem().getType1()); // item type1 //unconfirmed, works
			writeD(item.getObjectId()); //unconfirmed, works
			writeD(item.getItemId()); //unconfirmed, works
			writeD(item.getCount()); //unconfirmed, works
			writeH(item.getItem().getType2());	// item type2 //unconfirmed, works
			writeH(0x00);	// ? 100
			writeD(item.getItem().getBodyPart());	// ?
			writeH(item.getEnchantLevel());	// enchant level -confirmed
			writeH(0x00);	// ? 300
			writeH(0x00);	// ? 200
			writeD(item.getObjectId()); // item id - confimed
			if (item.isAugmented())
			{
				writeD(0x0000FFFF&item.getAugmentation().getAugmentationId());
				writeD(item.getAugmentation().getAugmentationId()>>16);
			}
			else writeQ(0x00);
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.l2j.gameserver.serverpackets.ServerBasePacket#getType()
	 */
	public String getType()
	{
		return _S__53_WAREHOUSEDEPOSITLIST;
	}
}
