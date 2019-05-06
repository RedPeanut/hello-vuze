package lbms.plugins.mldht.kad.messages;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.TreeMap;

import gudy.azureus2.core3.util.BEncoder;
import lbms.plugins.mldht.kad.DHTConstants;
import lbms.plugins.mldht.kad.Key;
import lbms.plugins.mldht.kad.RPCServer;
import lbms.plugins.mldht.kad.messages.MessageBase.Method;
import lbms.plugins.mldht.kad.messages.MessageBase.Type;

public class MessageBase {
	
	public static final String	VERSION_KEY = "v";
	public static final String	TRANSACTION_KEY = "t";
	
	protected byte[]			mtid;
	protected Method			method;
	protected Type				type;
	protected Key				id;
	protected InetSocketAddress	origin;
	protected String			version;
	protected RPCServer			srv;
	
	public MessageBase(byte[] mtid, Method m, Type type) {
		this.mtid = mtid;
		this.method = m;
		this.type = type;
	}

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
	}

	/// Get the origin
	public InetSocketAddress getDestination() {
		return origin;
	}
	
	/// Set the origin (i.e. where the message came from)
	public void setDestination(InetSocketAddress o) {
		origin = o;
	}
		
	/**
	 * BEncode the message.
	 * @return Data array
	 */
	public byte[] encode() throws IOException {
		return BEncoder.encode(getBase());
	}
	
	public Map<String, Object> getBase() {
		Map<String, Object> base = new TreeMap<String, Object>();
		Map<String, Object> inner = getInnerMap();
		if (inner != null)
			base.put(getType().innerKey(), inner);
		
		// transaction ID
		base.put(TRANSACTION_KEY, mtid);
		// version
		base.put(VERSION_KEY, DHTConstants.getVersion());
	
		// message type
		base.put(Type.TYPE_KEY, getType().getRPCTypeName());
		// message method if we're a request
		if (getType() == Type.REQ_MSG)
			base.put(getType().getRPCTypeName(), getMethod().getRPCName());

		return base;
	}
	
	public Map<String, Object> getInnerMap() {
		return null;
	}
}
