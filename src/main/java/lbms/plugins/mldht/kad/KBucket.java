package lbms.plugins.mldht.kad;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class KBucket implements Externalizable {

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
	
}
