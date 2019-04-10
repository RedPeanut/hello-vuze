package aelitis.azureus.core.dht.control;

import aelitis.azureus.core.dht.control.impl.DHTControl;
import aelitis.azureus.core.dht.control.impl.DHTControlImpl;
import aelitis.azureus.core.dht.transport.DHTTransport;

public class DHTControlFactory {
	
	public static DHTControl create(DHTTransport transport, int K, int B) {
		return new DHTControlImpl(transport, K, B);
	}
}
