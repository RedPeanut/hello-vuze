package gudy.azureus2.core3.util;

public class AESemaphore {
	
	private String name;
	private int dontWait = 0;
	
	public AESemaphore(String name) {
		this(name, 0);
	}

	public AESemaphore(String name, int count) {
		this.name = name;
		this.dontWait = count;
	}

	public void reserve() {
		synchronized(this) {
			if (dontWait == 0) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				dontWait--;
			}
		}
	}

	public void release() {
		synchronized(this) {
			notify();
		}
	}
}
