package lbms.plugins.mldht.kad;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class RPCServer implements Runnable {

	private DatagramSocket							sock;
	private volatile boolean						running;
	private int										numReceived;
	private int										numSent;
	private int										port;
	
	public RPCServer(int port) {
		this.port = port;
	}
	
	@Override
	public void run() {
		
		int delay = 1;
		
		byte[] buffer = new byte[DHTConstants.RECEIVE_BUFFER_SIZE];
		
		while (running) {
			
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			
			try {
			
				if (sock.isClosed()) { // don't try to receive on a closed socket, attempt to create a new one instead.
					Thread.sleep(delay * 100);
					if (delay < 256)
						delay <<= 1;
					if (createSocket())
						continue;
					else
						break;
				}
				
				sock.receive(packet);
				
			} catch (Exception e) {
				continue;
			}
			
			handlePacket(packet);
		}
	}
	
	private boolean createSocket() {
		
		if (sock != null)
			sock.close();
		
		try {
			InetAddress addr = InetAddress.getByAddress(new byte[] { 0,0,0,0 });
			
			sock = new DatagramSocket(null);
			sock.setReuseAddress(true);
			sock.bind(new InetSocketAddress(addr, port));
			
			return true;
		} catch (Exception e) {
			if (sock != null)
				sock.close();
			//destroy();
			return false;
		}
	}
	
	private void handlePacket(DatagramPacket p) {
		numReceived++;
	}
	
}
