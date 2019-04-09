package gudy;

import gudy.azureus2.core3.util.ThreadPool;

public class TestThreadPool {
	
	/*public static void main(String[] args) {
		System.out.println("start...");
		
		ThreadPool nonBlockingPool = new ThreadPool("", 5, ThreadPool.MODE_NONBLOCKING);
		for (int i = 0; i < 10; i++) {
			final int f_i = i;
			Runnable r = new Runnable() {
				@Override
				public void run() {
					System.out.println(String.format("#%d is run...", f_i));
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			nonBlockingPool.run(r);
		}
		System.out.println("end...");
	}*/
	
	public static void main(String[] args) {
		
		System.out.println("start...");
		
		ThreadPool blocking = new ThreadPool("", 5);
		
		for (int i = 0; i < 10; i++) {
			final int f_i = i;
			Runnable r = new Runnable() {
				@Override
				public void run() {
					System.out.println(String.format("#%d is run...", f_i));
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			blocking.run(r);
		}
		
		/*while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		
		System.out.println("end...");
	}
}
