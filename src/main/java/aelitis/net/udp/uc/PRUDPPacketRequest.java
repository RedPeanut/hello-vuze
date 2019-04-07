/*
 * File    : PRUDPPacketRequest.java
 * Created : 20-Jan-2004
 * By      : parg
 *
 * Azureus - a Java Bittorrent client
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (see the LICENSE file).
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package aelitis.net.udp.uc;

/**
 * @author parg
 *
 */

import gudy.azureus2.core3.util.AEMonitor;
import gudy.azureus2.core3.util.Debug;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import aelitis.net.udp.uc.impl.PRUDPPacketHandler;

public abstract class PRUDPPacketRequest extends PRUDPPacket {
	
	public static final int	PR_HEADER_SIZE	= 16;

	private static AEMonitor	classMonitor = new AEMonitor("PRUDPPacketRequest:class");

	private static Map	packetDecoders	= new HashMap();

	private long		connectionId;
	private long		receive_time;

	public static void registerDecoders(Map _decoders) {
		try {
			classMonitor.enter();
			Map	new_decoders = new HashMap(packetDecoders);
			Iterator	it = _decoders.keySet().iterator();
			while (it.hasNext()) {
				Integer action = (Integer)it.next();
				if (packetDecoders.containsKey( action)) {
					Debug.out("Duplicate codec! " + action);
				}
			}
			new_decoders.putAll(_decoders);
			packetDecoders	= new_decoders;
		} finally {
			classMonitor.exit();
		}
	}

	public PRUDPPacketRequest(
		int		_action,
		long	_conId) {
		super(_action);
		connectionId	= _conId;
	}

	protected PRUDPPacketRequest(
		int		_action,
		long	_conId,
		int		_transId) {
		super(_action, _transId);
		connectionId	= _conId;
	}

	public long	getConnectionId() {
		return (connectionId);
	}

	public long	getReceiveTime() {
		return (receive_time);
	}

	public void setReceiveTime(long _rt) {
		receive_time = _rt;
	}

	public void serialise(DataOutputStream os)
		throws IOException {
		// add to this and you need to adjust HEADER_SIZE above
		os.writeLong(connectionId);
		os.writeInt(getAction());
		os.writeInt(getTransactionId());
	}

	public static PRUDPPacketRequest deserialiseRequest(
		PRUDPPacketHandler	handler,
		DataInputStream		is )
		throws IOException
	{
		long		connection_id 	= is.readLong();
		int			action			= is.readInt();
		int			transaction_id	= is.readInt();
		PRUDPPacketRequestDecoder	decoder = (PRUDPPacketRequestDecoder)packetDecoders.get(new Integer( action));
		if (decoder == null) {
			throw(new IOException( "No decoder registered for action '" + action + "'"));
		}
		return (decoder.decode( handler, is, connection_id, action, transaction_id));
	}

	public String getString() {
		return (super.getString() + ":request[con=" + connectionId + ",trans=" + getTransactionId() + "]");
	}
}
