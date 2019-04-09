package aelitis.azureus.core.dht.transport.udp.impl;

import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;

public class DHTTransportUDPImpl implements DHTTransportUDP {
	
	private DHTTransportUDPContactImpl localContact;
	
	public DHTTransportUDPImpl() {
		localContact = new DHTTransportUDPContactImpl(true);
	}
}
