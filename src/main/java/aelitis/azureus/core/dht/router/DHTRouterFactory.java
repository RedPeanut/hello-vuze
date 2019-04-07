package aelitis.azureus.core.dht.router;

import aelitis.azureus.core.dht.router.impl.DHTRouterImpl;

public class DHTRouterFactory {

	public static DHTRouter create(int K, int B) {
		DHTRouterImpl res = new DHTRouterImpl(K, B);

		return res;
	}

}
