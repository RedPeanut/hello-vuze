package aelitis.azureus.core.dht.transport;

import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;
import aelitis.azureus.core.dht.transport.udp.impl.DHTTransportUDPImpl;

public class DHTTransportFactory {

	public static DHTTransportUDP createUDP(
			byte			protocolVersion,
			String			defaultIp,
			int				port
	) {
		return new DHTTransportUDPImpl(
				protocolVersion,
				defaultIp,
				port
		);
	}

}
