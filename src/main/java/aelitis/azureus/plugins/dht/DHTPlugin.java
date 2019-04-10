package aelitis.azureus.plugins.dht;

import gudy.azureus2.core3.util.RandomUtils;
import hello.util.Log;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;
import aelitis.azureus.plugins.dht.impl.DHTPluginImpl;

public class DHTPlugin {
	
	private static String TAG = DHTPlugin.class.getSimpleName();
	
	private int dhtDataPort;
	
	public void initialize() {
		dhtDataPort = RandomUtils.generateRandomNetworkListenPort();
		Log.d(TAG, ">>> dhtDataPort = " + dhtDataPort);
	}
	
	public void initComplete() {
		Thread t = new Thread("DhtPlugin.init") {
			@Override
			public void run() {
				new DHTPluginImpl(
						DHTTransportUDP.PROTOCOL_VERSION_MAIN,
						dhtDataPort
				);
			}
		};
		t.start();
	}
}
