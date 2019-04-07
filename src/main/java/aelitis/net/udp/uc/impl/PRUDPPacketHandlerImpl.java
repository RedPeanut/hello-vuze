package aelitis.net.udp.uc.impl;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import aelitis.net.udp.uc.PRUDPPacket;
import aelitis.net.udp.uc.PRUDPPacketHandlerException;
import aelitis.net.udp.uc.PRUDPPacketReceiver;
import aelitis.net.udp.uc.PRUDPRequestHandler;
import gudy.azureus2.core3.util.AESemaphore;
import gudy.azureus2.core3.util.AEThread2;
import gudy.azureus2.core3.util.SystemTime;
import hello.util.Log;

public class PRUDPPacketHandlerImpl implements PRUDPPacketHandler {
	
	private static String TAG = PRUDPPacketHandlerImpl.class.getSimpleName();
	
	private static int MAX_PACKET_SIZE = 8192;
	
	private int				port;
	private DatagramSocket	socket;
	
	private InetAddress				defaultBindIp;
	private InetAddress				explicitBindIp;

	private volatile InetAddress	currentBindIp;
	private volatile InetAddress	targetBindIp;

	private volatile boolean		failed;
	private volatile boolean		destroyed;
	
	private final PacketTransformer	packetTransformer;
	
	public PRUDPPacketHandlerImpl(
			int					_port,
			InetAddress			_bindIp,
			PacketTransformer	_packetTransformer) throws UnknownHostException {
		
		port = _port;
		explicitBindIp = _bindIp;
		packetTransformer = _packetTransformer;
		defaultBindIp = InetAddress.getByAddress(new byte[] { 0,0,0,0 });
		//calcBind();
		targetBindIp = defaultBindIp;
		
		final AESemaphore initSemaphore = new AESemaphore("PRUDPPacketHandler:init");
		new AEThread2("PRUDPPacketReciever:" + port, true) {
			public void run() {
				receiveLoop(initSemaphore);
			}
		}.start();
		//initSemaphore.reserve();
	}
	
	protected void calcBind() {}
	
