package lbms.plugins.mldht.kad.messages;

import lbms.plugins.mldht.kad.Key;
import lbms.plugins.mldht.kad.messages.MessageBase.Type;

public abstract class AbstractLookupRequest extends MessageBase {

	protected Key target;

	public AbstractLookupRequest(Key target, Method m) {
		super(new byte[] {(byte) 0xFF}, m, Type.REQ_MSG);
		this.target = target;
	}

}
