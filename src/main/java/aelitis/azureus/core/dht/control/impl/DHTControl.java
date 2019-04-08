package aelitis.azureus.core.dht.control.impl;

public interface DHTControl {
	
	public static final int		K_DEFAULT								= 20;
	public static final int		B_DEFAULT								= 4;
	public static final int		MAX_REP_PER_NODE_DEFAULT				= 5;
	public static final int		SEARCH_CONCURRENCY_DEFAULT				= 5;
	public static final int		LOOKUP_CONCURRENCY_DEFAULT				= 10;
	public static final int		CACHE_AT_CLOSEST_N_DEFAULT				= 1;
	public static final int		ORIGINAL_REPUBLISH_INTERVAL_DEFAULT		= 8*60*60*1000;
	public static final int		CACHE_REPUBLISH_INTERVAL_DEFAULT		=   30*60*1000;
	public static final int		ENCODE_KEYS_DEFAULT						= 1;
	public static final int		ENABLE_RANDOM_DEFAULT					= 1;
	
}
