package gudy.azureus2.core3.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtils {

	public static final Random RANDOM = new Random(System.currentTimeMillis());
	public static final SecureRandom SECURE_RANDOM = new SecureRandom();
	
	public static int nextInt() {
		return 0;
	}

	public static final int LISTEN_PORT_MIN = 10000;
	public static final int LISTEN_PORT_MAX = 65535;

	/**
	 * Generate a random port number for binding a network IP listening socket to.
	 * NOTE: Will return a valid non-privileged port number >= LISTEN_PORT_MIN and <= LISTEN_PORT_MAX.
	 * @return random port number
	 */
	public static int generateRandomNetworkListenPort() {
		return (generateRandomNetworkListenPort(LISTEN_PORT_MIN, LISTEN_PORT_MAX));
	}
	
	public static int generateRandomNetworkListenPort(
			int		minPort,
			int		maxPort)
		{
			if (minPort > maxPort) {
				int temp 	= minPort;
				minPort	= maxPort;
				maxPort	= temp;
			}
			
			if (maxPort > LISTEN_PORT_MAX)
				maxPort = LISTEN_PORT_MAX;
			if (maxPort < 1)
				maxPort = 1;
			if (minPort < 1)
				minPort = 1;
			if (minPort > maxPort)
				minPort = maxPort;
			
			// DON'T use NetworkManager methods to get the ports here else startup can hang
			//int existingTcp	= COConfigurationManager.getIntParameter("TCP.Listen.Port");
			//int existingUdp	= COConfigurationManager.getIntParameter("UDP.Listen.Port");
			//int existingUdp2	= COConfigurationManager.getIntParameter("UDP.NonData.Listen.Port");
			int port = minPort;
			for (int i=0;i<100;i++) {
				int min 	= minPort;
				port 		= min + RANDOM.nextInt(maxPort + 1 - min);
				// skip magnet ports
				if (port >= 45100 && port <= 45110) {
					continue;
				}
				/*if (port != existingTcp && port != existingUdp && port != existingUdp2) {
					return port;
				}*/
				return port;
			}
			return (port);
		}
}
