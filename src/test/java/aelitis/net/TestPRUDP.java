package aelitis.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.Random;

import org.junit.Test;

import aelitis.azureus.core.dht.transport.DHTTransportException;
import aelitis.azureus.core.dht.transport.udp.DHTTransportUDP;
import aelitis.azureus.core.dht.transport.udp.impl.DHTUDPPacketHelper;
import aelitis.azureus.core.dht.transport.udp.impl.DHTUDPUtils;
import aelitis.net.udp.uc.PRUDPPacketReply;
import aelitis.net.udp.uc.PRUDPPacketRequest;
import aelitis.net.udp.uc.impl.PRUDPPacketHandler;
import aelitis.net.udp.uc.impl.PRUDPPacketHandlerImpl;
import gudy.azureus2.core3.util.RandomUtils;
import gudy.azureus2.core3.util.SystemTime;

public class TestPRUDP {

	@Test
	public void makePlainIp() {
		int[][] hexIps = {
			{0xC4, 0x34, 0x22, 0x17, 0x9C78},
			{0xBC, 0xDC, 0xD5, 0xD7, 0xB8EA},
			{0xC2, 0x87, 0x99, 0xAE, 0x5DE8},
			{0x2E, 0xF1, 0x05, 0x56, 0xE4BF},
			{0x1F, 0xC8, 0xEF, 0x5E, 0x509E},
			{0x7C, 0x95, 0xC3, 0x55, 0xCA14},
			{0x7C, 0x95, 0xC3, 0x55, 0xCA14},
			{0xB0, 0x0F, 0xC4, 0x03, 0xF3E8}
		};
		
		for (int i=0;i<hexIps.length;i++) {
			System.out.println(String.format("%d.%d.%d.%d:%d", hexIps[i][0], hexIps[i][1], hexIps[i][2], hexIps[i][3], hexIps[i][4]));
		}
	}
	
	@Test
	public void testInetAddress() {
		InetAddress localhostV4 = null;
		try {
			//anyLocalAddressIPv4 = InetAddress.getByAddress(new byte[] { 0,0,0,0 });
			localhostV4 = InetAddress.getByAddress(new byte[] {127,0,0,1});
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		//InetSocketAddress address = localhostV4;
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 5555);
		InetAddress ia = address.getAddress();
		byte[] bytes = ia.getAddress();
		System.out.println(bytes.length);
		
	}
	
	private Random				random;
	
	public TestPRUDP() {
		random = RandomUtils.SECURE_RANDOM;
	}
	
	protected long getConnectionID() {
		// unfortunately, to reuse the UDP port with the tracker protocol we
		// have to distinguish our connection ids by setting the MSB. This allows
		// the decode to work as there is no common header format for the request
		// and reply packets

		// note that tracker usage of UDP via this handler is only for outbound
		// messages, hence for that use a request will never be received by the
		// handler
		return (0x8000000000000000L | random.nextLong());
	}
	
	public void exec() throws UnknownHostException {
		int listenPort = 59294; //RandomUtils.generateRandomNetworkListenPort();
		System.out.println("listenPort = " + listenPort);
		
		InetAddress anyLocalAddressIPv4 = null;
		InetAddress localhostV4 = null;
		try {
			anyLocalAddressIPv4 = InetAddress.getByAddress(new byte[] { 0,0,0,0 });
			localhostV4 = InetAddress.getByAddress(new byte[] {127,0,0,1});
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		InetAddress listenAddress = localhostV4;
		
		//PRUDPPacketHandler packetHandler = PRUDPPacketHandlerFactory.getHandler(listenPort, listenAddress, null);
		PRUDPPacketHandler packetHandler = new PRUDPPacketHandlerImpl(listenPort, listenAddress, null);
		/*PRUDPRequestHandler requestHandler = new PRUDPRequestHandler() {
			@Override
			public void process(PRUDPPacketRequest request) {
			}
		};*/
		
		int action = DHTUDPPacketHelper.ACT_REPLY_PING;
		long connectionId = getConnectionID();
		
		PRUDPPacketRequest dhtUDPPacketRequest = new PRUDPPacketRequest(action, connectionId) {
			
			private byte				protocolVersion;
			private int					network;
			
			private byte				originatorVersion;
			private long				originatorTime;
			private InetSocketAddress	originatorAddress;
			private int					originatorInstanceId;
			private byte				flags;
			private byte				flags2;
			
			{
				protocolVersion = DHTTransportUDP.PROTOCOL_VERSION_PROC_TIME;
				network = 0x00000000;
				
				originatorTime = SystemTime.getCurrentTime();
				originatorAddress = new InetSocketAddress("1.244.159.36", 59294);
				originatorInstanceId = 0xBFE3A03C;
				flags = 0x00;
				flags2 = 0x01;
			}
			
			@Override
			public void serialise(DataOutputStream os) throws IOException {
				super.serialise(os);
				
				// add to this and you need to amend HEADER_SIZE above
				os.writeByte(protocolVersion);
				if (protocolVersion >= DHTTransportUDP.PROTOCOL_VERSION_VENDOR_ID) {
					os.writeByte(DHTTransportUDP.VENDOR_ID_ME);
				}
				if (protocolVersion >= DHTTransportUDP.PROTOCOL_VERSION_NETWORKS) {
					os.writeInt(network);
				}
				if (protocolVersion >= DHTTransportUDP.PROTOCOL_VERSION_FIX_ORIGINATOR) {
					// originator version
					//os.writeByte(getTransport().getProtocolVersion());
					os.writeByte(protocolVersion);
				}
				
				try {
					DHTUDPUtils.serialiseAddress(os, originatorAddress);
				} catch (DHTTransportException e) {
					throw(new IOException(e.getMessage()));
				}
				os.writeInt(originatorInstanceId);
				os.writeLong(originatorTime);
				if (protocolVersion >= DHTTransportUDP.PROTOCOL_VERSION_PACKET_FLAGS) {
					os.writeByte(flags);
				}
				if (protocolVersion >= DHTTransportUDP.PROTOCOL_VERSION_PACKET_FLAGS2) {
					os.writeByte(flags2);
				}
			}
		};
		
		//*/
		/*
		//int destinationPort = 0;
		InetSocketAddress destinationAddress = new InetSocketAddress("1.244.159.36", 59294);
		
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			//
		}
		
		try {
			packetHandler.sendAndReceive(null,
					dhtUDPPacketRequest, //requestPacket,
					destinationAddress, //destinationAddress,
					null, //receiver,
					0L, //timeout,
					0 //priority
			);
		} catch (PRUDPPacketHandlerException e) {
			e.printStackTrace();
		}
		//*/
		
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				//
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			new TestPRUDP().exec();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPRUDP() {
	}
	
	@Test
	public void testPRUDP2() {
		PRUDPPacketRequest request = new PRUDPPacketRequest(0, 0) {};
		PRUDPPacketReply reply = new PRUDPPacketReply(0, 0) {};
	}
	
}
