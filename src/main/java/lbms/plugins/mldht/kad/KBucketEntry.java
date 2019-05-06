package lbms.plugins.mldht.kad;

import java.net.InetSocketAddress;

import gudy.azureus2.core3.util.TimeFormatter;

public class KBucketEntry {
	
	private InetSocketAddress	addr;
	private Key					nodeID;
	private long				lastSeen;
	private int					failedQueries	= 0;
	private long				timeCreated;
	private String				version;
	
	/**
	 * @return the address of the node
	 */
	public InetSocketAddress getAddress () {
		return addr;
	}
	
	public boolean equals(Object o) {
		if (o instanceof KBucketEntry)
			return this.equals((KBucketEntry)o);
		return this == o;
	}

	/**
	 * violating the equals contract (specifically: the transitivity requirement) here, use with care
	 */
	public boolean equals (KBucketEntry other) {
		return nodeID.equals(other.nodeID) || addr.getAddress().equals(other.addr.getAddress());
	}

	/**
	 * overriding hash code to always return 0 because we can't compute a good one that represents that OR-comparison semantic between 
	 */
	@Override
	public int hashCode() {
		new Exception("KBucketEntry hashCode should not be used").printStackTrace();
		return 0;
	}

	/**
	 * @return id
	 */
	public Key getID() {
		return nodeID;
	}

	/**
     * @param version the version to set
     */
    public void setVersion(String version) {
	    this.version = version;
    }

	/**
     * @return the version
     */
    public String getVersion() {
	    return version;
    }

	/**
	 * @return the last_responded
	 */
	public long getLastSeen () {
		return lastSeen;
	}

	public long getCreationTime() {
		return timeCreated;
	}

	/**
	 * @return the failedQueries
	 */
	public int getFailedQueries () {
		return failedQueries;
	}
	
	public String toString() {
		long now = System.currentTimeMillis();
		return nodeID+"/"+addr+";seen:"+TimeFormatter.format((now-lastSeen)/1000)+";age:"+TimeFormatter.format((now-timeCreated) / 1000)+(failedQueries>0?";fail:"+failedQueries:"");
	}

	/**
	 * Checks if the node is Good.
	 *
	 * A node is considered Good if it has responded in the last 15 min
	 *
	 * @return true if the node as responded in the last 15 min.
	 */
	public boolean isGood() {
		return !isQuestionable();
	}
	
	/**
	 * Checks if a node is Questionable.
	 *
	 * A node is considered Questionable if it hasn't responded in the last 15 min
	 *
	 * @return true if peer hasn't responded in the last 15 min.
	 */
	public boolean isQuestionable() {
		return (System.currentTimeMillis() - lastSeen > DHTConstants.KBE_QUESTIONABLE_TIME || isBad());
	}
	
	/**
	 * Checks if a node is Bad.
	 *
	 * A node is bad if it isn't good and hasn't responded the last 3 queries, or
	 * failed 5 times. i
	 *
	 * @return true if bad
	 */
	public boolean isBad() {
		if (failedQueries >= DHTConstants.KBE_BAD_IMMEDIATLY_ON_FAILED_QUERIES) {
	        return true;
        }
		if (failedQueries > DHTConstants.KBE_BAD_IF_FAILED_QUERIES_LARGER_THAN && 
			System.currentTimeMillis() - lastSeen > DHTConstants.KBE_QUESTIONABLE_TIME) {
	        return true;
        }
		return false;
	}
}
