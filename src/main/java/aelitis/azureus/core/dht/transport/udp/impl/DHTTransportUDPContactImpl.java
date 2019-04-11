package aelitis.azureus.core.dht.transport.udp.impl;

import java.net.InetSocketAddress;

import aelitis.azureus.core.dht.netcoords.DHTNetworkPosition;
import aelitis.azureus.core.dht.netcoords.DHTNetworkPositionManager;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDPContact;

public class DHTTransportUDPContactImpl implements DHTTransportUDPContact {

	private InetSocketAddress externalAddress;
	private InetSocketAddress transportAddress;

	private byte protocolVersion;
	
	private byte[] id;
	
	private DHTNetworkPosition[] networkPositions;
	
	protected DHTTransportUDPContactImpl(
			boolean _isLocal,
			InetSocketAddress _transportAddress,
			InetSocketAddress _externalAddress,
			byte _protocolVersion
	) {
		
		transportAddress = _transportAddress;
		externalAddress = _externalAddress;
		protocolVersion = _protocolVersion;
		
		if (	   transportAddress == externalAddress 
				|| transportAddress.getAddress().equals(externalAddress.getAddress())
		) {
			id = DHTUDPUtils.getNodeID(externalAddress);
		}
		
		createNetworkPositions(_isLocal);
	}
	
	public void createNetworkPositions(boolean isLocal) {
		networkPositions = DHTNetworkPositionManager.createPositions(isLocal);
	}

	@Override
	public byte[] getID() {
		return id;
	}
	
}
