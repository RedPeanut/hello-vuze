/*
 * File    : PRUDPPacketReceiverFactory.java
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

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aelitis.net.udp.uc.impl.PRUDPPacketHandler;
import aelitis.net.udp.uc.impl.PRUDPPacketHandlerImpl;

/**
 * @author parg
 *
 */

import gudy.azureus2.core3.util.AEMonitor;
import gudy.azureus2.core3.util.Debug;

public class PRUDPPacketHandlerFactory {
	
	public static PRUDPPacketHandler getHandler(int port) {
		return (getHandler(port, null));
	}

	
	
	public static PRUDPPacketHandler getHandler(
		int						port,
		PRUDPRequestHandler		handler) {
		return (getHandler(port, null, handler));
	}

	/*public static PRUDPPacketHandler getHandler(
		int						port,
		InetAddress				bindIp,
		PRUDPRequestHandler		handler) {
		return (getHandler(port, bindIp, handler));
	}*/

	public static PRUDPReleasablePacketHandler getReleasableHandler(int port) {
		return (getReleasableHandler(port, null));
	}

	/*public static List<PRUDPPacketHandler> getHandlers() {
		return (getHandlers());
	}*/
	
	private static Map<Integer,PRUDPPacketHandlerImpl> receiverMap = new HashMap<>();

	private static AEMonitor			classMonitor	= new AEMonitor("PRUDPPHF");
	private static Map<Integer, List>	releasableMap = new HashMap<>();
	private static Set<Integer>			nonReleasableSet = new HashSet<>();

	public static List<PRUDPPacketHandler> getHandlers() {
		try {
			classMonitor.enter();
			return (new ArrayList<PRUDPPacketHandler>(receiverMap.values()));
		} finally {
			classMonitor.exit();
		}
	}

	public static PRUDPPacketHandler getHandler(
		int						port,
		InetAddress				bindIp,
		PRUDPRequestHandler		requestHandler) {
		
		final Integer f_port = new Integer(port);
		try {
			classMonitor.enter();
			nonReleasableSet.add(f_port);
			PRUDPPacketHandlerImpl receiver = receiverMap.get(f_port);
			if (receiver == null) {
				receiver = new PRUDPPacketHandlerImpl(port, bindIp, null);
				receiverMap.put(f_port, receiver);
			}
			
			// only set the incoming request handler if one has been specified. This is important when
			// the port is shared (e.g. default udp tracker and dht) and only one usage has need to handle
			// unsolicited inbound requests as we don't want the tracker null handler to erase the dht's
			// one
			if (requestHandler != null) {
				receiver.setRequestHandler(requestHandler);
			}
			return (receiver);
		} finally {
			classMonitor.exit();
		}
	}

	public static PRUDPReleasablePacketHandler getReleasableHandler(
		int						port,
		PRUDPRequestHandler		requestHandler) {
		
		final Integer	f_port = new Integer(port);
		
		try {
			classMonitor.enter();
			PRUDPPacketHandlerImpl receiver = (PRUDPPacketHandlerImpl)receiverMap.get(f_port);
			if (receiver == null) {
				receiver = new PRUDPPacketHandlerImpl(port, null, null);
				receiverMap.put(f_port, receiver);
			}
			
			// only set the incoming request handler if one has been specified. This is important when
			// the port is shared (e.g. default udp tracker and dht) and only one usage has need to handle
			// unsolicited inbound requests as we don't want the tracker null handler to erase the dht's
			// one
			if (requestHandler != null) {
				receiver.setRequestHandler(requestHandler);
			}
			
			final PRUDPPacketHandlerImpl f_receiver = receiver;
			final PRUDPReleasablePacketHandler rel =
				new PRUDPReleasablePacketHandler() {
				
					public PRUDPPacketHandler getHandler() {
						return (f_receiver);
					}
					
					public void release() {
						try {
							classMonitor.enter();
							List l = (List)releasableMap.get(f_port);
							if (l == null) {
								Debug.out("hmm");
							} else {
								if (!l.remove( this)) {
									Debug.out("hmm");
								} else {
									if (l.size() == 0) {
										if (!nonReleasableSet.contains( f_port)) {
											f_receiver.destroy();
											receiverMap.remove(f_port);
										}
										releasableMap.remove(f_port);
									}
								}
							}
						} finally {
							classMonitor.exit();
						}
					}
				};
			List l = (List)releasableMap.get(f_port);
			if (l == null) {
				l = new ArrayList();
				releasableMap.put(f_port, l);
			}
			l.add(rel);
			if (l.size() > 1024) {
				Debug.out("things going wrong here");
			}
			return (rel);
		} finally {
			classMonitor.exit();
		}
	}

}
