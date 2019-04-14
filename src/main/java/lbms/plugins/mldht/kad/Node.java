package lbms.plugins.mldht.kad;

import java.util.ArrayList;
import java.util.List;

public class Node {

	public static class RoutingTableEntry {
		
		private KBucket bucket;
		
		public RoutingTableEntry(KBucket bucket) { 
			this.bucket = bucket;
		}
	}
	
	private volatile List<RoutingTableEntry> routingTable = new ArrayList<RoutingTableEntry>();
	private DHT dht;
	
	public Node(DHT dht) {
		this.dht = dht;
		routingTable.add(new RoutingTableEntry(new KBucket(this)));
	}

}
