package aelitis.azureus.core.dht.transport;

import aelitis.azureus.core.dht.control.impl.DHTTransportContact;

public interface DHTTransport {
	public DHTTransportContact getLocalContact();
	public void setRequestHandler(DHTTransportRequestHandler receiver);
}
