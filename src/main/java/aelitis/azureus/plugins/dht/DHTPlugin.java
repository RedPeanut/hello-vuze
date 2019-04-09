package aelitis.azureus.plugins.dht;

import aelitis.azureus.plugins.dht.impl.DHTPluginImpl;

public class DHTPlugin {
	
	protected void initComplete() {
		Thread t = new Thread("DhtPlugin.init") {
			@Override
			public void run() {
				new DHTPluginImpl();
			}
		};
		t.start();
	}
}
