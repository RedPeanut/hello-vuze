package aelitis.azureus.core.dht.transport.udp.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import aelitis.azureus.core.dht.transport.DHTTransportException;

public class DHTUDPUtils {
	
	public static void serialiseAddress(
		DataOutputStream	os,
		InetSocketAddress	address)
		throws IOException, DHTTransportException
	{
		InetAddress	ia = address.getAddress();
		if (ia == null) {
			// could be an unresolved dht6 seed, stick in a fake value as we are committed to serialising
			// something here
			serialiseByteArray(os, new byte[4], 16);
			os.writeShort(0);
		} else {
			serialiseByteArray(os, ia.getAddress(), 16);	// 16 (Pv6) + 1 length
			os.writeShort(address.getPort());	//19
		}
	}
	
	protected static void serialiseByteArray(
		DataOutputStream	os,
		byte[]				data,
		int					maxLength)
		throws IOException
	{
		serialiseByteArray(os, data, 0, data.length, maxLength);
	}
	
	protected static void serialiseByteArray(
		DataOutputStream	os,
		byte[]				data,
		int					start,
		int					length,
		int					maxLength)
		throws IOException
	{
		serialiseLength(os, length, maxLength);
		os.write(data, start, length);
	}
	
	protected static void serialiseLength(
		DataOutputStream	os,
		int					len,
		int					maxLength)
		throws IOException
	{
		if (len > maxLength) {
			throw(new IOException("Invalid DHT data length: max=" + maxLength + ",actual=" + len));
		}
		if (maxLength < 256) {
			os.writeByte(len);
		} else if (maxLength < 65536) {
			os.writeShort(len);
		} else {
			os.writeInt(len);
		}
	}
}
