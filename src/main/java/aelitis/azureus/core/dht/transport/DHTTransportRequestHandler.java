package aelitis.azureus.core.dht.transport;

import aelitis.azureus.core.dht.control.impl.DHTTransportContact;

public interface DHTTransportRequestHandler {
	public void contactImported(
			DHTTransportContact		contact,
			boolean					isBootstrap);
}
