package gudy;

import gudy.azureus2.core3.util.ThreadPool;

public class TestThreadPool {
	
	public static void main(String[] args) {
		System.out.println("start...");
		ThreadPool pool = new ThreadPool("", 5);
		
		for (int i = 0; i < 10; i++) {
			final int f_i = i;
			Runnable r = new Runnable() {
				@Override
				public void run() {
					System.out.println(String.format("#%d is run...", f_i));
					try {
						Thread.sleep((10-f_i)*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			pool.run(r);
		}
		System.out.println("end...");
	}
}
