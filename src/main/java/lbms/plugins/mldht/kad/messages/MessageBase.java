package lbms.plugins.mldht.kad.messages;

import java.net.InetSocketAddress;

import lbms.plugins.mldht.kad.RPCServer;

public class MessageBase {
	
	protected byte[]			mtid;
	protected Method			method;
	protected Type				type;
	//protected Key				id;
	protected InetSocketAddress	origin;
	protected String			version;
	protected RPCServer			srv;
	
	/// Get the type of the message
	public Type getType() {
		return type;
	}

	/// Get the message it's method
	public Method getMethod() {
		return method;
	}
		
	public static enum Type {
		REQ_MSG {
			String innerKey() {	return "a";	}
			String getRPCTypeName() { return "q"; }
		}, RSP_MSG {
			String innerKey() {	return "r";	}
			String getRPCTypeName() { return "r"; }
		}, ERR_MSG {
			String getRPCTypeName() { return "e"; }
			String innerKey() {	return "e";	}
		}, INVALID;
		
		String innerKey() {
			return null;
		}
		
		String getRPCTypeName()	{
			return null;
		}
		
		public static final String TYPE_KEY = "y";
	};
	
	public static enum Method {
		PING, FIND_NODE, GET_PEERS, ANNOUNCE_PEER, NONE;
		
		String getRPCName()	{
			return name().toLowerCase();						
		}
	};
	
}
