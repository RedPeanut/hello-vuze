package aelitis.azureus.core.dht.transport.udp.impl;

import java.net.InetSocketAddress;

import aelitis.azureus.core.dht.control.impl.DHTTransportContact;
import aelitis.azureus.core.dht.transport.DHTTransportRequestHandler;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDPContact;

public class DHTTransportUDPImpl implements DHTTransportUDP {
	
	private String externalAddress;
	
	private final byte protocolVersion;
	private int port;
	
	private DHTTransportRequestHandler requestHandler;
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

	@Override
	public DHTTransportUDPContact importContact(
			InetSocketAddress	_address,
			byte				_protocolVersion,
			boolean				isBootstrap) {
		DHTTransportUDPContactImpl contact = new DHTTransportUDPContactImpl(false, _address, _address, _protocolVersion);
		importContact(contact, isBootstrap);
		return contact;
	}
	
	protected void importContact(DHTTransportUDPContactImpl contact, boolean isBootstrap) {
		
	}

	@Override
	public void setRequestHandler(DHTTransportRequestHandler _requestHandler) {
		requestHandler = _requestHandler;
	}
}
