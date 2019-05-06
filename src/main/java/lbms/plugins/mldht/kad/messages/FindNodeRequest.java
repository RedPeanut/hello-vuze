package lbms.plugins.mldht.kad.messages;

import lbms.plugins.mldht.kad.Key;
import lbms.plugins.mldht.kad.messages.MessageBase.Method;

public class FindNodeRequest extends AbstractLookupRequest {

	public FindNodeRequest(Key target) {
		super(target, Method.FIND_NODE);
	}

}
