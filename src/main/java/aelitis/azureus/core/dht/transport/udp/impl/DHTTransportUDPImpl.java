package aelitis.azureus.core.dht.transport.udp.impl;

import java.net.InetSocketAddress;

import aelitis.azureus.core.dht.control.impl.DHTTransportContact;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;

public class DHTTransportUDPImpl implements DHTTransportUDP {
	
	private String externalAddress;
	
	private final byte protocolVersion;
	private int port;
	
	private DHTTransportUDPContactImpl localContact;
	
	public DHTTransportUDPImpl(
			byte _protocolVersion,
			String _defaultIp,
			int _port) {
		
		protocolVersion = _protocolVersion;
		port = _port;
		
		String defaultIp = _defaultIp==null?"127.0.0.1":_defaultIp;
		externalAddress = defaultIp;
		InetSocketAddress address = new InetSocketAddress(externalAddress, port);
		
		localContact = new DHTTransportUDPContactImpl(
				true,
				address,	// transport
				address,	// external
				protocolVersion
		);
	}

	@Override
	public DHTTransportContact getLocalContact() {
		return localContact;
	}
}
