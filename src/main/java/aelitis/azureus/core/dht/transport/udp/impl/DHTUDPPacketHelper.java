package aelitis.azureus.core.dht.transport.udp.impl;

public class DHTUDPPacketHelper {
	public static final int		PACKET_MAX_BYTES		= 1400;

	// these actions have to co-exist with the tracker ones when the connection
	// is shared, hence 1024

	public static final int ACT_REQUEST_PING		= 1024; // 0x400
	public static final int ACT_REPLY_PING			= 1025;
	
	public static final int ACT_REQUEST_STORE		= 1026;
	public static final int ACT_REPLY_STORE			= 1027;
	
	public static final int ACT_REQUEST_FIND_NODE	= 1028; // 0x404
	public static final int ACT_REPLY_FIND_NODE		= 1029;
	
	public static final int ACT_REQUEST_FIND_VALUE	= 1030;
	public static final int ACT_REPLY_FIND_VALUE	= 1031;
	
	public static final int ACT_REPLY_ERROR			= 1032;
	public static final int ACT_REPLY_STATS			= 1033;
	public static final int ACT_REQUEST_STATS		= 1034;
	
	public static final int ACT_DATA				= 1035;
	
	public static final int ACT_REQUEST_KEY_BLOCK	= 1036;
	public static final int ACT_REPLY_KEY_BLOCK		= 1037;
	
	public static final int ACT_REQUEST_QUERY_STORE	= 1038;
	public static final int ACT_REPLY_QUERY_STORE	= 1039;
}
