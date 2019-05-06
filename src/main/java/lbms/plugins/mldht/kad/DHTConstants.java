package lbms.plugins.mldht.kad;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

public class DHTConstants {
	
	public static final int		MAX_ENTRIES_PER_BUCKET					= 8;
	public static final int		RECEIVE_BUFFER_SIZE						= 5 * 1024;
	
	public static final long	KBE_QUESTIONABLE_TIME					= 15 * 60 * 1000;	// 15min
	public static final int		KBE_BAD_IF_FAILED_QUERIES_LARGER_THAN	= 2;
	public static final int		KBE_BAD_IMMEDIATLY_ON_FAILED_QUERIES	= 8;
	
	public static final String[]			BOOTSTRAP_NODES				= new String[]	{ "mldht.wifi.pps.univ-paris-diderot.fr", 	"router.bittorrent.com" };
	public static final int[]				BOOTSTRAP_PORTS				= new int[]		{ 6881, 						6881 };
	public static List<InetSocketAddress>	BOOTSTRAP_NODE_ADDRESSES	= Collections.EMPTY_LIST;
	private static String version = "AZ00";

	public static String getVersion() {
		return version;
	}
}
