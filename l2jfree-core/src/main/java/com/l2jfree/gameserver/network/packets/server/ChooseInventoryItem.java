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
package com.l2jfree.gameserver.network.packets.server;

import com.l2jfree.gameserver.network.packets.L2ServerPacket;

public class ChooseInventoryItem extends L2ServerPacket
{
	private static final String _S__7C_CHOOSEINVENTORYITEM = "[S] 7c ChooseInventoryItem [d]";
	
	private final int _itemId;
	
	public ChooseInventoryItem(int Item)
	{
		_itemId = Item;
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x7c);
		writeD(_itemId);
	}
	
	/* (non-Javadoc)
	 * @see com.l2jfree.gameserver.serverpackets.ServerBasePacket#getType()
	 */
	@Override
	public String getType()
	{
		return _S__7C_CHOOSEINVENTORYITEM;
	}
}
