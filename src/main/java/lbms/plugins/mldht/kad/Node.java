package lbms.plugins.mldht.kad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lbms.plugins.mldht.kad.Node.RoutingTableEntry;

public class Node {

	public static class RoutingTableEntry {
		
		public final Prefix prefix;
		private KBucket bucket;
		
		public RoutingTableEntry(Prefix prefix, KBucket bucket) { 
			this.prefix = prefix;
			this.bucket = bucket;
		}
		
		public KBucket getBucket() {
			return bucket;
		}
	}
	
	private volatile List<RoutingTableEntry> routingTable = new ArrayList<RoutingTableEntry>();
	private DHT dht;
	
	private static Map<String,Serializable> dataStore;
	
	public Node(DHT dht) {
		this.dht = dht;
		routingTable.add(new RoutingTableEntry(new Prefix(), new KBucket(this)));
	}

	public List<RoutingTableEntry> getBuckets () {
		return Collections.unmodifiableList(routingTable) ;
	}
	
	/**
	 * @return OurID
	 */
	public Key getRootID () {
		if (dataStore != null)
			return (Key)dataStore.get("commonKey");
		// return a fake key if we're not initialized yet
		return Key.MIN_KEY;
	}
	
}
