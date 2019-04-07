package aelitis.azureus.core.dht.router.impl;

import java.util.List;

public class DHTRouterNodeImpl {
	
	private final DHTRouterImpl router;
	private final int depth;
	private final boolean containsRouterNodeId;

	private List<DHTRouterContactImpl> buckets;

	private DHTRouterNodeImpl left;
	private DHTRouterNodeImpl right;
	
	public DHTRouterNodeImpl(
			DHTRouterImpl _router, 
			int _depth,
			boolean _containsRouterNodeId,
			List<DHTRouterContactImpl> _buckets) {
		router = _router;
		depth = _depth;
		containsRouterNodeId = _containsRouterNodeId;
		buckets = _buckets;
	}
	
	protected int getDepth() {
		return (depth);
	}

	protected boolean containsRouterNodeID() {
		return (containsRouterNodeId);
	}

	protected DHTRouterNodeImpl getLeft() {
		return (left);
	}

	protected DHTRouterNodeImpl getRight() {
		return (right);
	}

	public List<DHTRouterContactImpl> getBuckets() {
		// TODO Auto-generated method stub
		return null;
	}
}
