package aelitis.azureus.core.dht.db;

import aelitis.azureus.core.dht.db.impl.DHTDBImpl;

public class DHTDBFactory {

	public static DHTDB create() {
		return new DHTDBImpl();
	}

}
