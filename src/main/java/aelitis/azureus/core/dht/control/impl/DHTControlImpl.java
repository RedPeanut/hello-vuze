package aelitis.azureus.core.dht.control.impl;

import aelitis.azureus.core.dht.router.DHTRouter;
import aelitis.azureus.core.dht.router.DHTRouterFactory;
import gudy.azureus2.core3.util.ThreadPoolTask;

public class DHTControlImpl implements DHTControl {
	
	final int K;
	private final int B;
	private DHTRouter router;

	public DHTControlImpl(DHTRouter _router, int _K, int _B) {
		K = _K;
		B = _B;
		router = _router;
	}
	
	protected void createRouter() {
		router = DHTRouterFactory.create(K, B);
	}
	
	protected DhtTask lookup() {
		
		DhtTask	task = new DhtTask() {

			@Override
			public void runSupport() {
			}
			
		};
		return task;
	}
	
	protected abstract class DhtTask extends ThreadPoolTask {
		//protected DhtTask(ThreadPool threadPool) {}
	}
	
	// 
	
	public int computeAndCompareDistances(byte[] t1, byte[] t2, byte[] pivot) {
		return (computeAndCompareDistances2(t1, t2, pivot));
	}

	protected static int computeAndCompareDistances2(byte[] t1, byte[] t2, byte[] pivot) {
		for (int i = 0; i < t1.length; i++) {
			byte d1 = (byte) (t1[i] ^ pivot[i]);
			byte d2 = (byte) (t2[i] ^ pivot[i]);
			int diff = (d1 & 0xff) - (d2 & 0xff);
			if (diff != 0) {
				return (diff);
			}
		}
		return (0);
	}

	public byte[] computeDistance(byte[] n1, byte[] n2) {
		return (computeDistance2(n1, n2));
	}

	protected static byte[] computeDistance2(byte[] n1, byte[] n2) {
		byte[] res = new byte[n1.length];
		for (int i = 0; i < res.length; i++) {
			res[i] = (byte) (n1[i] ^ n2[i]);
		}
		return (res);
	}
	
	/**
	 * -ve -> n1 < n2
	 * @param n1
	 * @param n2
	 * @return
	 */
	public int compareDistances(byte[] n1, byte[] n2) {
		return (compareDistances2(n1, n2));
	}

	protected static int compareDistances2(byte[] n1, byte[] n2) {
		for (int i=0;i<n1.length;i++) {
			int diff = (n1[i]&0xff) - (n2[i]&0xff);
			if (diff != 0) {
				return (diff);
			}
		}
		return (0);
	}
}
