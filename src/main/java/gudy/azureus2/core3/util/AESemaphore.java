package gudy.azureus2.core3.util;

public class AESemaphore {
	
	private String name;
	private int count = 0;
	
	public AESemaphore(String name) {
		this(name, 0);
	}

	public AESemaphore(String name, int count) {
		this.name = name;
		this.count = count;
	}

	// non blocking
	public boolean reserveIfAvailable() {
		synchronized(this) {
			if (count > 0) {
				reserve();
				return (true);
			} else {
				return (false);
			}
		}
	}
	
	// blocking
	public void reserve() {
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

	// 
	public void release() {
		synchronized(this) {
			notify();
			count++;
		}
	}
}
