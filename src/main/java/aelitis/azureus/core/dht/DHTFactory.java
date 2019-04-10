package aelitis.azureus.core.dht;

import aelitis.azureus.core.dht.impl.DHTImpl;
import aelitis.azureus.core.dht.transport.DHTTransport;

public class DHTFactory {

	public static DHT create(
			DHTTransport transport
	) {
		return new DHTImpl(transport);
	}
	
}
