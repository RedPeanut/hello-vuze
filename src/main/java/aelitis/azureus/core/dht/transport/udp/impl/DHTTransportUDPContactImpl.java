package aelitis.azureus.core.dht.transport.udp.impl;

import aelitis.azureus.core.dht.netcoords.DHTNetworkPosition;
import aelitis.azureus.core.dht.netcoords.DHTNetworkPositionManager;

public class DHTTransportUDPContactImpl {

	private DHTNetworkPosition[]		networkPositions;
	
	protected DHTTransportUDPContactImpl(boolean _isLocal) {
		createNetworkPositions(_isLocal);
	}
	
	public void createNetworkPositions(boolean isLocal) {
		networkPositions = DHTNetworkPositionManager.createPositions(isLocal);
	}
	
}
