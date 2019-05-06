package lbms.plugins.mldht.kad;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KBucket implements Externalizable {
	
	/**
	 * use {@link #insertOrRefresh}, {@link #sortedInsert} or {@link #removeEntry} to handle this<br>
	 * using copy-on-write semantics for this list, referencing it is safe if you make local copy 
	 */
	private volatile List<KBucketEntry>	entries;
	// replacements are synchronized on entries, not on replacementBucket! using a liked list since we use it as queue, not as stack
	private ConcurrentLinkedQueue<KBucketEntry>	replacementBucket;
	
	private Node node;
	
	public KBucket(Node node) {
		this.node = node;
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
	}
	
	/**
	 * @return the entries
	 */
	public List<KBucketEntry> getEntries () {
		return new ArrayList<KBucketEntry>(entries);
	}
	
	public List<KBucketEntry> getReplacementEntries() {
		return new ArrayList<KBucketEntry>(replacementBucket);
	}
}
