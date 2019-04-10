package aelitis.azureus.plugins.dht.impl;

import hello.util.NetworkUtil;
import aelitis.azureus.core.dht.transport.DHTTransportFactory;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;

public class DHTPluginImpl {
	
	private DHTTransportUDP transport;
	
	public DHTPluginImpl(
			byte	_protocolVersion,
			int		_port
	) {
		String externalAddress = NetworkUtil.getExternalAddress();
		transport = DHTTransportFactory.createUDP(
				_protocolVersion,
				externalAddress,
				_port
		);
	}
	
	
}
