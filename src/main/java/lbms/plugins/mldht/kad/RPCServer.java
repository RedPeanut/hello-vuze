package lbms.plugins.mldht.kad;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;

import gudy.azureus2.core3.util.BDecoder;
import hello.util.Log;
import lbms.plugins.mldht.kad.messages.MessageBase;
import lbms.plugins.mldht.kad.messages.MessageDecoder;

public class RPCServer implements Runnable {
	
	private static String TAG = RPCServer.class.getSimpleName();
	
	private DatagramSocket							sock;
	private volatile boolean						running;
	private Thread									thread;
	private int										numReceived;
	private int										numSent;
	private int										port;
	
	public RPCServer(int port) {
		this.port = port;
		start();
	}
	
	public void start() {
		if (!createSocket())
			return;
		
		// start thread after registering so the DHT can handle incoming packets properly
		thread = new Thread(this, "mlDHT RPC Thread ");
		thread.setPriority(Thread.MIN_PRIORITY);
		thread.setDaemon(true);
		thread.start();
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
	
	// we only decode in the listening thread, so reused the decoder
	private BDecoder decoder = new BDecoder();
		
	private void handlePacket(DatagramPacket p) {
		numReceived++;
		
		try {
			Log.v(TAG, new String(p.getData(), 0, p.getLength(), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Map<String, Object> bedata = decoder.decodeByteArray(p.getData(), 0, p.getLength(), false);
			MessageBase msg = MessageDecoder.parseMessage(bedata, this);
			Log.d(TAG, "RPC received message ["+p.getAddress().getHostAddress()+"] "+msg.toString());
			
		} catch (IOException e) {
			
		}
	}
	
	public void sendMessage(MessageBase msg) {
		try {
			send(msg.getDestination(), msg.encode());
			Log.d(TAG, "RPC send Message: [" + msg.getDestination().getAddress().getHostAddress() + "] "+ msg.toString());
		}  catch (IOException e) {
			System.out.print(sock.getLocalAddress()+" -> "+msg.getDestination()+" ");
			e.printStackTrace();
		}
	}
	
	private void send(InetSocketAddress addr, byte[] msg) throws IOException {
		if (!sock.isClosed()) {
			DatagramPacket p = new DatagramPacket(msg, msg.length);
			p.setSocketAddress(addr);
			sock.send(p);
			
			try {
				Log.v(TAG, new String(p.getData(), 0, p.getLength(), "UTF-8"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			numSent++;
		}
	}
	
}
