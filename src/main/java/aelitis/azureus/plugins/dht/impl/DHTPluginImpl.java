package aelitis.azureus.plugins.dht.impl;

import aelitis.azureus.core.dht.transport.DHTTransportFactory;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;

public class DHTPluginImpl {
	
	private DHTTransportUDP transport;
	
	public DHTPluginImpl() {
		transport = DHTTransportFactory.createUDP();
	}
}
