package hello.main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import aelitis.azureus.core.dht.transport.udp.impl.DHTUDPPacketHelper;
import aelitis.net.udp.uc.PRUDPPacket;
import aelitis.net.udp.uc.PRUDPPacketHandlerException;
import aelitis.net.udp.uc.PRUDPPacketHandlerRequest;
import aelitis.net.udp.uc.PRUDPPacketReceiver;
import aelitis.net.udp.uc.PRUDPPacketRequest;
import aelitis.net.udp.uc.impl.PRUDPPacketHandlerImpl;
import gudy.azureus2.core3.util.RandomUtils;

public class PRUDPPacketHandler {
	
	private Random random;
	
	public static void main(String[] args) {
		try {
			new PRUDPPacketHandler().exec(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected PRUDPPacketHandler() {
		random = RandomUtils.SECURE_RANDOM;
	}
	
	private void exec(String[] args) {
		try {
			Display display = new Display();
			
			Shell shell = new Shell(display);
			shell.setText("Hello, world!");
			
			shell.setLayout(new FormLayout());
			initialize(shell);
			shell.pack();
			refresh();
			shell.open();
			
			// Set up the event loop.
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					// If no more entries in the event queue
					display.sleep();
				}
			}
			display.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	Composite panel;
	
	private void initialize(Composite parent) throws Exception {
		
		int port = 59294;
		final PRUDPPacketHandlerImpl receiver = new PRUDPPacketHandlerImpl(port, null, null);
		
		final PRUDPPacket requestPacket = new PRUDPPacketRequest(
				DHTUDPPacketHelper.ACT_REQUEST_PING,	// actionId - defined
				getConnectionID()						// connectionId - random generate number
		) {

			@Override
			public void serialise(DataOutputStream os) throws IOException {
				super.serialise(os);
				
				
			}
			
		};
		final InetSocketAddress destinationAddress = 
			new InetSocketAddress(
					InetAddress.getByAddress(new byte[] { 127,0,0,1 }),
					port
			); // send to self
		
		new java.util.Timer().schedule( 
	        new java.util.TimerTask() {
	            @Override
	            public void run() {
	            	try {
						receiver.sendAndReceive(
							null,				// PasswordAuthentication	auth,
							requestPacket,		// PRUDPPacket				requestPacket,
							destinationAddress,	// InetSocketAddress		destinationAddress,
							new PRUDPPacketReceiver() {
								@Override
								public void packetReceived(
										PRUDPPacketHandlerRequest request,
										PRUDPPacket packet,
										InetSocketAddress fromAddress) {
									
								}

								@Override
								public void error(PRUDPPacketHandlerException e) {
								}
							},					// PRUDPPacketReceiver		receiver,
							10000,				// udp timeout 
							aelitis.net.udp.uc.impl.PRUDPPacketHandler.PRIORITY_MEDIUM	// int		priority
						);
					} catch (PRUDPPacketHandlerException e) {
						e.printStackTrace();
					}
	            }
	        }, 
	        100 
		);
		
		panel = new Composite(parent,SWT.NULL);
	}

	private void refresh() {
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
}
