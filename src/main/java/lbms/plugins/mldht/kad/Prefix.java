package lbms.plugins.mldht.kad;

public class Prefix extends Key {

	/**
	 * identifies the first bit of a key that has to be equal to be considered as covered by this prefix
	 * -1 = prefix matches whole keyspace
	 * 0 = 0th bit must match
	 * 1 = ...
	 */
	int depth = -1;
	
	public Prefix() {
		super();
	}
	
	public int getDepth() {
		return depth;
	}
}
