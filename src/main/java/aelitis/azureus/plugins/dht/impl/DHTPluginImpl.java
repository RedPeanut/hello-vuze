package aelitis.azureus.plugins.dht.impl;

import java.net.InetSocketAddress;
import java.util.Properties;

import aelitis.azureus.core.dht.DHT;
import aelitis.azureus.core.dht.DHTFactory;
import aelitis.azureus.core.dht.control.impl.DHTTransportContact;
import aelitis.azureus.core.dht.transport.DHTTransportFactory;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;
import hello.util.Log;
import hello.util.NetworkUtil;

public class DHTPluginImpl {
	
	private static String TAG = DHTPluginImpl.class.getSimpleName();

	public static final String SEED_ADDRESS_V4 = "dht.vuze.com";
	public static final int SEED_PORT = 6881;
	
	private DHT dht;
	private int port;
	private byte protocolVersion;
	private DHTTransportUDP transport;
	
	public DHTPluginImpl(
			byte	_protocolVersion,
			int		_port
	) {

		protocolVersion = _protocolVersion;
		port = _port;
		
		String externalAddress = NetworkUtil.getExternalAddress();
		Log.d(TAG, "externalAddress = " + externalAddress);
		transport = DHTTransportFactory.createUDP(
				_protocolVersion,
				externalAddress,
				_port
		);

		Properties props = new Properties();
		dht = DHTFactory.create(transport);
		
		importRootSeed();
		
	}
	
	protected DHTTransportContact importRootSeed() {
		InetSocketAddress seedAddress = new InetSocketAddress(SEED_ADDRESS_V4, SEED_PORT);
		return importSeed(seedAddress);
	}
	
	protected DHTTransportContact importSeed(InetSocketAddress ia) {
		return (transport.importContact(ia, protocolVersion, true));
	}
}
