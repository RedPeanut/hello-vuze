package lbms.plugins.mldht.kad;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import lbms.plugins.mldht.kad.messages.FindNodeRequest;

public class RPCServerWrapper {
	
	private void resolveBootstrapAddresses() {
		
		List<InetSocketAddress> nodeAddresses =  new ArrayList<InetSocketAddress>();
		
		for (int i = 0;i<DHTConstants.BOOTSTRAP_NODES.length;i++) {
			String 	hostname 	= DHTConstants.BOOTSTRAP_NODES[i];
			int 	port 		= DHTConstants.BOOTSTRAP_PORTS[i];
			try {
				InetAddress[] result = InetAddress.getAllByName(hostname);
				for (InetAddress addr : result) {
					nodeAddresses.add(new InetSocketAddress(addr, port));
				}
			} catch (Exception e) {
				
			}
		}
		
		if (nodeAddresses.size() > 0)
			DHTConstants.BOOTSTRAP_NODE_ADDRESSES = nodeAddresses;
	}
	public void start() {
		
		RPCServer srv = new RPCServer(49001);
		//new Thread(srv).start();
		
		Key targetKey = null;
		FindNodeRequest fnr = new FindNodeRequest(targetKey);
		
		String 	hostname 	= DHTConstants.BOOTSTRAP_NODES[1];
		int 	port 		= DHTConstants.BOOTSTRAP_PORTS[1];
		InetSocketAddress isa = new InetSocketAddress(hostname, port);
		fnr.setDestination(isa);
		srv.sendMessage(fnr);
		
		// need 1 non-daemon-thread to keep VM alive
		while (true) {
			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		new RPCServerWrapper().start();
	}
}