	protected void receiveLoop(AESemaphore initSemaphore) {
		
		try {
			//System.out.println("failed = " + failed);
			//System.out.println("destroyed = " + destroyed);
			//System.out.println("!failed && !destroyed = " + (!failed && !destroyed));
			
			// outter loop picks up bind-ip changes
			while (!failed && !destroyed) {
				
				if (socket != null) {
					try {
						socket.close();
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
				
				InetSocketAddress	address		= null;
				DatagramSocket		newSocket	= null;
				
				try {
					if (targetBindIp == null) {
						address = new InetSocketAddress("127.0.0.1", port);
						newSocket = new DatagramSocket(port);
					} else {
						address = new InetSocketAddress(targetBindIp, port);
						newSocket = new DatagramSocket(address);
					}
				} catch (BindException e) {
					//throw(e);
				}
				
				newSocket.setReuseAddress(true);
				// short timeout on receive so that we can interrupt a receive fairly quickly
				newSocket.setSoTimeout(1000);
				// only make the socket public once fully configured
				socket			= newSocket;
				currentBindIp	= targetBindIp;
				
				byte[] buffer = null;
				
				while (!failed && !destroyed) {
					
					//int count = SingleCounter0.getInstance().getAndIncreaseCount();
					//Log.d(TAG, String.format("how many times is this called... #%d", count));
					
					if (currentBindIp != targetBindIp)
						break;
				
					try {
						
						Log.d(TAG, ">>> 1");
						
						if (buffer == null)
							buffer = new byte[MAX_PACKET_SIZE];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address);
						//receiveFromSocket(packet);
						socket.receive(packet);
						
						Log.d(TAG, ">>> 2");
						
						long receiveTime = SystemTime.getCurrentTime();
						
						if (buffer != null) {
							//System.out.println("packet is received...");
							process(packet, receiveTime);
						}
						
						Log.d(TAG, ">>> 3");
						
					} catch (SocketTimeoutException e) {
						//
						Log.d(TAG, "ste is occured...");
					}
					
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			//System.out.println("finally...");
			if (socket != null) {
				try {
					socket.close();
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void process(DatagramPacket dgPacket, long receiveTime) {
		byte[]	packetData	= dgPacket.getData();
		int		packetLen	= dgPacket.getLength();
		
		InetSocketAddress originator = (InetSocketAddress)dgPacket.getSocketAddress();
		
		// check request or reply from first one byte
		boolean	requestPacket;
		
		// reply
		if ((packetData[0]&0x80) == 0) {
			requestPacket = false;
		}
		// request
		else {
			requestPacket = true;
		}
		
		// request
		if (requestPacket) {
			
		} 
		// reply
		else {
			
		}
	}

	private void receiveFromSocket(DatagramPacket p) throws IOException {
		socket.receive(p);
		if (packetTransformer != null) {
			packetTransformer.transformReceive(p);
		}
	}
	
	public void send(PRUDPPacket	requestPacket,
			InetSocketAddress		destinationAddress) {
		DataOutputStream os = null;
		try {
			MyByteArrayOutputStream	baos = new MyByteArrayOutputStream(MAX_PACKET_SIZE);
			os = new DataOutputStream(baos);
			requestPacket.serialise(os);
			byte[]	_buffer = baos.getBuffer();
			int		_length	= baos.size();
			requestPacket.setSerialisedSize(_length);
			DatagramPacket dgPacket = new DatagramPacket(_buffer, _length, destinationAddress);
			socket.send(dgPacket);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (os != null) try { os.close(); } catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	/*public void sendAndReceive(PRUDPPacket requestPacket,
			InetSocketAddress destinationAddress,
			PRUDPPacketReceiver receiver,
			long timeout, 
			int priority)
			throws PRUDPPacketHandlerException {
		sendAndReceive(null, requestPacket, destinationAddress, receiver, timeout, priority);
	}*/
	
	public PRUDPPacketHandlerRequestImpl sendAndReceive(
			PasswordAuthentication		auth,
			PRUDPPacket					requestPacket,
			InetSocketAddress			destinationAddress,
			PRUDPPacketReceiver			receiver,
			long						timeout,
			int							priority)
			throws PRUDPPacketHandlerException
	{
		if (socket == null) {
			System.out.println("socket == null");
			return null;
		}
		
		DataOutputStream os = null;
		try {
			MyByteArrayOutputStream baos = new MyByteArrayOutputStream(MAX_PACKET_SIZE);
			os = new DataOutputStream(baos);
			requestPacket.serialise(os);
			
			byte[] _buffer = baos.getBuffer();
			int _length = baos.size();
			
			DatagramPacket dgPacket = new DatagramPacket(_buffer, _length, destinationAddress);
			socket.send(dgPacket);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) try { os.close(); } catch (IOException e) { e.printStackTrace(); }
		}
		return null;
	}
	
	public void destroy() {
		destroyed	= true;
	}
	
	private PRUDPRequestHandler		requestHandler;
	
	private List<Object[]>	recvQueue = new ArrayList<>();
	
	public void setRequestHandler(PRUDPRequestHandler _requestHandler) {
		requestHandler = _requestHandler;
	}
	
	public long getSendQueueLength() {
		int res = 0;
		return res;
	}
	
	public long getReceiveQueueLength() {
		long size = recvQueue.size();
		return size;
	}
	
	private static class MyByteArrayOutputStream extends ByteArrayOutputStream {
		
		private MyByteArrayOutputStream(int	size) {
			super(size);
		}

		private byte[] getBuffer() {
			return (buf);
		}
	}

	protected interface	PacketTransformer {
		public void transformSend(DatagramPacket packet);
		public void transformReceive(DatagramPacket packet);
	}
}
