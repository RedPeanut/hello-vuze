package gudy;

import gudy.azureus2.core3.util.AEMonitor;
import gudy.azureus2.core3.util.ThreadPool;

public class TestMonitor {
	
	/*public static void main(String[] args) {
		
		final AEMonitor monitor = new AEMonitor("Test monitor");
		
		Thread a = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					monitor.enter();
					Thread.sleep(3000);
					System.out.println("[A] run() is called...");
					//a.start();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					monitor.exit();
				}
			}
		});
		a.start();
		
		Thread b = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					monitor.enter();
					Thread.sleep(2000);
					System.out.println("[B] run() is called...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					monitor.exit();
				}
			}
		});
		b.start();
		
		Thread c = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					monitor.enter();
					Thread.sleep(1000);
					System.out.println("[C] run() is called...");
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					monitor.exit();
				}
			}
		});
		c.start();
		
		System.out.println("[outer] main() is called...");

	}*/
	
	public static void main(String[] args) {
		System.out.println("start...");
		
		final AEMonitor monitor = new AEMonitor("Test monitor");
		
		for (int i = 0; i < 10; i++) {
			final int f_i = i;
			Runnable r = new Runnable() {
				@Override
				public void run() {
					try {
						monitor.enter();
						System.out.println(String.format("#%d is run...", f_i));
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					} finally {
						monitor.exit();
					}
				}
			};
			new Thread(r).start();
		}
		System.out.println("end...");
	}
	
}
