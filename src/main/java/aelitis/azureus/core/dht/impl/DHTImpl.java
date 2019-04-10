package aelitis.azureus.core.dht.impl;

import aelitis.azureus.core.dht.DHT;
import aelitis.azureus.core.dht.control.DHTControlFactory;
import aelitis.azureus.core.dht.control.impl.DHTControl;
import aelitis.azureus.core.dht.transport.DHTTransport;

public class DHTImpl implements DHT {
	
	DHTControl control;

	public DHTImpl(DHTTransport _transport) {
		int K = DHTControl.K_DEFAULT;
		int B = DHTControl.B_DEFAULT;
		control = DHTControlFactory.create(_transport, K, B);
	}
	
}
