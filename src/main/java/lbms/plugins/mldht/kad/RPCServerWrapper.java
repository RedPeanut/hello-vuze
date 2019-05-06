package lbms.plugins.mldht.kad;

public class RPCServerWrapper {
	
	public void start() {
		
		new Thread(new RPCServer(49001)).start();
		
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
