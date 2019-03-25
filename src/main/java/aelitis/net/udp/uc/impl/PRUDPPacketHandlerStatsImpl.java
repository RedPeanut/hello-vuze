/*
 * Created on 25-Jan-2005
 * Created by Paul Gardner
 * Copyright (C) Azureus Software, Inc, All Rights Reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package aelitis.net.udp.uc.impl;

import gudy.azureus2.core3.util.Debug;
import aelitis.net.udp.uc.PRUDPPacketHandlerStats;

/**
 * @author parg
 *
 */

public class PRUDPPacketHandlerStatsImpl
	implements PRUDPPacketHandlerStats, Cloneable {
	
	private PRUDPPacketHandlerImpl packetHandler;

	private long packetsSent;
	private long packetsReceived;
	private long requests_timeout;
	private long bytes_sent;
	private long bytes_received;

	protected PRUDPPacketHandlerStatsImpl(PRUDPPacketHandlerImpl _packet_handler) {
		packetHandler = _packet_handler;
	}

	public long getPacketsSent() {
		return (packetsSent);
	}

	protected void packetSent(int len) {
		packetsSent++;
		bytes_sent += len;
	}

	public long getPacketsReceived() {
		return (packetsReceived);
	}

	protected void packetReceived(int len) {
		packetsReceived++;
		bytes_received += len;
	}

	protected void primordialPacketSent(int len) {
	}

	protected void primordialPacketReceived(int len) {
	}

	public long getRequestsTimedOut() {
		return (requests_timeout);
	}

	protected void requestTimedOut() {
		requests_timeout++;
	}

	public long getBytesSent() {
		return (bytes_sent);
	}

	public long getBytesReceived() {
		return (bytes_received);
	}

	public long getSendQueueLength() {
		return (packetHandler.getSendQueueLength());
	}

	public long getReceiveQueueLength() {
		return (packetHandler.getReceiveQueueLength());

	}

	public PRUDPPacketHandlerStats
	snapshot() {
		try {
			return ((PRUDPPacketHandlerStats)clone());

		} catch (CloneNotSupportedException e) {

			Debug.printStackTrace(e);

			return (null);
		}
	}
}
