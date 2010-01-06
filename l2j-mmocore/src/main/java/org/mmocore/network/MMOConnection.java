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
package org.mmocore.network;

import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.CancelledKeyException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.WritableByteChannel;

import javolution.util.FastList;

/**
 * @author KenM
 */
public abstract class MMOConnection<T extends MMOConnection<T>>
{
	private final SelectorThread<T> _selectorThread;
	private final Socket _socket;
	
	private FastList<SendablePacket<T>> _sendQueue;
	private final SelectionKey _selectionKey;
	
	private ByteBuffer _readBuffer;
	
	private ByteBuffer _primaryWriteBuffer;
	private ByteBuffer _secondaryWriteBuffer;
	
	private long _timeClosed = -1;
	
	protected MMOConnection(SelectorThread<T> selectorThread, Socket socket, SelectionKey key)
	{
		_selectorThread = selectorThread;
		_socket = socket;
		_selectionKey = key;
	}
	
	public synchronized void sendPacket(SendablePacket<T> sp)
	{
		if (isClosed())
			return;
		
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() | SelectionKey.OP_WRITE);
			getSendQueue2().addLast(sp);
		}
		catch (CancelledKeyException e)
		{
			// ignore
		}
	}
	
	private SelectorThread<T> getSelectorThread()
	{
		return _selectorThread;
	}
	
	SelectionKey getSelectionKey()
	{
		return _selectionKey;
	}
	
	void enableReadInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() | SelectionKey.OP_READ);
		}
		catch (CancelledKeyException e)
		{
			// ignore
		}
	}
	
	void disableReadInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() & ~SelectionKey.OP_READ);
		}
		catch (CancelledKeyException e)
		{
			// ignore
		}
	}
	
	void enableWriteInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() | SelectionKey.OP_WRITE);
		}
		catch (CancelledKeyException e)
		{
			// ignore
		}
	}
	
	void disableWriteInterest()
	{
		try
		{
			getSelectionKey().interestOps(getSelectionKey().interestOps() & ~SelectionKey.OP_WRITE);
		}
		catch (CancelledKeyException e)
		{
			// ignore
		}
	}
	
	public Socket getSocket()
	{
		return _socket;
	}
	
	WritableByteChannel getWritableChannel()
	{
		return _socket.getChannel();
	}
	
	ReadableByteChannel getReadableByteChannel()
	{
		return _socket.getChannel();
	}
	
	synchronized FastList<SendablePacket<T>> getSendQueue2()
	{
		if (_sendQueue == null)
			_sendQueue = new FastList<SendablePacket<T>>();
		
		return _sendQueue;
	}
	
	void createWriteBuffer(ByteBuffer buf)
	{
		if (_primaryWriteBuffer == null)
		{
			//System.err.println("APPENDING FOR NULL");
			//System.err.flush();
			_primaryWriteBuffer = getSelectorThread().getPooledBuffer();
			_primaryWriteBuffer.put(buf);
		}
		else
		{
			//System.err.println("PREPENDING ON EXISTING");
			//System.err.flush();
			
			ByteBuffer temp = getSelectorThread().getPooledBuffer();
			temp.put(buf);
			
			int remaining = temp.remaining();
			_primaryWriteBuffer.flip();
			int limit = _primaryWriteBuffer.limit();
			
			if (remaining >= _primaryWriteBuffer.remaining())
			{
				temp.put(_primaryWriteBuffer);
				getSelectorThread().recycleBuffer(_primaryWriteBuffer);
				_primaryWriteBuffer = temp;
			}
			else
			{
				_primaryWriteBuffer.limit(remaining);
				temp.put(_primaryWriteBuffer);
				_primaryWriteBuffer.limit(limit);
				_primaryWriteBuffer.compact();
				_secondaryWriteBuffer = _primaryWriteBuffer;
				_primaryWriteBuffer = temp;
			}
		}
	}
	
	boolean hasPendingWriteBuffer()
	{
		return _primaryWriteBuffer != null;
	}
	
	void movePendingWriteBufferTo(ByteBuffer dest)
	{
		//System.err.println("PRI SIZE: "+_primaryWriteBuffer.position());
		//System.err.flush();
		_primaryWriteBuffer.flip();
		dest.put(_primaryWriteBuffer);
		getSelectorThread().recycleBuffer(_primaryWriteBuffer);
		_primaryWriteBuffer = _secondaryWriteBuffer;
		_secondaryWriteBuffer = null;
	}
	
	void setReadBuffer(ByteBuffer buf)
	{
		_readBuffer = buf;
	}
	
	ByteBuffer getReadBuffer()
	{
		return _readBuffer;
	}
	
	boolean isClosed()
	{
		return _timeClosed != -1;
	}
	
	boolean closeTimeouted()
	{
		return System.currentTimeMillis() > _timeClosed + 10000;
	}
	
	public synchronized void closeNow()
	{
		if (isClosed())
			return;
		
		_timeClosed = System.currentTimeMillis();
		getSendQueue2().clear();
		disableWriteInterest();
		getSelectorThread().closeConnection(this);
	}
	
	public synchronized void close(SendablePacket<T> sp)
	{
		if (isClosed())
			return;
		
		getSendQueue2().clear();
		sendPacket(sp);
		_timeClosed = System.currentTimeMillis();
		getSelectorThread().closeConnection(this);
	}
	
	void releaseBuffers()
	{
		if (_primaryWriteBuffer != null)
		{
			getSelectorThread().recycleBuffer(_primaryWriteBuffer);
			_primaryWriteBuffer = null;
			if (_secondaryWriteBuffer != null)
			{
				getSelectorThread().recycleBuffer(_secondaryWriteBuffer);
				_secondaryWriteBuffer = null;
			}
		}
		
		if (_readBuffer != null)
		{
			getSelectorThread().recycleBuffer(_readBuffer);
			_readBuffer = null;
		}
	}
	
	protected abstract void onDisconnection();
	
	protected abstract void onForcedDisconnection();
	
	protected abstract boolean decrypt(ByteBuffer buf, int size);
	
	protected abstract boolean encrypt(ByteBuffer buf, int size);
}
