/*
 * File    : PRUDPPacketHandlerRequest.java
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

package aelitis.net.udp.uc.impl;

import java.net.InetSocketAddress;

import aelitis.net.udp.uc.PRUDPPacket;
import aelitis.net.udp.uc.PRUDPPacketHandlerException;
import aelitis.net.udp.uc.PRUDPPacketHandlerRequest;
import aelitis.net.udp.uc.PRUDPPacketReceiver;
import gudy.azureus2.core3.util.AEMonitor;

/**
 * @author parg
 *
 */

import gudy.azureus2.core3.util.AESemaphore;
import gudy.azureus2.core3.util.SystemTime;

public class PRUDPPacketHandlerRequestImpl
	implements PRUDPPacketHandlerRequest {
	
	private AEMonitor monitor = new AEMonitor("PRUDPPacketHandlerRequest");

	private long						timeout;
	private PRUDPPacketReceiver			receiver;

	private PRUDPPacketHandlerException	exception;
	private PRUDPPacket					reply;

	private long						send_time;
	private long						reply_time;

	protected
	PRUDPPacketHandlerRequestImpl(
		PRUDPPacketReceiver	_receiver,
		long				_timeout) {
		receiver	= _receiver;
		timeout		= _timeout;
	}

	protected void sent() {
		send_time = SystemTime.getCurrentTime();
		
	}

	protected long getSendTime() {
		return (send_time);
	}

	protected long getTimeout() {
		return (timeout);
	}

	public long getElapsedTime() {
		if (send_time == 0 || reply_time == 0) {
			return (-1);
		}
		long	res = reply_time - send_time;
		if (res < 0) {
			res	= 0;
		}
			// do something sensible with 0 time!
		if (res == 0) {
			res = SystemTime.TIME_GRANULARITY_MILLIS / 2;
		}
		return (res);
	}

	protected void setReply(
			PRUDPPacket packet, 
			InetSocketAddress originator, 
			long receiveTime) {
		if (reply == null) {
			reply_time = receiveTime;
			reply = packet;
		} else {
			packet.setPreviousPacket(reply);
			reply = packet;
		}
		if (!packet.hasContinuation()) {
			//sem.release();
			monitor.exit();
		}
		if (receiver != null) {
			receiver.packetReceived(this, packet, originator);
		}
	}

	protected void setException(PRUDPPacketHandlerException e) {
		// don't override existing reply for synchronous callers as they can
		// do what they want with it
		if (reply == null) {
			reply_time	= SystemTime.getCurrentTime();
			exception	= e;
		}
		//sem.release();
		monitor.exit();
		// still report errors to asyn clients (even when a reply has been received)
		// as they need something to indicate that a continuation packet wasn't received
		// and that the request has timed-out. ie. a multi-packet reply must terminate
		// either with the reception of a non-continuation (i.e. last) packet *or* a
		// timeout/error
		if (receiver != null) {
			receiver.error(e);
		}
	}

	protected PRUDPPacket getReply() throws PRUDPPacketHandlerException {
		//sem.reserve();
		monitor.enter();
		if (exception != null) {
			throw (exception);
		}
		return (reply);
	}
}
