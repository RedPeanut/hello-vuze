package hello.main;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Main {
	
	public static void main(String[] args) {
		new Main().exec(args);
	}
	
	public void exec(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		FillLayout fillLayout = new FillLayout();
		shell.setLayout(fillLayout);
		
		Button listenButton = new Button(shell, SWT.PUSH);
		listenButton.setText ("listening...");
		listenButton.addListener(SWT.Selection, listenButtonClickListener);
		
		Button sendButton = new Button(shell, SWT.PUSH);
		sendButton.setText ("sending...");
		sendButton.addListener(SWT.Selection, sendButtonClickListener);
		
		shell.pack();
		shell.open();
		
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	Listener listenButtonClickListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			new Thread() {
				@Override
				public void run() {
					try {
						ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
						serverSocketChannel.bind(new InetSocketAddress(9999));
						
						while (true) {
							SocketChannel socketChannel = serverSocketChannel.accept();
							//socketQueue.add(new Socket(socketChannel));
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	};
	
	Listener sendButtonClickListener = new Listener() {
		@Override
		public void handleEvent(Event event) {
			new Thread() {
				@Override
				public void run() {
					try {
						Selector readSelector = Selector.open();
						Selector writeSelector = Selector.open();
						
						while (true) {
							
							//takeNewSockets();
							//readFromSockets();
							//writeSockets();
							
							try {
								Thread.sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	};
	
}
