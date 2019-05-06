package lbms.plugins.mldht.kad;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;
import java.util.Map;

import lbms.plugins.mldht.DHTConfiguration;
import lbms.plugins.mldht.kad.DHT.DHTtype;
import lbms.plugins.mldht.kad.DHT.LogLevel;

public class DHT {
	
	public static enum DHTtype {
		IPV4_DHT("IPv4",20+4+2, 4+2, Inet4Address.class,20+8),
		IPV6_DHT("IPv6",20+16+2, 16+2, Inet6Address.class,40+8);
		
		public final int							HEADER_LENGTH;
		public final int 							NODES_ENTRY_LENGTH;
		public final int							ADDRESS_ENTRY_LENGTH;
		public final Class<? extends InetAddress>	PREFERRED_ADDRESS_TYPE;
		public final String 						shortName;
		private DHTtype(String shortName, int nodeslength, int addresslength, Class<? extends InetAddress> addresstype, int header) {
			this.shortName = shortName;
			this.NODES_ENTRY_LENGTH = nodeslength;
			this.PREFERRED_ADDRESS_TYPE = addresstype;
			this.ADDRESS_ENTRY_LENGTH = addresslength;
			this.HEADER_LENGTH = header;
		}
	}
	
	private boolean running;
	private boolean stopped;

	Node node;
	
	public void start() {
		node = new Node(this);
	}
	
	public static enum LogLevel {
		Fatal, Error, Info, Debug, Verbose
	}

	public static Map<DHTtype, DHT> createDHTs() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void setLogLevel(LogLevel debug) {
		// TODO Auto-generated method stub
		
	}

	public void start(DHTConfiguration config, RPCServerListener serverListener)
			throws SocketException {
		// TODO Auto-generated method stub
		
	}

	public void bootstrap() {
		// TODO Auto-generated method stub
		
	}

	public void stop() {
		// TODO Auto-generated method stub
		
	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isDhtUpdateScheduleActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stopDhtUpdateSchedule() {
		// TODO Auto-generated method stub
		
	}

	public void startDhtUpdateSchedule() {
		// TODO Auto-generated method stub
		
	}

	public boolean isExpiredEntriesScheduleActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stopExpiredEntriesSchedule() {
		// TODO Auto-generated method stub
		
	}

	public void startExpiredEntriesSchedule() {
		// TODO Auto-generated method stub
		
	}

	public boolean isLookupScheduleActive() {
		// TODO Auto-generated method stub
		return false;
	}

	public void stopLookupSchedule() {
		// TODO Auto-generated method stub
		
	}

	public void startLookupSchedule() {
		// TODO Auto-generated method stub
		
	}
	
	private List<DHTStatsListener>		statsListeners;
	
	public void addStatsListener(DHTStatsListener listener) {
		statsListeners.add(listener);
	}

	public void removeStatsListener (DHTStatsListener listener) {
		statsListeners.remove(listener);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see lbms.plugins.mldht.kad.DHTBase#getNode()
	 */
	public Node getNode () {
		return node;
	}
	
	public Key getOurID() {
		if (running) {
			return node.getRootID();
		}
		return null;
	}
}
