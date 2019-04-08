package gudy.azureus2.core3.util;

/**
 * mutex(binary semaphore)
 * called to monitor in vuze...
 */
public class AEMonitor {

	private int count = 1;
	
	public AEMonitor(String string) {}

	public void enter() {
		synchronized(this) {
			if (count == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				count--;
			}
		}
	}
	
	public void exit() {
		synchronized(this) {
			notify();
		}
	}

}
