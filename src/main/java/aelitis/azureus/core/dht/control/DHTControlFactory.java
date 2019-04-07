package aelitis.azureus.core.dht.control;

import aelitis.azureus.core.dht.control.impl.DHTControl;
import aelitis.azureus.core.dht.control.impl.DHTControlImpl;
import aelitis.azureus.core.dht.router.DHTRouter;

public class DHTControlFactory {
	
	public static DHTControl create(DHTRouter router, int K, int B) {
		return new DHTControlImpl(router, K, B);
	}
}
