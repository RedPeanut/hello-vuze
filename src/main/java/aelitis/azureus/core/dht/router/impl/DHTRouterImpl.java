package aelitis.azureus.core.dht.router.impl;

import java.util.ArrayList;
import java.util.List;

import aelitis.azureus.core.dht.router.DHTRouter;
import aelitis.azureus.core.dht.router.DHTRouterContact;
import gudy.azureus2.core3.util.SimpleTimer;
import gudy.azureus2.core3.util.TimerEvent;
import gudy.azureus2.core3.util.TimerEventPerformer;
import gudy.azureus2.core3.util.TimerEventPeriodic;

public class DHTRouterImpl implements DHTRouter {

	private String TAG = DHTRouterImpl.class.getSimpleName();

	private int K;
	private int B;

	private DHTRouterNodeImpl root;

	private TimerEvent periodicEvent;
	private volatile int seedInTicks;

	private static final int TICK_PERIOD = 10 * 1000;
	
	public DHTRouterImpl(int _K, int _B) {
		K = _K;
		B = _B;
		
		List<DHTRouterContactImpl> buckets = new ArrayList<>();
		root = new DHTRouterNodeImpl(this, 0, true, buckets);
		
		periodicEvent = SimpleTimer.addPeriodicEvent(
			"DHTRouter:pinger",
			TICK_PERIOD,
			new TimerEventPerformer() {
				public void perform(TimerEvent event) {
					
				}
			}
		);
	}

	public List<DHTRouterContact> findClosestContacts(
			byte[]		nodeId,
			int			numToReturn,
			boolean		liveOnly) {
		List<DHTRouterContact> res = new ArrayList<>();
		findClosestContacts(nodeId, numToReturn, 0, root, liveOnly, res);
		return res;
	}
	
	protected void findClosestContacts(
			byte[]					nodeId,
			int						numToReturn,
			int						depth,
			DHTRouterNodeImpl		currentNode,
			boolean					liveOnly,
			List<DHTRouterContact>	res) {
		
		List<DHTRouterContactImpl> buckets = currentNode.getBuckets();
		if (buckets != null) {
			// add everything from the buckets - caller will sort and select
			// the best ones as required
			
		} else {
			boolean bit = (
				(nodeId[depth/8] >> (	// 
						7-(depth%8)		// 
					)
				) & 0x01
			) == 1;
			DHTRouterNodeImpl bestNode;
			DHTRouterNodeImpl worseNode;
			if (bit) {
				bestNode = currentNode.getLeft();
				worseNode = currentNode.getRight();
			} else {
				bestNode = currentNode.getRight();
				worseNode = currentNode.getLeft();
			}
			findClosestContacts(nodeId, numToReturn, depth+1, bestNode, liveOnly, res);
			if (res.size() < numToReturn) {
				findClosestContacts(nodeId, numToReturn, depth+1, worseNode, liveOnly, res);
			}
		}
	}
}
