package aelitis.azureus.plugins.dht.impl;

import aelitis.azureus.core.dht.DHT;
import aelitis.azureus.core.dht.DHTFactory;
import aelitis.azureus.core.dht.transport.DHTTransportFactory;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;
import hello.util.Log;
import hello.util.NetworkUtil;

public class DHTPluginImpl {
	
	private static String TAG = DHTPluginImpl.class.getSimpleName();

	private DHT dht;
	private DHTTransportUDP transport;
	
	public DHTPluginImpl(
			byte	_protocolVersion,
			int		_port
	) {
		String externalAddress = NetworkUtil.getExternalAddress();
		Log.d(TAG, "externalAddress = " + externalAddress);
		transport = DHTTransportFactory.createUDP(
				_protocolVersion,
				externalAddress,
				_port
		);
		
		dht = DHTFactory.create(transport);
	}
	
	
}
