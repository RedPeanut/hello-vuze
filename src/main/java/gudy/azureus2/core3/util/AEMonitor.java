package gudy.azureus2.core3.util;

/**
 * mutex(binary semaphore)
 * called to monitor in vuze...
 */
public class AEMonitor {

	private int dontWait = 1;
	
	public AEMonitor(String string) {}

	public void enter() {
		enter(null);
	}

	public void enter(String from) {
		synchronized(this) {
			if (dontWait == 0) {
				try {
					System.out.println("from = " + from);
					System.out.println("wait() is called...");
					wait();
					System.out.println("notified...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				dontWait--;
			}
		}
	}
	
	public void exit() {
		synchronized(this) {
			notify();
		}
	}

}
