package aelitis.azureus.core.dht;

public interface DHT {
	
	// all property values are Integer values

	public static final String	PR_CONTACTS_PER_NODE				= "EntriesPerNode";
	public static final String	PR_NODE_SPLIT_FACTOR				= "NodeSplitFactor";
	public static final String	PR_SEARCH_CONCURRENCY				= "SearchConcurrency";
	public static final String	PR_LOOKUP_CONCURRENCY				= "LookupConcurrency";
	public static final String	PR_MAX_REPLACEMENTS_PER_NODE		= "ReplacementsPerNode";
	public static final String	PR_CACHE_AT_CLOSEST_N				= "CacheClosestN";
	public static final String	PR_ORIGINAL_REPUBLISH_INTERVAL		= "OriginalRepublishInterval";
	public static final String	PR_CACHE_REPUBLISH_INTERVAL			= "CacheRepublishInterval";
	public static final String	PR_ENCODE_KEYS						= "EncodeKeys";
	public static final String	PR_ENABLE_RANDOM_LOOKUP				= "EnableRandomLookup";
	
}
