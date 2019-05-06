package lbms.plugins.mldht.kad;

public class DHTConstants {
	
	public static final int		MAX_ENTRIES_PER_BUCKET					= 8;
	public static final int		RECEIVE_BUFFER_SIZE						= 5 * 1024;
	
	public static final long	KBE_QUESTIONABLE_TIME					= 15 * 60 * 1000;	// 15min
	public static final int		KBE_BAD_IF_FAILED_QUERIES_LARGER_THAN	= 2;
	public static final int		KBE_BAD_IMMEDIATLY_ON_FAILED_QUERIES	= 8;
}
